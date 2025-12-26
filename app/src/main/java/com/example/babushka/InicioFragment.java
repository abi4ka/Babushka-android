package com.example.babushka;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public InicioFragment() {
        super(R.layout.fragment_inicio);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRecetas = view.findViewById(R.id.rvRecetas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRecetas.setLayoutManager(layoutManager);

        adapter = new RecetaAdapter(recetas);
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

    private void loadNextPage() {
        Toast.makeText(getContext(), "loadNextPage", Toast.LENGTH_SHORT).show();
        isLoading = true;

        RetrofitClient.getApi()
                .getRecipes(currentPage, PAGE_SIZE)
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
                dto.title,
                dto.description,
                String.valueOf(dto.difficulty),
                dto.ingredients,
                dto.preparation,
                dto.image
        );
    }
}