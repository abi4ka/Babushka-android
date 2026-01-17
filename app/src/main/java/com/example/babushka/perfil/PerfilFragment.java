package com.example.babushka.perfil;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.babushka.R;

public class PerfilFragment extends Fragment {

    public PerfilFragment() {
        super(R.layout.fragment_perfil);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager2 pager = view.findViewById(R.id.viewPager);

        long userId = 3L; // current user

        pager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    return RecipeListFragment.newInstance(
                            RecipeListType.MY_RECIPES, userId
                    );
                } else {
                    return RecipeListFragment.newInstance(
                            RecipeListType.FAVORITES, userId
                    );
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });
    }
}
