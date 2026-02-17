package com.abik.babushka.perfil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abik.babushka.recipe.RecipeNavigation;
import com.abik.babushka.recipe.Receta;
import com.abik.babushka.recipe.RecetaAdapter;
import com.abik.babushka.R;
import com.abik.babushka.network.dto.RecipeResponseDto;
import com.abik.babushka.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListFragment extends Fragment {
    private RecipeListType type;
    private RecetaAdapter adapter;
    private List<Receta> recetas = new ArrayList<>();
    private int page = 0;
    private boolean isLoading = false;

    public static RecipeListFragment newInstance(RecipeListType type) {
        RecipeListFragment f = new RecipeListFragment();
        Bundle b = new Bundle();
        b.putString("type", type.name());
        f.setArguments(b);
        return f;
    }

    public RecipeListFragment() {
        super(R.layout.fragment_recipe_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        type = RecipeListType.valueOf(getArguments().getString("type"));

        RecyclerView rvRecetas = view.findViewById(R.id.rvRecetas);

        // Vertical Layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRecetas.setLayoutManager(layoutManager);

        /*
         * Creamos el adapter y definimos
         * qué pasa cuando se hace click en una receta
         */
        adapter = new RecetaAdapter(recetas, receta -> {
            abrirDetalleReceta(receta);
        });

        rvRecetas.setAdapter(adapter);

        // Peticion para recibir primeros elementos
        loadNextPage();

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
                    "MainActivity must implement InicioNavigation"
            );
        }
    }

    private void abrirDetalleReceta(Receta receta) {
        navigation.abrirDetalle(receta);
    }

    private void loadNextPage() {
        isLoading = true;

        Call<List<RecipeResponseDto>> call;

        // 2 posibles peticiones
        if (type == RecipeListType.MY_RECIPES) {
            call = RetrofitClient.getApi()
                    .getMyRecipes(page, 6);
        } else {
            call = RetrofitClient.getApi()
                    .getFavoriteRecipes(page, 6);
        }

        // Enviar petición
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<RecipeResponseDto>> call,
                                   Response<List<RecipeResponseDto>> response) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    // Crear recetas from dto model
                    List<Receta> nuevas = new ArrayList<>();
                    for (RecipeResponseDto dto : response.body()) {
                        nuevas.add(new Receta(dto));
                    }

                    adapter.addRecetas(nuevas);
                    page++;
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<List<RecipeResponseDto>> call, Throwable t) {
                isLoading = false;
            }
        });
    }
}