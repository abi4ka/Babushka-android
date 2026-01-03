package com.example.babushka.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApi {

    @GET("/recipes")
    Call<List<RecipeResponseDto>> getRecipes(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("/recipes/{id}/image")
    Call<ResponseBody> getRecipeImage(@Path("id") long id);
}
