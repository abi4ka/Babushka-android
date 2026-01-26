package com.example.babushka.categorias;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.babushka.R;
import com.example.babushka.network.RecipeApi;
import com.example.babushka.network.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<CategoryDto> list;
    OnCategoriaSelected listener;

    public CategoryAdapter(List<CategoryDto> list, OnCategoriaSelected listener) {
        this.list = list;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;

        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imgCategory);
            txt = v.findViewById(R.id.txtCategory);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CategoryDto c = list.get(position);
        holder.txt.setText(c.name);

        holder.img.setImageDrawable(null);

        RecipeApi api = RetrofitClient.getApi();
        api.getCategoryImage(c.id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    holder.img.setImageBitmap(bmp);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

        holder.itemView.setOnClickListener(v -> {
            listener.onCategoriaSelected(c.name, android.R.color.black);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
