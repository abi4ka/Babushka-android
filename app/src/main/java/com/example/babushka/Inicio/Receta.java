package com.example.babushka.Inicio;


import android.graphics.Bitmap;

import java.io.Serializable;
// Implementamos Serializable para poder enviar objetos Receta entre fragments usando Bundle

public class Receta implements Serializable {
    public Long id;
    public String nombre;
    public String descripcion;
    public int dificultad;
    public String ingredientes;
    public String preparacion;
    public Bitmap bitmapImage;

    public Receta(Long id,
                  String nombre,
                  String descripcion,
                  int dificultad,
                  String ingredientes,
                  String preparacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.preparacion = preparacion;
        this.ingredientes = ingredientes;
    }
}