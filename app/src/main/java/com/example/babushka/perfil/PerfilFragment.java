package com.example.babushka.perfil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.babushka.R;
import com.example.babushka.network.RetrofitClient;
import com.example.babushka.network.dto.UserInfoDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {
    private TextView tvUsername, tvCreatedCount, tvFavoriteCount;
    private View indicatorCreated, indicatorFavorite;

    public PerfilFragment() {
        super(R.layout.fragment_perfil);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Inicialización de vistas
        tvUsername = view.findViewById(R.id.tvUsername);
        tvCreatedCount = view.findViewById(R.id.tvCreatedCount);
        tvFavoriteCount = view.findViewById(R.id.tvFavoriteCount);

        indicatorCreated = view.findViewById(R.id.indicatorCreated);
        indicatorFavorite = view.findViewById(R.id.indicatorFavorite);

        LinearLayout createdContainer = view.findViewById(R.id.createdContainer);
        LinearLayout favoriteContainer = view.findViewById(R.id.favoriteContainer);
        ViewPager2 pager = view.findViewById(R.id.viewPager);

        // Petición al backend para obtener información del usuario
        RetrofitClient.getApi()
                .getUserInfo()
                .enqueue(new Callback<UserInfoDto>() {
                    @Override
                    public void onResponse(Call<UserInfoDto> call,
                                           Response<UserInfoDto> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UserInfoDto user = response.body();

                            // Mostramos los datos del usuario en la UI
                            tvUsername.setText(user.username);
                            tvCreatedCount.setText(String.valueOf(user.countCreatedRecipe));
                            tvFavoriteCount.setText(String.valueOf(user.countFavoriteRecipe));
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfoDto> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        // Click para mostrar recetas creadas
        createdContainer.setOnClickListener(v -> {
            pager.setCurrentItem(0, true);
        });

        // Click para mostrar recetas favoritas
        favoriteContainer.setOnClickListener(v -> {
            pager.setCurrentItem(1, true);
        });

        // Adapter del ViewPager con dos fragmentos
        pager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0)
                    return RecipeListFragment.newInstance(RecipeListType.MY_RECIPES);
                else
                    return RecipeListFragment.newInstance(RecipeListType.FAVORITES);
            }

            @Override
            public int getItemCount() {
                // Número de páginas
                return 2;
            }
        });

        // Listener para cambiar el indicador según la página seleccionada
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // Colores
                int active = ContextCompat.getColor(requireContext(), R.color.text_secondary);
                int inactive = Color.TRANSPARENT;

                if (position == 0) {
                    indicatorCreated.setBackgroundColor(active);
                    indicatorFavorite.setBackgroundColor(inactive);
                } else {
                    indicatorCreated.setBackgroundColor(inactive);
                    indicatorFavorite.setBackgroundColor(active);
                }
            }
        });
    }
}
