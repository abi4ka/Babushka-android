package com.abik.babushka.profile;

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

import com.abik.babushka.R;
import com.abik.babushka.network.RetrofitClient;
import com.abik.babushka.network.dto.UserInfoDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment that displays user profile information,
 * including created and favorite recipes.
 */
public class ProfileFragment extends Fragment {

    private TextView tvUsername, tvCreatedCount, tvFavoriteCount;
    private View indicatorCreated, indicatorFavorite;

    public ProfileFragment() {
        super(R.layout.profile_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tvUsername = view.findViewById(R.id.usernameProfile);
        tvCreatedCount = view.findViewById(R.id.createdCountProfile);
        tvFavoriteCount = view.findViewById(R.id.favoriteCountProfile);

        indicatorCreated = view.findViewById(R.id.indicatorCreatedProfile);
        indicatorFavorite = view.findViewById(R.id.indicatorFavoriteProfile);

        LinearLayout createdContainer = view.findViewById(R.id.createdContainerProfile);
        LinearLayout favoriteContainer = view.findViewById(R.id.favoriteContainerProfile);
        ViewPager2 pager = view.findViewById(R.id.viewPagerProfile);

        // Load user profile information from backend
        RetrofitClient.getApi()
                .getUserInfo()
                .enqueue(new Callback<UserInfoDto>() {
                    @Override
                    public void onResponse(Call<UserInfoDto> call,
                                           Response<UserInfoDto> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UserInfoDto user = response.body();

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

        // Switch pages when tapping on section headers
        createdContainer.setOnClickListener(v -> pager.setCurrentItem(0, true));
        favoriteContainer.setOnClickListener(v -> pager.setCurrentItem(1, true));

        // Configure ViewPager with two pages: created recipes and favorites
        pager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return position == 0
                        ? RecipeListFragment.newInstance(RecipeListType.MY_RECIPES)
                        : RecipeListFragment.newInstance(RecipeListType.FAVORITES);
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        // Update visual indicator when page changes
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

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
