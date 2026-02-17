package com.abik.babushka.category;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abik.babushka.R;
import com.abik.babushka.network.RecipeApi;
import com.abik.babushka.network.RetrofitClient;
import com.abik.babushka.network.dto.CategoryDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment responsible for displaying the list of recipe categories.
 */
public class CategoryFragment extends Fragment {

    private OnCategorySelected listener;
    private RecyclerView recycler;

    public CategoryFragment() {
        super(R.layout.category_fragment);
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Ensures the activity implements the OnCategorySelected interface.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnCategorySelected) {
            listener = (OnCategorySelected) context;
        }
    }

    /**
     * Initializes the RecyclerView and triggers category loading.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recyclerCategories);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCategories();
    }

    /**
     * Fetches categories from the REST API and updates the RecyclerView.
     */
    private void loadCategories() {
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
