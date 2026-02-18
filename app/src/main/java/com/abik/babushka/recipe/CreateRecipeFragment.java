package com.abik.babushka.recipe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abik.babushka.R;
import com.abik.babushka.network.RetrofitClient;
import com.abik.babushka.network.dto.RecipeResponseDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment for creating a new recipe.
 * Collects input from the user and sends it to the backend.
 */
public class CreateRecipeFragment extends Fragment {

    public CreateRecipeFragment() {
        super(R.layout.recipe_create_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Input fields
        EditText titleInput = view.findViewById(R.id.recipeTitleCreateRecipe);
        EditText timeInput = view.findViewById(R.id.timeCreateRecipe);
        EditText difficultyInput = view.findViewById(R.id.difficultyCreateRecipe);
        EditText descriptionInput = view.findViewById(R.id.descriptionCreateRecipe);
        EditText ingredientsListInput = view.findViewById(R.id.ingredientsCreateRecipe);
        EditText preparationStepsInput = view.findViewById(R.id.preparationStepsCreateRecipe);

        TextView errorMessage = view.findViewById(R.id.errorMessage);
        Button createRecipeButton = view.findViewById(R.id.buttonCreateCreateRecipe);

        // Handle create button click
        createRecipeButton.setOnClickListener(v -> {
            // Validate input
            if (titleInput.getText().toString().isEmpty()) {
                errorMessage.setText("Recipe name is empty.");
            } else if (timeInput.getText().toString().isEmpty()) {
                errorMessage.setText("Time is empty.");
            } else if (verifyNumber(timeInput.getText().toString())) {
                errorMessage.setText("Time must be a number.");
            } else if (difficultyInput.getText().toString().isEmpty()) {
                errorMessage.setText("Difficulty is empty.");
            } else if (verifyNumber(difficultyInput.getText().toString())) {
                errorMessage.setText("Difficulty must be a number.");
            } else if (Integer.parseInt(difficultyInput.getText().toString()) < 1 ||
                    Integer.parseInt(difficultyInput.getText().toString()) > 5) {
                errorMessage.setText("Difficulty must be between 1 and 5.");
            } else if (descriptionInput.getText().toString().isEmpty()) {
                errorMessage.setText("Description is empty.");
            } else if (ingredientsListInput.getText().toString().isEmpty()) {
                errorMessage.setText("Ingredients are empty.");
            } else if (preparationStepsInput.getText().toString().isEmpty()) {
                errorMessage.setText("Preparation is empty.");
            } else {
                // Create DTO from input
                RecipeResponseDto recipe = new RecipeResponseDto(
                        titleInput.getText().toString(),
                        descriptionInput.getText().toString(),
                        ingredientsListInput.getText().toString(),
                        preparationStepsInput.getText().toString(),
                        Integer.parseInt(timeInput.getText().toString()),
                        Integer.parseInt(difficultyInput.getText().toString()),
                        null
                );

                // Send recipe to backend
                RetrofitClient.getApi()
                        .createRecipe(recipe)
                        .enqueue(new Callback<RecipeResponseDto>() {
                            @Override
                            public void onResponse(Call<RecipeResponseDto> call,
                                                   Response<RecipeResponseDto> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    errorMessage.setText("Recipe created successfully.");

                                    // Clear input fields
                                    titleInput.setText("");
                                    descriptionInput.setText("");
                                    ingredientsListInput.setText("");
                                    preparationStepsInput.setText("");
                                    timeInput.setText("");
                                    difficultyInput.setText("");
                                } else {
                                    errorMessage.setText("Server error while creating recipe.");
                                }
                            }

                            @Override
                            public void onFailure(Call<RecipeResponseDto> call, Throwable t) {
                                t.printStackTrace();
                                errorMessage.setText("Connection error.");
                            }
                        });
            }
        });
    }

    /**
     * Verifies if a string is a valid integer.
     *
     * @param str input string
     * @return true if string is not a valid integer
     */
    private boolean verifyNumber(String str) {
        str = str.trim();
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return true;
        }
        return false;
    }
}
