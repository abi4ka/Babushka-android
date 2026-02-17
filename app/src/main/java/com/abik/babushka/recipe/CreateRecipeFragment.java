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
        super(R.layout.create_recipe_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Input fields
        EditText nombre = view.findViewById(R.id.etNombre);
        EditText tiempo = view.findViewById(R.id.etTiempo);
        EditText dificultad = view.findViewById(R.id.etDificultad);
        EditText descripcion = view.findViewById(R.id.etDescripcion);
        EditText ingredientes = view.findViewById(R.id.etIngredientes);
        EditText preparacion = view.findViewById(R.id.etPreparacion);

        TextView mensajeError = view.findViewById(R.id.mensajeError);
        Button botonCrear = view.findViewById(R.id.botonCrear);

        // Handle create button click
        botonCrear.setOnClickListener(v -> {
            // Validate input
            if (nombre.getText().toString().isEmpty()) {
                mensajeError.setText("Recipe name is empty.");
            } else if (tiempo.getText().toString().isEmpty()) {
                mensajeError.setText("Time is empty.");
            } else if (verifyNumber(tiempo.getText().toString())) {
                mensajeError.setText("Time must be a number.");
            } else if (dificultad.getText().toString().isEmpty()) {
                mensajeError.setText("Difficulty is empty.");
            } else if (verifyNumber(dificultad.getText().toString())) {
                mensajeError.setText("Difficulty must be a number.");
            } else if (Integer.parseInt(dificultad.getText().toString()) < 1 ||
                    Integer.parseInt(dificultad.getText().toString()) > 5) {
                mensajeError.setText("Difficulty must be between 1 and 5.");
            } else if (descripcion.getText().toString().isEmpty()) {
                mensajeError.setText("Description is empty.");
            } else if (ingredientes.getText().toString().isEmpty()) {
                mensajeError.setText("Ingredients are empty.");
            } else if (preparacion.getText().toString().isEmpty()) {
                mensajeError.setText("Preparation is empty.");
            } else {
                // Create DTO from input
                RecipeResponseDto receta = new RecipeResponseDto(
                        nombre.getText().toString(),
                        descripcion.getText().toString(),
                        ingredientes.getText().toString(),
                        preparacion.getText().toString(),
                        Integer.parseInt(tiempo.getText().toString()),
                        Integer.parseInt(dificultad.getText().toString()),
                        null
                );

                // Send recipe to backend
                RetrofitClient.getApi()
                        .createRecipe(receta)
                        .enqueue(new Callback<RecipeResponseDto>() {
                            @Override
                            public void onResponse(Call<RecipeResponseDto> call,
                                                   Response<RecipeResponseDto> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    mensajeError.setText("Recipe created successfully.");

                                    // Clear input fields
                                    nombre.setText("");
                                    descripcion.setText("");
                                    ingredientes.setText("");
                                    preparacion.setText("");
                                    tiempo.setText("");
                                    dificultad.setText("");
                                } else {
                                    mensajeError.setText("Server error while creating recipe.");
                                }
                            }

                            @Override
                            public void onFailure(Call<RecipeResponseDto> call, Throwable t) {
                                t.printStackTrace();
                                mensajeError.setText("Connection error.");
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
