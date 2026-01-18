package com.example.babushka.recipe;


import android.graphics.Bitmap;

import com.example.babushka.network.RecipeResponseDto;

import java.io.Serializable;
// Implementamos Serializable para poder enviar objetos Receta entre fragments usando Bundle
public class Receta implements Serializable {
    public Long id;
    public String title;
    public String description;
    public int difficulty;
    public String ingredients;
    public String preparation;
    public Bitmap bitmapImage;
    public boolean isFavorite;

    public Receta(Long id,
                  String title,
                  String description,
                  int difficulty,
                  String ingredients,
                  String preparation,
                  boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.preparation = preparation;
        this.ingredients = ingredients;
        this.isFavorite = isFavorite;
    }

    public Receta(RecipeResponseDto dto){
        this.id = dto.id;
        this.title = dto.title;
        this.description = dto.description;
        this.difficulty = dto.difficulty;
        this.preparation = dto.preparation;
        this.ingredients = dto.ingredients;
        this.isFavorite = dto.favorite;
    }
}