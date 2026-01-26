package com.example.babushka.categorias;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.babushka.R;

import java.util.List;

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

        byte[] bytes = Base64.decode(c.imageBase64, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.img.setImageBitmap(bmp);

        holder.itemView.setOnClickListener(v -> {
            listener.onCategoriaSelected(c.name, android.R.color.black);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
