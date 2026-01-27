package com.example.babushka;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.babushka.Inicio.InicioFragment;
import com.example.babushka.categorias.CategoriasFragment;
import com.example.babushka.categorias.OnCategoriaSelected;
import com.example.babushka.perfil.PerfilFragment;
import com.example.babushka.recipe.CrearRecetaFragment;
import com.example.babushka.recipe.DetalleFragment;
import com.example.babushka.recipe.Receta;
import com.example.babushka.recipe.RecipeNavigation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnCategoriaSelected, RecipeNavigation {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        if (savedInstanceState == null) {
            replaceFragment(InicioFragment.newInstance( null));
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_inicio) replaceFragment(InicioFragment.newInstance( null));
            else if (id == R.id.menu_categorias) replaceFragment(new CategoriasFragment());
            else if (id == R.id.menu_perfil) replaceFragment(new PerfilFragment());
            else if (id == R.id.menu_crear_receta) replaceFragment(new CrearRecetaFragment());
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
    public void onCategoriaSelected(String categoria) {
        replaceFragment(InicioFragment.newInstance(categoria));
    }

    @Override
    public void abrirDetalle(Receta receta) {
        replaceFragment(DetalleFragment.newInstance(receta));
    }
}