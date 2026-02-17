package com.abik.babushka.recipe;

import com.abik.babushka.network.RetrofitClient;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abik.babushka.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailFragment extends Fragment {

    private Recipe receta;

    public RecipeDetailFragment() {
        super(R.layout.recipe);
    }

    public static RecipeDetailFragment newInstance(Recipe receta) {
        // Recibimos receta
        // Creamos nuevo fragment y Bundle para receta
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();

        // Guardamos receta en args
        args.putSerializable("receta", receta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recuperamos la receta enviada desde InicioFragment
        receta = (Recipe) requireArguments().getSerializable("receta");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nombre = view.findViewById(R.id.txNombre);
        TextView tiempo = view.findViewById(R.id.tvTiempo);
        TextView dificultad = view.findViewById(R.id.tvDificultad);
        TextView descripcion = view.findViewById(R.id.txDescripcion);
        TextView ingredientes = view.findViewById(R.id.txIngredientes);
        TextView preparacion = view.findViewById(R.id.txPreparacion);
        ImageView imagen = view.findViewById(R.id.vwImagen);
        ImageView estrella = view.findViewById(R.id.Estrella);


        // Mostramos los datos
        nombre.setText(receta.title);
        tiempo.setText("" + receta.time);
        dificultad.setText("" + receta.difficulty);
        descripcion.setText(receta.description);
        ingredientes.setText(ingredientesBetterLook(receta.ingredients));
        preparacion.setText(receta.preparation);
        Bitmap bitmap = receta.bitmapImage;

        if (bitmap != null) {
            imagen.setImageBitmap(bitmap);
        }

        // Estado inicial de la estrella
        updateStar(estrella);

        // Click en estrella (añadir/quitar de favoritos)
        estrella.setOnClickListener(v -> {
            favorite(estrella);
        });
    }

    // Actualiza el icono de la estrella según si es favorita o no
    private void updateStar(ImageView estrella) {
        if (receta.isFavorite) {
            estrella.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            estrella.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    // Pasar ingredientes a listado en receta
    public String ingredientesBetterLook(String ingredientes) {
        StringBuilder new_ingredientes = new StringBuilder();

        String[] ingredientes_list = ingredientes.split(", ");

        for (String ingredient : ingredientes_list) {
            new_ingredientes.append("▶ ")
                    .append(Character.toUpperCase(ingredient.charAt(0)))
                    .append(ingredient.substring(1))
                    .append("\n");
        }
        return new_ingredientes.toString();
    }

    private void favorite(ImageView estrella) {

        RetrofitClient.getApi()
                .postFavoriteRecipes(receta.id, !receta.isFavorite)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        // Desmarcar de favorito si el servidor responde bien
                        receta.isFavorite = !receta.isFavorite;
                        updateStar(estrella);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}

