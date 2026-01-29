package com.example.babushka.recipe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babushka.R;
import com.example.babushka.network.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.ViewHolder> {
    private List<Receta> listaReceta;
    private OnRecetaClickListener listener;

    // Interfaz para comunicar el click al Fragment (el Adapter NO abre fragments, solo avisa)
    public interface OnRecetaClickListener {
        void onRecetaClick(Receta receta);
    }

    public RecetaAdapter(List<Receta> listaReceta, OnRecetaClickListener listener) {
        this.listaReceta = listaReceta;
        this.listener = listener;
    }

    // Contar total de Recetas
    @Override
    public int getItemCount() {
        return listaReceta.size();
    }

    // Crear mini Receta
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_receta, parent, false);
        return new ViewHolder(view);
    }

    // Asignar valores a variables que luego se visualizarán
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receta receta = listaReceta.get(position);

        holder.nombre.setText(receta.title);
        holder.descrip.setText(receta.description);
        holder.dificult.setText("" + receta.difficulty);
        holder.tiempo.setText("" + receta.time);

        // "" + ... convierte a String (con int da error)

        // Descargar imagen
        RetrofitClient.getApi()
                .getRecipeImage(receta.id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Bitmap bitmap = BitmapFactory.decodeStream(
                                    response.body().byteStream()
                            );
                            receta.bitmapImage = bitmap;
                            holder.imagen.setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        //  Inicio de estrella según isFavorite
        updateStar(holder.estrella, receta);

        // Click en la estrella (añadir/quitar de favoritos)
        holder.estrella.setOnClickListener(v -> {
            miniFavorite(holder.estrella, receta);
        });

        // Cuando se hace click en una mini receta,
        // Avisamos al Fragment y le pasamos la receta clicada
        holder.itemView.setOnClickListener(v -> {
            listener.onRecetaClick(receta);
        });
    }

    public void addRecetas(List<Receta> nuevas) {
        int start = listaReceta.size();
        listaReceta.addAll(nuevas);
        notifyItemRangeInserted(start, nuevas.size());
    }

    // Actualiza el icono de la estrella según si es favorita o no
    private void updateStar(ImageView estrella, Receta receta) {
        if (receta.isFavorite) {
            estrella.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            estrella.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    // Peticion para marcar/desmarcar favorito
    private void miniFavorite(ImageView estrella, Receta receta) {
        RetrofitClient.getApi()
                .postFavoriteRecipes(receta.id, !receta.isFavorite)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call,
                                           Response<Void> response) {
                        // Solo cambiamos el estado si el servidor responde bien
                        receta.isFavorite = !receta.isFavorite;
                        updateStar(estrella, receta);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    // Asignar visualización de información
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descrip, dificult, tiempo;
        ImageView imagen, estrella;

        ViewHolder(View view) {
            super(view);
            nombre = view.findViewById(R.id.txNombre);
            descrip = view.findViewById(R.id.txDescripcion);
            dificult = view.findViewById(R.id.tvDificultad);
            imagen = view.findViewById(R.id.vwImagen);
            estrella = view.findViewById(R.id.Estrella);
            tiempo = view.findViewById(R.id.tvTiempo);
        }
    }
}