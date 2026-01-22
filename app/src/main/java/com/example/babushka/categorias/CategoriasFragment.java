package com.example.babushka.categorias;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babushka.R;

public class CategoriasFragment extends Fragment {
    private OnCategoriaSelected listener;

    public CategoriasFragment() {
        super(R.layout.fragment_categorias);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoriaSelected) {
            listener = (OnCategoriaSelected) context;
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
            listener.onCategoriaSelected("health");
        });

        categoriaEntrantes.setOnClickListener(v -> {
            listener.onCategoriaSelected("entrantes");
        });

        categoriaCarnes.setOnClickListener(v -> {
            listener.onCategoriaSelected("carnes");
        });

        categoriaPasta.setOnClickListener(v -> {
            listener.onCategoriaSelected("pasta");
        });

        categoriaMar.setOnClickListener(v -> {
            listener.onCategoriaSelected("mar");
        });

        categoriaEnsaladas.setOnClickListener(v -> {
            listener.onCategoriaSelected("ensaladas");
        });

        categoriaPostres.setOnClickListener(v -> {
            listener.onCategoriaSelected("postres");
        });

        categoriaVegetariano.setOnClickListener(v -> {
            listener.onCategoriaSelected("vegetariano");
        });

        categoriaVegano.setOnClickListener(v -> {
            listener.onCategoriaSelected("vegano");
        });

        categoriaSinGluten.setOnClickListener(v -> {
            listener.onCategoriaSelected("sin_gluten");
        });
    }
}
