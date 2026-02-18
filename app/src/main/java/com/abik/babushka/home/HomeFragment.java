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

/**
 * Fragment responsible for displaying paginated recipes,
 * with optional category filtering and search functionality.
 */
public class HomeFragment extends Fragment {

    private String search;
    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    private static final long SEARCH_DELAY = 500;

    private RecipeAdapter adapter;
    private final List<Recipe> recetas = new ArrayList<>();

    private int currentPage = 0;
    private boolean isLoading = false;
    private Long categoryId = null;

    private RecipeNavigation navigation;

    public HomeFragment() {
        super(R.layout.home_fragment);
    }

    /**
     * Creates a new fragment instance with optional category filtering.
     */
    public static HomeFragment newInstance(String categoria, Long categoriaId) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("categoria", categoria);
        args.putSerializable("categoriaId", categoriaId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initializes UI components, search logic, and pagination behavior.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = requireArguments();
        String category = args.getString("categoria");
        categoryId = (Long) args.getSerializable("categoriaId");

        TextView tituloCategoria = view.findViewById(R.id.tvCategoryTitle);

        if (category != null) {
            tituloCategoria.setVisibility(View.VISIBLE);
            tituloCategoria.setText(category.toUpperCase());
        }

        setupSearch(view);
        setupRecycler(view);

        loadNextPage();
    }

    /**
     * Configures the search bar with debounce behavior.
     */
    private void setupSearch(View view) {
        EditText barraBuscador = view.findViewById(R.id.etSearch);

        barraBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = s.toString();

                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                searchRunnable = () -> {
                    currentPage = 0;
                    recetas.clear();
                    adapter.notifyDataSetChanged();
                    loadNextPage();
                };

                searchHandler.postDelayed(searchRunnable, SEARCH_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
    }

    /**
     * Configures RecyclerView, adapter, and infinite scroll listener.
     */
    private void setupRecycler(View view) {
        RecyclerView rvRecetas = view.findViewById(R.id.rvRecipes);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRecetas.setLayoutManager(layoutManager);

        adapter = new RecipeAdapter(recetas, this::openRecipeDetails);
        rvRecetas.setAdapter(adapter);

        rvRecetas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy <= 0) return;

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                // Trigger next page when reaching near the end of the list
                if (!isLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount - 2) {
                    loadNextPage();
                }
            }
        });
    }

    /**
     * Ensures the hosting activity implements RecipeNavigation.
     */
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

    /**
     * Opens the selected recipe detail screen.
     */
    private void openRecipeDetails(Recipe receta) {
        navigation.openRecipeDetails(receta);
    }

    /**
     * Loads the next page of recipes from the API.
     * Applies search and category filters if provided.
     */
    private void loadNextPage() {
        isLoading = true;

        RetrofitClient.getApi()
                .getRecipes(currentPage, 6, search, categoryId)
                .enqueue(new Callback<List<RecipeResponseDto>>() {

                    @Override
                    public void onResponse(
                            Call<List<RecipeResponseDto>> call,
                            Response<List<RecipeResponseDto>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            List<Recipe> nuevas = new ArrayList<>();
                            for (RecipeResponseDto dto : response.body()) {
                                nuevas.add(new Recipe(dto));
                            }

                            adapter.addRecipes(nuevas);
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
