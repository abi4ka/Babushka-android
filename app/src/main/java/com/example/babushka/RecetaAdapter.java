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

//Constructor
    public RecetaAdapter(List<Receta> listaReceta){
        this.listaReceta = listaReceta;
    }

// Contar total de Recetas
    @Override
    public int getItemCount(){
        return listaReceta.size();
    }

// Asignar valor a variables que luego se visualizarán
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receta receta = listaReceta.get(position);

        holder.nombre.setText(receta.nombre);
        holder.descrip.setText(receta.descripcion);
        holder.dificult.setText(receta.dificultad);
    }

// Crear mini Receta
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_receta, parent, false);
        return new ViewHolder(view);
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

