package com.example.babushka.Inicio;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babushka.MainActivity;
import com.example.babushka.R;
import com.example.babushka.network.RecipeResponseDto;
import com.example.babushka.network.RetrofitClient;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioFragment extends Fragment {

    private RecyclerView rvRecetas;
    private RecetaAdapter adapter;
    private List<Receta> recetas = new ArrayList<>();

    private int currentPage = 0;
    private final int PAGE_SIZE = 6;
    private boolean isLoading = false;

    private MainActivity mainActivity;

    private String category;
    private int color;
    private String search;

    //Constructor
    public InicioFragment(MainActivity mainActivity, String category, int color) {
        super(R.layout.fragment_inicio);
        this.mainActivity = mainActivity;
        this.category = category;
        this.color = color;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Barra buscador
        EditText barraBuscador = view.findViewById(R.id.etBuscar);

        barraBuscador.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Guardamos el texto que escribe el usuario
                search = s.toString();

                // Reiniciamos paginación y lista
                currentPage = 0;
                recetas.clear();
                adapter.notifyDataSetChanged();

                // Pedimos la primera página con el texto de búsqueda
                loadNextPage();
            }

        });


        // Queremos guardar el fondo del fragment inicio para despues asignar color
        ConstraintLayout rootLayout = view.findViewById(R.id.rootLayout);
        rootLayout.setBackgroundColor(ContextCompat.getColor(mainActivity, color));

        rvRecetas = view.findViewById(R.id.rvRecetas);

        // Layout vertical
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRecetas.setLayoutManager(layoutManager);

        /*
         * Creamos el adapter y definimos
         * qué pasa cuando se hace click en una receta
         */
        adapter = new RecetaAdapter(recetas, receta -> {
            abrirDetalleReceta(receta);
        },getContext());

        rvRecetas.setAdapter(adapter);

        loadNextPage();

        // Scroll que "pide" más elementos una vez se llegue al final
        rvRecetas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy <= 0) return; //Comprobamos en que elemento estamos (si estamos en el último no se hace nada más)

                //Creación de variables para controlar...
                int visibleItemCount = layoutManager.getChildCount();                //... nº items en pantalla
                int totalItemCount = layoutManager.getItemCount();                   //... nº total items
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition(); //...posición del  primer item visible en el Scroll

                // Si cumple condiciones de variables se crea otro elemento que se mostrará en el Scroll
                if (!isLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount - 2) {
                    loadNextPage();
                }
            }
        });
    }

    private void abrirDetalleReceta(Receta receta) {
        mainActivity.replaceFragment(new DetalleFragment(receta));
    }

    // Simulación de carga de recetas (scroll infinito)
    private void loadNextPage() {
        isLoading = true;

        RetrofitClient.getApi()
                .getRecipes(currentPage, PAGE_SIZE, search)
                .enqueue(new Callback<List<RecipeResponseDto>>() {
                    @Override
                    public void onResponse(Call<List<RecipeResponseDto>> call,
                                           Response<List<RecipeResponseDto>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            List<Receta> nuevas = new ArrayList<>();

                            for (RecipeResponseDto dto : response.body()) {
                                nuevas.add(mapToReceta(dto));
                            }

                            adapter.addRecetas(nuevas);
                            currentPage++;
                        }

                        isLoading = false;
                    }

                    @Override
                    public void onFailure(Call<List<RecipeResponseDto>> call, Throwable t) {
                        t.printStackTrace();
                        isLoading = false;
                    }
                });
    }

    private Receta mapToReceta(RecipeResponseDto dto) {
        return new Receta(
                dto.id,
                dto.title,
                dto.description,
                String.valueOf(dto.difficulty),
                dto.ingredients,
                dto.preparation
        );
    }
}