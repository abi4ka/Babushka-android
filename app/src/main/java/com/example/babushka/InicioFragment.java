package com.example.babushka;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    public InicioFragment() {
        super(R.layout.fragment_inicio);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvRecetas = view.findViewById(R.id.rvRecetas);

        // Layout vertical
        rvRecetas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Recetas del server (ahora puse 6)
        List<Receta> recetas = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            recetas.add(new Receta(
                    "Tarta de queso",
                    "Una tarta de queso cremosa y suave, perfecta como postre tradicional.",
                    "3"
            ));
        }

        RecetaAdapter adapter = new RecetaAdapter(recetas);
        rvRecetas.setAdapter(adapter);
    }

}
