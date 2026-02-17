package com.abik.babushka.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abik.babushka.R;
import com.abik.babushka.recipe.Recipe;
import com.abik.babushka.recipe.RecipeAdapter;
import com.abik.babushka.recipe.RecipeNavigation;
import com.abik.babushka.network.dto.RecipeResponseDto;
import com.abik.babushka.network.RetrofitClient;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private String search;
    private Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    private static final long SEARCH_DELAY = 500; // milisegundos
    private RecipeAdapter adapter;
    private List<Recipe> recetas = new ArrayList<>();
    private int currentPage = 0;
    private boolean isLoading = false;
    private Long categoryId = null;


    public HomeFragment() {
        super(R.layout.home_fragment);
    }

    public static HomeFragment newInstance(String categoria, Long categoriaId) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        // Guardar información
        args.putString("categoria", categoria);
        args.putSerializable("categoriaId", categoriaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = requireArguments();
        // Sacamos información
        String category = args.getString("categoria");
        categoryId = (Long) getArguments().getSerializable("categoriaId");

        TextView tituloCategoria = view.findViewById(R.id.tvCategoria);

        if (category != null) {
            tituloCategoria.setVisibility(View.VISIBLE);
            tituloCategoria.setText(category.toUpperCase());
        }

        //Barra buscador
        EditText barraBuscador = view.findViewById(R.id.etBuscar);

        barraBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = s.toString();

                // Cancelamos cualquier búsqueda pendiente
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                // Creamos una nueva búsqueda con delay
                searchRunnable = () -> {
                    currentPage = 0;
                    recetas.clear();
                    adapter.notifyDataSetChanged();
                    loadNextPage();
                };

                // Ejecutamos tras X ms desde la última tecla
                searchHandler.postDelayed(searchRunnable, SEARCH_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        RecyclerView rvRecetas = view.findViewById(R.id.rvRecetas);

        // Vertical Layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRecetas.setLayoutManager(layoutManager);

        // Creamos el adapter y definimos
        adapter = new RecipeAdapter(recetas, receta -> abrirDetalleReceta(receta));

        rvRecetas.setAdapter(adapter);

        // Peticion para recibir primeros elementos
        loadNextPage();

        // Scroll que "pide" más elementos una vez se llegue al final
        rvRecetas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy <= 0)
                    return; //Comprobamos en que elemento estamos (si estamos en el último no se hace nada más)

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

    private RecipeNavigation navigation;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RecipeNavigation) {
            navigation = (RecipeNavigation) context;
        } else {
            throw new IllegalStateException(
                    "MainActivity must implement RecipeNavigation"
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigation = null;
    }

    private void abrirDetalleReceta(Recipe receta) {
        navigation.abrirDetalle(receta);
    }

    private void loadNextPage() {
        isLoading = true;

        // Enviar petición
        RetrofitClient.getApi()
                .getRecipes(currentPage, 6, search, categoryId)
                .enqueue(new Callback<List<RecipeResponseDto>>() {
                    @Override
                    public void onResponse(
                            Call<List<RecipeResponseDto>> call,
                            Response<List<RecipeResponseDto>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            // Crear recetas from dto model
                            List<Recipe> nuevas = new ArrayList<>();
                            for (RecipeResponseDto dto : response.body()) {
                                nuevas.add(new Recipe(dto));
                            }

                            adapter.addRecetas(nuevas);
                            currentPage++;
                        }

                        isLoading = false;
                    }

                    @Override
                    public void onFailure(
                            Call<List<RecipeResponseDto>> call,
                            Throwable t) {
                        t.printStackTrace();
                        isLoading = false;
                    }
                });
    }
}