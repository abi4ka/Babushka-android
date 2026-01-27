package com.example.babushka.recipe;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babushka.R;

public class CrearRecetaFragment extends Fragment {
    private Receta receta;

    public CrearRecetaFragment() {
        super(R.layout.crear_receta);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // EditText del xml
        EditText nombre = view.findViewById(R.id.etNombre);
        EditText tiempo = view.findViewById(R.id.etTiempo);
        EditText dificultad = view.findViewById(R.id.etDificultad);
        EditText descripcion = view.findViewById(R.id.etDescripcion);
        EditText ingretientes = view.findViewById(R.id.etIngredientes);
        EditText preparacion = view.findViewById(R.id.etPreparacion);
    }


}
