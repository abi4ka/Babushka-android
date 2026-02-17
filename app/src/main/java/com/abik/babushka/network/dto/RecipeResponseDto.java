package com.abik.babushka.network.dto;

import android.graphics.Bitmap;

public class RecipeResponseDto {
    public Long id;
    public String title;
    public String description;
    public String ingredients;
    public String preparation;
    public int time;
    public int difficulty;
    public Boolean isFavorite;
    public Bitmap imageBase64;

    // Base para crear receta nueva
    public RecipeResponseDto( String title, String description, String ingredients, String preparation, int time, int difficulty, Bitmap imageBase64) {
        this.description = description;
        this.title = title;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.time = time;
        this.difficulty = difficulty;
        this.imageBase64 = imageBase64;
    }
}