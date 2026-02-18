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
import com.abik.babushka.recipe.RecipeDetailsFragment;
import com.abik.babushka.recipe.Recipe;
import com.abik.babushka.recipe.RecipeNavigation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

/**
 * MainActivity handles the app's main navigation via drawer and fragment replacement.
 * Implements category selection and recipe navigation.
 */
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

        // Open navigation drawer when toolbar menu button is clicked
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Load HomeFragment on first launch
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment.newInstance(null, null));
        }

        // Handle navigation drawer item clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_home) replaceFragment(HomeFragment.newInstance(null, null));
            else if (id == R.id.menu_categories) replaceFragment(new CategoryFragment());
            else if (id == R.id.menu_profile) replaceFragment(new ProfileFragment());
            else if (id == R.id.menu_create_recipe) replaceFragment(new CreateRecipeFragment());

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    /**
     * Replace the main fragment container with the given fragment.
     * Adds the transaction to the back stack to allow navigation back.
     */
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .addToBackStack(null) // Allow returning from detail fragments without closing the app
                .commit();
    }

    // Callback for category selection from CategoryFragment
    @Override
    public void onCategorySelected(String categoria, Long categoryId) {
        replaceFragment(HomeFragment.newInstance(categoria, categoryId));
    }

    // Callback to open recipe detail fragment
    @Override
    public void openRecipeDetails(Recipe receta) {
        replaceFragment(RecipeDetailsFragment.newInstance(receta));
    }
}
