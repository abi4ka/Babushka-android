package com.example.babushka;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.ViewHolder> {

    private List<Receta> listaReceta;
    private OnRecetaClickListener listener;


    // Interfaz para comunicar el click al Fragment
    // (el Adapter NO abre fragments, solo avisa)
    public interface OnRecetaClickListener {
        void onRecetaClick(Receta receta);
    }

//Constructor
    public RecetaAdapter(List<Receta> listaReceta, OnRecetaClickListener listener){
        this.listaReceta = listaReceta;
        this.listener = listener;
    }

// Contar total de Recetas
    @Override
    public int getItemCount(){
        return listaReceta.size();
    }

    // Crear mini Receta
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_receta, parent, false);
        return new ViewHolder(view);
    }

    // Asignar valor a variables que luego se visualizarán
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receta receta = listaReceta.get(position);

        holder.nombre.setText(receta.nombre);
        holder.descrip.setText(receta.descripcion);
        holder.dificult.setText(receta.dificultad);

        /*
         * Cuando se hace click en una mini receta,
         * avisamos al Fragment y le pasamos la receta clicada
         */
        holder.itemView.setOnClickListener(v -> {
            listener.onRecetaClick(receta);
        });
    }

    public void addRecetas(List<Receta> nuevas) {
        int start = listaReceta.size();
        listaReceta.addAll(nuevas);
        notifyItemRangeInserted(start, nuevas.size());
    }

// Asignar visualización de información
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, descrip, dificult;
        ViewHolder(View view){
            super(view);
            nombre = view.findViewById(R.id.txNombre);
            descrip = view.findViewById(R.id.txDescripcion);
            dificult = view.findViewById(R.id.tvDificultad);
        }

    }





}

