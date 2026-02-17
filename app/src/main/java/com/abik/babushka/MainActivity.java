package com.abik.babushka;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.abik.babushka.home.HomeFragment;
import com.abik.babushka.category.CategoryFragment;
import com.abik.babushka.category.OnCategorySelected;
import com.abik.babushka.profile.ProfileFragment;
import com.abik.babushka.recipe.CreateRecipeFragment;
import com.abik.babushka.recipe.RecipeDetailFragment;
import com.abik.babushka.recipe.Recipe;
import com.abik.babushka.recipe.RecipeNavigation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnCategorySelected, RecipeNavigation {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment.newInstance( null, null));
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_inicio) replaceFragment(HomeFragment.newInstance( null, null));
            else if (id == R.id.menu_categorias) replaceFragment(new CategoryFragment());
            else if (id == R.id.menu_perfil) replaceFragment(new ProfileFragment());
            else if (id == R.id.menu_crear_receta) replaceFragment(new CreateRecipeFragment());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .addToBackStack(null) //Para salir de la receta grande sin cerrar aplicación
                .commit();
    }

    @Override
    public void onCategoriaSelected(String categoria, Long categoryId) {
        replaceFragment(HomeFragment.newInstance(categoria, categoryId));
    }

    @Override
    public void abrirDetalle(Recipe receta) {
        replaceFragment(RecipeDetailFragment.newInstance(receta));
    }
}