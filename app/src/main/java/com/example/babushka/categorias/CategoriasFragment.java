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

public class CategoriasFragment extends Fragment {

    private OnCategoriaSelected listener;
    RecyclerView recycler;

    public CategoriasFragment() {
        super(R.layout.fragment_categorias);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoriaSelected) {
            listener = (OnCategoriaSelected) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recyclerCategories);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCategories();
    }

    void loadCategories() {
        RecipeApi api = RetrofitClient.getApi();

        api.getCategories().enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recycler.setAdapter(new CategoryAdapter(response.body(), listener));
                }
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
