package com.abik.babushka.recipe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abik.babushka.R;
import com.abik.babushka.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Fragment to display detailed information about a single recipe.
 */
public class RecipeDetailFragment extends Fragment {

    private Recipe recipe;

    public RecipeDetailFragment() {
        super(R.layout.recipe);
    }

    /**
     * Create a new instance of the fragment with a given recipe.
     */
    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("receta", recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the recipe passed from previous fragment
        recipe = (Recipe) requireArguments().getSerializable("receta");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView name = view.findViewById(R.id.txNombre);
        TextView time = view.findViewById(R.id.tvTiempo);
        TextView difficulty = view.findViewById(R.id.tvDificultad);
        TextView description = view.findViewById(R.id.txDescripcion);
        TextView ingredients = view.findViewById(R.id.txIngredientes);
        TextView preparation = view.findViewById(R.id.txPreparacion);
        ImageView image = view.findViewById(R.id.vwImagen);
        ImageView star = view.findViewById(R.id.Estrella);

        // Bind recipe data to UI elements
        name.setText(recipe.title);
        time.setText(String.valueOf(recipe.time));
        difficulty.setText(String.valueOf(recipe.difficulty));
        description.setText(recipe.description);
        ingredients.setText(formatIngredients(recipe.ingredients));
        preparation.setText(recipe.preparation);

        // Display image if available
        Bitmap bitmap = recipe.bitmapImage;
        if (bitmap != null) {
            image.setImageBitmap(bitmap);
        }

        // Set initial favorite star state
        updateStar(star);

        // Toggle favorite status on star click
        star.setOnClickListener(v -> toggleFavorite(star));
    }

    /**
     * Update star icon based on favorite status.
     */
    private void updateStar(ImageView star) {
        if (recipe.isFavorite) {
            star.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            star.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    /**
     * Format ingredients string to a readable list.
     */
    private String formatIngredients(String ingredients) {
        StringBuilder formatted = new StringBuilder();
        String[] list = ingredients.split(", ");
        for (String ingredient : list) {
            formatted.append("▶ ")
                    .append(Character.toUpperCase(ingredient.charAt(0)))
                    .append(ingredient.substring(1))
                    .append("\n");
        }
        return formatted.toString();
    }

    /**
     * Toggle favorite status and update backend.
     */
    private void toggleFavorite(ImageView star) {
        RetrofitClient.getApi()
                .postFavoriteRecipes(recipe.id, !recipe.isFavorite)
                .enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        recipe.isFavorite = !recipe.isFavorite;
                        updateStar(star);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
