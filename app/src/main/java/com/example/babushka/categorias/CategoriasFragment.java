package com.example.babushka.categorias;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babushka.Inicio.InicioFragment;
import com.example.babushka.MainActivity;
import com.example.babushka.R;

public class CategoriasFragment extends Fragment {
    private MainActivity mainActivity;

    public CategoriasFragment() {
        super(R.layout.fragment_categorias);
        this.mainActivity = mainActivity;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //REFERENCIAS A LAS CATEGORIAS
        View categoriaHealth = view.findViewById(R.id.categoriaHealth);
        View categoriaEntrantes = view.findViewById(R.id.categoriaEntrantes);
        View categoriaCarnes = view.findViewById(R.id.categoriaCarnes);

        View categoriaPasta = view.findViewById(R.id.categoriaPasta);
        View categoriaMar = view.findViewById(R.id.categoriaMar);
        View categoriaEnsaladas = view.findViewById(R.id.categoriaEnsaladas);

        View categoriaPostres = view.findViewById(R.id.categoriaPostres);
        View categoriaVegetariano = view.findViewById(R.id.categoriaVegetariano);
        View categoriaVegano = view.findViewById(R.id.categoriaVegano);

        View categoriaSinGluten = view.findViewById(R.id.categoriaSinGluten);


        // Al hacer click, abrir la pantalla de la categoria:
        categoriaHealth.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("health",R.color.cat_health));
        });

        categoriaEntrantes.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("entrantes",R.color.cat_entrantes));
        });

        categoriaCarnes.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("carnes",R.color.cat_carnes));
        });

        categoriaPasta.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("pasta",R.color.cat_pasta));
        });

        categoriaMar.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("mar", R.color.cat_mar));
        });

        categoriaEnsaladas.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("ensaladas", R.color.cat_ensaladas));
        });

        categoriaPostres.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("postres", R.color.cat_postres));
        });

        categoriaVegetariano.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("vegetariano", R.color.cat_vegetariano));
        });

        categoriaVegano.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("vegano", R.color.cat_vegano));
        });

        categoriaSinGluten.setOnClickListener(v -> {
            mainActivity.replaceFragment(new InicioFragment("sin_gluten", R.color.cat_sin_gluten));
        });
    }
}
