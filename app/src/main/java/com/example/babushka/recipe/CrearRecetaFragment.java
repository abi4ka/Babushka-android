package com.example.babushka.recipe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babushka.R;
import com.example.babushka.network.ClientResponse;
import com.example.babushka.network.RetrofitClient;
import com.example.babushka.network.dto.RecipeResponseDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearRecetaFragment extends Fragment {

    public CrearRecetaFragment() {
        super(R.layout.crear_receta);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText nombre = view.findViewById(R.id.etNombre);
        EditText tiempo = view.findViewById(R.id.etTiempo);
        EditText dificultad = view.findViewById(R.id.etDificultad);
        EditText descripcion = view.findViewById(R.id.etDescripcion);
        EditText ingretientes = view.findViewById(R.id.etIngredientes);
        EditText preparacion = view.findViewById(R.id.etPreparacion);

        TextView mensajeError = view.findViewById(R.id.mensajeError);
        Button botonCrear = view.findViewById(R.id.botonCrear);

        botonCrear.setOnClickListener(v -> {
            // Comprobaciones de info sacada de EditText
            if (nombre.getText().toString().length() < 1) {
                mensajeError.setText("Nombre de receta está vacío.");
                mensajeError.setVisibility(View.VISIBLE);

            } else if (tiempo.getText().toString().length() < 1){
                mensajeError.setText("Tiempo está vacío.");
                mensajeError.setVisibility(View.VISIBLE);

            } else if (verifyNumber(tiempo.getText().toString())) {
                mensajeError.setText("Tiempo debe ser número.");
                mensajeError.setVisibility(View.VISIBLE);

            }else if(dificultad.getText().toString().length() < 1){
                mensajeError.setText("Dificultad está vacío.");
                mensajeError.setVisibility(View.VISIBLE);

            } else if (verifyNumber(dificultad.getText().toString())) {
                mensajeError.setText("Dificultad debe ser número.");
                mensajeError.setVisibility(View.VISIBLE);

            }  else if (Integer.parseInt(dificultad.getText().toString()) < 1 ||
                        Integer.parseInt(dificultad.getText().toString()) > 5) {
                mensajeError.setText("Dificultad debe ser de 1 a 5.");
                mensajeError.setVisibility(View.VISIBLE);

            }else if (descripcion.getText().toString().length() < 1) {
                mensajeError.setText("Descripción está vacía.");
                mensajeError.setVisibility(View.VISIBLE);

            }else if (ingretientes.getText().toString().length() < 1) {
                mensajeError.setText("No hay ingredientes.");
                mensajeError.setVisibility(View.VISIBLE);

            } else if (preparacion.getText().toString().length() < 1) {
                mensajeError.setText("No hay preparación.");
                mensajeError.setVisibility(View.VISIBLE);

            } else{
                RecipeResponseDto receta = new RecipeResponseDto(
                        nombre.getText().toString(),
                        descripcion.getText().toString(),
                        ingretientes.getText().toString(),
                        preparacion.getText().toString(),
                        Integer.parseInt(tiempo.getText().toString()),
                        Integer.parseInt(dificultad.getText().toString()),
                        null);

                RetrofitClient.getApi()
                        .createRecipe(receta)
                        .enqueue(new Callback<ClientResponse<RecipeResponseDto>>() {

                            @Override
                            public void onResponse(
                                    Call<ClientResponse<RecipeResponseDto>> call,
                                    Response<ClientResponse<RecipeResponseDto>> response) {

                                if (response.isSuccessful()
                                        && response.body() != null) {

                                    // Receta creada correctamente
                                    mensajeError.setVisibility(View.GONE);

                                } else {
                                    // Error del servidor
                                    mensajeError.setText("Error al crear la receta");
                                    mensajeError.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onFailure(
                                    Call<ClientResponse<RecipeResponseDto>> call,
                                    Throwable t) {

                                t.printStackTrace();
                                mensajeError.setText("Error de conexión");
                                mensajeError.setVisibility(View.VISIBLE);
                            }
                        });

            }

        });



    }

    private boolean verifyNumber(String str){
        str = str.trim(); //Quitar espacios

        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

}
