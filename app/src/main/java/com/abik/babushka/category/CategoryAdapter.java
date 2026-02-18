package com.abik.babushka.category;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abik.babushka.R;
import com.abik.babushka.network.RecipeApi;
import com.abik.babushka.network.RetrofitClient;
import com.abik.babushka.network.dto.CategoryDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<CategoryDto> list;
    private final OnCategorySelected listener;

    public CategoryAdapter(List<CategoryDto> list, OnCategorySelected listener) {
        this.list = list;
        this.listener = listener;
    }

    /**
     * ViewHolder representing a single category item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;

        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imgCategory);
            txt = v.findViewById(R.id.tvCategory);
        }
    }

    /**
     * Inflates the layout for each RecyclerView item.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_layout, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Binds category data to the ViewHolder.
     * Loads the category image from the server and handles item click events.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CategoryDto category = list.get(position);

        holder.txt.setText(category.name);
        holder.img.setImageDrawable(null); // Prevents incorrect image reuse

        RecipeApi api = RetrofitClient.getApi();

        api.getCategoryImage(category.id).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    holder.img.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

        holder.itemView.setOnClickListener(v ->
                listener.onCategorySelected(category.name, category.id)
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
