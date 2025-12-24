package com.example.babushka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CategoriasFragment extends Fragment {
    public CategoriasFragment() {
        super(R.layout.fragment_categorias);
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
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "HEALTH");
            startActivity(intent);
        });

        // ENTRANTES
        categoriaEntrantes.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "ENTRANTES");
            startActivity(intent);
        });

        // CARNES
        categoriaCarnes.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "CARNES");
            startActivity(intent);
        });

        // PASTA
        categoriaPasta.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "PASTA");
            startActivity(intent);
        });

        // MAR
        categoriaMar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "MAR");
            startActivity(intent);
        });

        // ENSALADAS
        categoriaEnsaladas.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "ENSALADAS");
            startActivity(intent);
        });

        // POSTRES
        categoriaPostres.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "POSTRES");
            startActivity(intent);
        });

        // VEGETARIANO
        categoriaVegetariano.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "VEGETARIANO");
            startActivity(intent);
        });

        // VEGANO
        categoriaVegano.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "VEGANO");
            startActivity(intent);
        });

        // SIN GLUTEN
        categoriaSinGluten.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoriasDetailActivity.class);
            intent.putExtra(CategoriasDetailActivity.EXTRA_CATEGORY_ID, "SIN GLUTEN");
            startActivity(intent);
        });
    }
}
