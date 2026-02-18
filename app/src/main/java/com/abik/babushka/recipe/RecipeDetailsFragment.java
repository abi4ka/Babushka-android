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
public class RecipeDetailsFragment extends Fragment {

    private Recipe recipe;

    public RecipeDetailsFragment() {
        super(R.layout.recipe);
    }

    /**
     * Create a new instance of the fragment with a given recipe.
     */
    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipe", recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the recipe passed from previous fragment
        recipe = (Recipe) requireArguments().getSerializable("recipe");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = view.findViewById(R.id.titleRecipe);
        TextView time = view.findViewById(R.id.timeRecipe);
        TextView difficulty = view.findViewById(R.id.difficultyRecipe);
        TextView description = view.findViewById(R.id.descriptionRecipe);
        TextView ingredientsList = view.findViewById(R.id.ingredientsListRecipe);
        TextView preparationSteps = view.findViewById(R.id.preparationStepsListRecipe);
        ImageView image = view.findViewById(R.id.imageRecipe);
        ImageView star = view.findViewById(R.id.starRecipe);

        // Bind recipe data to UI elements
        title.setText(recipe.title);
        time.setText(String.valueOf(recipe.time));
        difficulty.setText(String.valueOf(recipe.difficulty));
        description.setText(recipe.description);
        ingredientsList.setText(formatIngredients(recipe.ingredientsList));
        preparationSteps.setText(recipe.preparationSteps);

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
