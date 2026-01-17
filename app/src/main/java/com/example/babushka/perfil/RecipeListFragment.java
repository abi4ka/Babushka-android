package com.example.babushka.perfil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babushka.recipe.RecipeNavigation;
import com.example.babushka.recipe.Receta;
import com.example.babushka.Inicio.RecetaAdapter;
import com.example.babushka.R;
import com.example.babushka.network.ClientResponse;
import com.example.babushka.network.RecipeResponseDto;
import com.example.babushka.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    private static final String ARG_USER_ID = "userId";

    private RecipeListType type;
    private long userId;

    private RecyclerView rv;
    private RecetaAdapter adapter;
    private List<Receta> recetas = new ArrayList<>();

    private int page = 0;
    private static final int PAGE_SIZE = 6;
    private boolean isLoading = false;

    public static RecipeListFragment newInstance(RecipeListType type, long userId) {
        RecipeListFragment f = new RecipeListFragment();
        Bundle b = new Bundle();
        b.putString(ARG_TYPE, type.name());
        b.putLong(ARG_USER_ID, userId);
        f.setArguments(b);
        return f;
    }

    public RecipeListFragment() {
        super(R.layout.fragment_recipe_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        type = RecipeListType.valueOf(getArguments().getString(ARG_TYPE));
        userId = getArguments().getLong(ARG_USER_ID);

        rv = view.findViewById(R.id.rvRecetas);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapter = new RecetaAdapter(recetas, receta -> {
            abrirDetalleReceta(receta);
        });

        rv.setAdapter(adapter);

        loadNextPage();

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                if (dy <= 0) return;

                int visible = lm.getChildCount();
                int total = lm.getItemCount();
                int first = lm.findFirstVisibleItemPosition();

                if (!isLoading && visible + first >= total - 2) {
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

        Call<ClientResponse<List<RecipeResponseDto>>> call;

        if (type == RecipeListType.MY_RECIPES) {
            call = RetrofitClient.getApi()
                    .getMyRecipes(userId, page, PAGE_SIZE);
        } else {
            call = RetrofitClient.getApi()
                    .getFavoriteRecipes(userId, page, PAGE_SIZE);
        }

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ClientResponse<List<RecipeResponseDto>>> call,
                                   Response<ClientResponse<List<RecipeResponseDto>>> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getData() != null) {

                    List<Receta> nuevas = new ArrayList<>();
                    for (RecipeResponseDto dto : response.body().getData()) {
                        nuevas.add(new Receta(dto));
                    }

                    adapter.addRecetas(nuevas);
                    page++;
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<ClientResponse<List<RecipeResponseDto>>> call, Throwable t) {
                isLoading = false;
            }
        });
    }
}

