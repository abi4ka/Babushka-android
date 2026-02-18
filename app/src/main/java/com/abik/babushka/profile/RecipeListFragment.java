package com.abik.babushka.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abik.babushka.recipe.RecipeNavigation;
import com.abik.babushka.recipe.Recipe;
import com.abik.babushka.recipe.RecipeAdapter;
import com.abik.babushka.R;
import com.abik.babushka.network.dto.RecipeResponseDto;
import com.abik.babushka.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment that displays a list of recipes.
 * Supports two types: user's created recipes and favorite recipes.
 */
public class RecipeListFragment extends Fragment {

    private RecipeListType type;
    private RecipeAdapter adapter;
    private List<Recipe> recetas = new ArrayList<>();
    private int page = 0;
    private boolean isLoading = false;
    private RecipeNavigation navigation;

    public RecipeListFragment() {
        super(R.layout.recipe_list_fragment);
    }

    public static RecipeListFragment newInstance(RecipeListType type) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putString("type", type.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RecipeNavigation) {
            navigation = (RecipeNavigation) context;
        } else {
            throw new IllegalStateException("Activity must implement RecipeNavigation");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        type = RecipeListType.valueOf(getArguments().getString("type"));

        RecyclerView rvRecetas = view.findViewById(R.id.rvRecipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRecetas.setLayoutManager(layoutManager);

        // Set up adapter with click listener for opening recipe details
        adapter = new RecipeAdapter(recetas, this::abrirDetalleReceta);
        rvRecetas.setAdapter(adapter);

        // Load initial recipes
        loadNextPage();

        // Infinite scroll listener to load more recipes when reaching near the end
        rvRecetas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy <= 0) return;

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount - 2) {
                    loadNextPage();
                }
            }
        });
    }

    private void abrirDetalleReceta(Recipe receta) {
        navigation.openRecipeDetails(receta);
    }

    /**
     * Loads the next page of recipes based on the fragment type.
     */
    private void loadNextPage() {
        isLoading = true;

        Call<List<RecipeResponseDto>> call = (type == RecipeListType.MY_RECIPES)
                ? RetrofitClient.getApi().getMyRecipes(page, 6)
                : RetrofitClient.getApi().getFavoriteRecipes(page, 6);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<RecipeResponseDto>> call,
                                   Response<List<RecipeResponseDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Recipe> nuevas = new ArrayList<>();
                    for (RecipeResponseDto dto : response.body()) {
                        nuevas.add(new Recipe(dto));
                    }
                    adapter.addRecipes(nuevas);
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
