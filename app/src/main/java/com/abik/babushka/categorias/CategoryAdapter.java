package com.abik.babushka.categorias;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

// Importaciones del proyecto
import com.abik.babushka.R;
import com.abik.babushka.network.RecipeApi;
import com.abik.babushka.network.RetrofitClient;
import com.abik.babushka.network.dto.CategoryDto;

import java.util.List;

// Importaciones para manejar llamadas HTTP con Retrofit
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Adaptador para el RecyclerView que muestra las categorías
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<CategoryDto> list;
    OnCategoriaSelected listener;

    // Constructor del adaptador
    public CategoryAdapter(List<CategoryDto> list, OnCategoriaSelected listener) {
        this.list = list;
        this.listener = listener;
    }

    // ViewHolder: contiene las vistas de cada item del RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;

        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imgCategory);
            txt = v.findViewById(R.id.txtCategory);
        }
    }

    // Crea cada fila (item) del RecyclerView
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_categoria, parent, false);
        return new ViewHolder(v);
    }

    // Asigna los datos a cada fila del RecyclerView
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Obtiene la categoría actual
        CategoryDto c = list.get(position);

        // Muestra el nombre de la categoría
        holder.txt.setText(c.name);

        holder.img.setImageDrawable(null);

        RecipeApi api = RetrofitClient.getApi();

        // Llama al servidor para descargar la imagen de la categoría
        api.getCategoryImage(c.id).enqueue(new Callback<ResponseBody>() {

            // Si la respuesta es correcta
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // Convierte la imagen descargada en un Bitmap y la asigna al ImageView
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    holder.img.setImageBitmap(bmp);
                }
            }

            //Si hay un error en la conexión
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // Detecta cuando el usuario pulsa una categoría
        holder.itemView.setOnClickListener(v -> {
            listener.onCategoriaSelected(c.name, c.id);
        });
    }

    // Devuelve cuántos elementos hay en la lista
    @Override
    public int getItemCount() {
        return list.size();
    }
}
