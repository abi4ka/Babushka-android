package com.example.babushka.categorias;

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

    public CategoriasFragment(MainActivity mainActivity) {
        super(R.layout.fragment_categorias);
        this.mainActivity = mainActivity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //REFERENCIAS A LAS CATEGORIAS

        // HEALTH
        View categoriaHealth = view.findViewById(R.id.categoriaHealth);
        // ENTRANTES
        View categoriaEntrantes = view.findViewById(R.id.categoriaEntrantes);
        // CARNES
        View categoriaCarnes = view.findViewById(R.id.categoriaCarnes);
        // PASTA
        View categoriaPasta = view.findViewById(R.id.categoriaPasta);
        // MAR
        View categoriaMar = view.findViewById(R.id.categoriaMar);
        // ENSALADAS
        View categoriaEnsaladas = view.findViewById(R.id.categoriaEnsaladas);
        // POSTRES
        View categoriaPostres = view.findViewById(R.id.categoriaPostres);
        // VEGETARIANO
        View categoriaVegetariano = view.findViewById(R.id.categoriaVegetariano);
        // VEGANO
        View categoriaVegano = view.findViewById(R.id.categoriaVegano);
        // SIN GLUTEN
        View categoriaSinGluten = view.findViewById(R.id.categoriaSinGluten);


        // Al hacer click, abrir la pantalla de la categoria:

        // HEALTH
        categoriaHealth.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "HEALTH");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"health",R.color.cat_health));
        });

        // ENTRANTES
        categoriaEntrantes.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "ENTRANTES");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"entrantes",R.color.cat_entrantes));
        });

        // CARNES
        categoriaCarnes.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "CARNES");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"carnes",R.color.cat_carnes));

        });

        // PASTA
        categoriaPasta.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "PASTA");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"pasta",R.color.cat_pasta));
        });

        // MAR
        categoriaMar.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "MAR");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"mar", R.color.cat_mar));
        });

        // ENSALADAS
        categoriaEnsaladas.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "ENSALADAS");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"ensaladas", R.color.cat_ensaladas));
        });

        // POSTRES
        categoriaPostres.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "POSTRES");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"postres", R.color.cat_postres));
        });

        // VEGETARIANO
        categoriaVegetariano.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "VEGETARIANO");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"vegetariano", R.color.cat_vegetariano));
        });

        // VEGANO
        categoriaVegano.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "VEGANO");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"vegano", R.color.cat_vegano));

        });

        // SIN GLUTEN
        categoriaSinGluten.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
//            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "SIN GLUTEN");
//            startActivity(intent);
            mainActivity.replaceFragment(new InicioFragment(mainActivity,"sin_gluten", R.color.cat_sin_gluten));
        });
    }
}
