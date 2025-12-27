package com.example.babushka;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetalleFragment extends Fragment {

    private Receta receta;

    public DetalleFragment(Receta receta) {
        super(R.layout.receta);
        this.receta = receta;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recuperamos la receta enviada desde InicioFragment


        // Referencias a TextView del layout
        TextView nombre = view.findViewById(R.id.txNombre);
        TextView descripcion = view.findViewById(R.id.txDescripcion);
        TextView ingredientes = view.findViewById(R.id.txIngredientes);
        TextView preparacion = view.findViewById(R.id.txPreparacion);
        ImageView imagen = view.findViewById(R.id.vwImagen);

        // Mostramos los datos
        nombre.setText(receta.nombre);
        descripcion.setText(receta.descripcion);
        ingredientes.setText(receta.ingredientes);
        preparacion.setText(receta.preparacion);

        // Convertirmos BASE64 a BITMAP
        //Bitmap bitmap = ImagenBase.base64ToBitmap(receta.imagen);
        Bitmap bitmap = receta.bitmapImage;

        if (bitmap != null) {
          imagen.setImageBitmap(bitmap);
        }
    }
}

