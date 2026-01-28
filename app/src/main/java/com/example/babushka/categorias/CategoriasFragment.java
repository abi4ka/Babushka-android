package com.example.babushka.categorias;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babushka.R;
import com.example.babushka.network.RecipeApi;
import com.example.babushka.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Fragment que muestra la lista de categorías en pantalla
public class CategoriasFragment extends Fragment {

    // Listener para comunicar al fragment con la actividad principal
    private OnCategoriaSelected listener;
    RecyclerView recycler;

    public CategoriasFragment() {
        super(R.layout.fragment_categorias);
    }

    // Se ejecuta cuando el fragment se asocia a la actividad
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Comprueba que la actividad implemente la interfaz de comunicación
        if (context instanceof OnCategoriaSelected) {
            listener = (OnCategoriaSelected) context;
        }
    }

    // Se ejecuta cuando la vista del fragment ya ha sido creada
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recyclerCategories);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCategories();
    }

    // Metodo que obtiene las categorias desde el API REST
    void loadCategories() {
        // Obtiene la instancia de la API usando Retrofit
        RecipeApi api = RetrofitClient.getApi();

        // Realiza la petición asíncrona al servidor
        api.getCategories().enqueue(new Callback<List<CategoryDto>>() {
            // Realiza la petición asíncrona al servidor
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                // Comprueba que la respuesta es correcta y tiene datos
                if (response.isSuccessful() && response.body() != null) {
                    // Asigna el adaptador al RecyclerView con los datos recibidos
                    recycler.setAdapter(new CategoryAdapter(response.body(), listener));
                }
            }

            // Si ocurre un error de red o de servidor
            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
