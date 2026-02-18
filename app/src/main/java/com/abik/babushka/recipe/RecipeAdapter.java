package com.abik.babushka.recipe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abik.babushka.R;
import com.abik.babushka.network.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * RecyclerView Adapter for displaying recipes in a list.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private List<Recipe> recipeList;
    private OnRecipeClickListener listener;

    /**
     * Interface for handling clicks on a recipe item.
     * The adapter itself does not handle navigation.
     */
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipeList, OnRecipeClickListener listener) {
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_mini, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        // Bind recipe data to UI
        holder.title.setText(recipe.title);
        holder.description.setText(recipe.description);
        holder.difficulty.setText(String.valueOf(recipe.difficulty));
        holder.time.setText(String.valueOf(recipe.time));

        // Download recipe image
        RetrofitClient.getApi()
                .getRecipeImage(recipe.id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                            recipe.bitmapImage = bitmap;
                            holder.image.setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        // Set initial favorite star state
        updateStar(holder.star, recipe);

        // Toggle favorite status on star click
        holder.star.setOnClickListener(v -> toggleFavorite(holder.star, recipe));

        // Notify listener when recipe item is clicked
        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
    }

    /**
     * Add new recipes to the list and update the adapter.
     */
    public void addRecipes(List<Recipe> newRecipes) {
        int start = recipeList.size();
        recipeList.addAll(newRecipes);
        notifyItemRangeInserted(start, newRecipes.size());
    }

    /**
     * Update star icon according to favorite status.
     */
    private void updateStar(ImageView star, Recipe recipe) {
        if (recipe.isFavorite) {
            star.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            star.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    /**
     * Send request to toggle favorite status on backend and update UI.
     */
    private void toggleFavorite(ImageView star, Recipe recipe) {
        RetrofitClient.getApi()
                .postFavoriteRecipes(recipe.id, !recipe.isFavorite)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        recipe.isFavorite = !recipe.isFavorite;
                        updateStar(star, recipe);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    /**
     * ViewHolder class for mini recipe layout.
     * Holds references to all UI elements in a single item.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, difficulty, time;
        ImageView image, star;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleMiniRecipe);
            description = view.findViewById(R.id.descriptionMiniRecipe);
            difficulty = view.findViewById(R.id.difficultyMiniRecipe);
            image = view.findViewById(R.id.imageMiniRecipe);
            star = view.findViewById(R.id.starMiniRecipe);
            time = view.findViewById(R.id.timeMiniRecipe);
        }
    }
}
