package com.example.babushka.Inicio;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babushka.R;

public class DetalleFragment extends Fragment {

    private Receta receta;

    public DetalleFragment() {
        super(R.layout.receta);
    }

    public static DetalleFragment newInstance(Receta receta) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle args = new Bundle();
        args.putSerializable("receta", receta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recuperamos la receta enviada desde InicioFragment
        receta = (Receta) requireArguments().getSerializable("receta");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencias a TextView del layout
        TextView nombre = view.findViewById(R.id.txNombre);
        TextView dificultad = view.findViewById(R.id.tvDificultad);
        TextView descripcion = view.findViewById(R.id.txDescripcion);
        TextView ingredientes = view.findViewById(R.id.txIngredientes);
        TextView preparacion = view.findViewById(R.id.txPreparacion);
        ImageView imagen = view.findViewById(R.id.vwImagen);


        // Mostramos los datos
        nombre.setText(receta.nombre);
        dificultad.setText("Dificultad " + receta.dificultad);
        descripcion.setText(receta.descripcion);
        ingredientes.setText(ingredientesBetterLook(receta.ingredientes));
        preparacion.setText(receta.preparacion);

        // Convertirmos BASE64 a BITMAP
        //Bitmap bitmap = ImagenBase.base64ToBitmap(receta.imagen);
        Bitmap bitmap = receta.bitmapImage;

        if (bitmap != null) {
          imagen.setImageBitmap(bitmap);
        }
    }

    //Pasar ingredientes a listado en receta
    public String ingredientesBetterLook(String ingredientes){
        StringBuilder new_ingredientes = new StringBuilder();

        String[] ingredientes_list = ingredientes.split(", ");

        for (String ingredient : ingredientes_list){
            new_ingredientes.append("▶ ")
                    .append(Character.toUpperCase(ingredient.charAt(0)))
                    .append(ingredient.substring(1))
                    .append("\n");
        }

        return new_ingredientes.toString();
    }
}

