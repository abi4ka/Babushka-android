package com.example.babushka;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

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
            replaceFragment(new CategoriasFragment());
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_inicio) replaceFragment(new CategoriasFragment());
            else if (id == R.id.menu_categorias) replaceFragment(new InicioFragment());
            else if (id == R.id.menu_perfil) replaceFragment(new PerfilFragment());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }
}
