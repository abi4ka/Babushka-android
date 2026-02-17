package com.abik.babushka.recipe;


import android.graphics.Bitmap;

import com.abik.babushka.network.dto.RecipeResponseDto;

import java.io.Serializable;

public class Recipe implements Serializable {
    public Long id;
    public String title;
    public String description;
    public String ingredientsList;
    public String preparationSteps;
    public int time;
    public int difficulty;
    public Boolean isFavorite;
    public Bitmap bitmapImage;

    public Recipe(RecipeResponseDto dto) {
        this.id = dto.id;
        this.title = dto.title;
        this.description = dto.description;
        this.ingredientsList = dto.ingredients;
        this.preparationSteps = dto.preparation;
        this.time = dto.time;
        this.difficulty = dto.difficulty;
        this.isFavorite = dto.isFavorite;
    }
}