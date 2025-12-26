package com.example.babushka;

public class Receta {
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
