package com.abik.babushka.recipe;


import android.graphics.Bitmap;

import com.abik.babushka.network.dto.RecipeResponseDto;

import java.io.Serializable;

public class Recipe implements Serializable {
    public Long id;
    public String title;
    public String description;
    public String ingredients;
    public String preparation;
    public int time;
    public int difficulty;
    public Boolean isFavorite;
    public Bitmap bitmapImage;

    // to receive recipe from backend
    public Recipe(Long id,
                  String title,
                  String description,
                  String ingredients,
                  String preparation,
                  int time,
                  int difficulty,
                  boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.time = time;
        this.difficulty = difficulty;
        this.isFavorite = isFavorite;
    }

    // to create a new recipe
    public Recipe(RecipeResponseDto dto) {
        this.id = dto.id;
        this.title = dto.title;
        this.description = dto.description;
        this.ingredients = dto.ingredients;
        this.preparation = dto.preparation;
        this.time = dto.time;
        this.difficulty = dto.difficulty;
        this.isFavorite = dto.isFavorite;
    }
}