package com.example.babushka;


import android.graphics.Bitmap;

import java.io.Serializable;
// Implementamos Serializable para poder enviar objetos Receta entre fragments usando Bundle

public class Receta implements Serializable {
    Long id;
    String nombre;
    String descripcion;
    String dificultad;
    String ingredientes;
    String preparacion;
    String imagen;
    Bitmap bitmapImage;

    public Receta(Long id,
                  String nombre,
                  String descripcion,
                  String dificultad,
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