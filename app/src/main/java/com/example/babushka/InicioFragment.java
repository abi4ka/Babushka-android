package com.example.babushka;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

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

    private RecyclerView rvRecetas;
    private RecetaAdapter adapter;
    private List<Receta> recetas = new ArrayList<>();

    private int currentPage = 0;
    private final int PAGE_SIZE = 6;
    private boolean isLoading = false;

    public InicioFragment() {
        super(R.layout.fragment_inicio);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRecetas = view.findViewById(R.id.rvRecetas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRecetas.setLayoutManager(layoutManager);

        adapter = new RecetaAdapter(recetas);
        rvRecetas.setAdapter(adapter);

        loadNextPage();

        // Scroll que "pide" más elementos una vez se llegue al final
        rvRecetas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy <= 0) return; //Comprobamos en que elemento estamos (si estamos en el último no se hace nada más)

                //Creación de variables para controlar...
                int visibleItemCount = layoutManager.getChildCount();                //... nº items en pantalla
                int totalItemCount = layoutManager.getItemCount();                   //... nº total items
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition(); //...posición del  primer item visible en el Scroll

                // Si cumple condiciones de variables se crea otro elemento que se mostrará en el Scroll
                if (!isLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount - 2) {
                    loadNextPage();
                }
            }
        });
    }

    //Similación de peticiones (luego implementamos la API)
    private void loadNextPage() {
        isLoading = true;

        List<Receta> nuevas = new ArrayList<>();

        for (int i = 0; i < PAGE_SIZE; i++) {
            nuevas.add(new Receta(
                    "Tarta de queso",
                    "Una tarta de queso cremosa y suave, perfecta como postre tradicional.",
                    "2",
                    "Galletas tipo María, mantequilla, queso crema, azúcar, huevos, nata líquida y vainilla.",
                    "Triturar las galletas y mezclarlas con mantequilla derretida. Forrar la base del molde. Batir el queso crema con el azúcar, añadir los huevos uno a uno, la nata y la vainilla. Verter la mezcla sobre la base y hornear a 170°C durante 50 minutos.",
                    null
            ));
        }

        adapter.addRecetas(nuevas);
        currentPage++;
        isLoading = false;
    }

}
