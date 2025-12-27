package com.example.babushka;


import java.io.Serializable;
// Implementamos Serializable para poder enviar objetos Receta entre fragments usando Bundle

public class Receta implements Serializable {

    String nombre;
    String descripcion;
    String dificultad;
    String ingredientes;
    String preparacion;
    String imagen;


    public Receta(String nombre, String descripcion, String dificultad, String ingredientes, String preparacion, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.preparacion = preparacion;
        this.ingredientes = ingredientes;
        this.imagen = imagen;
    }
}
