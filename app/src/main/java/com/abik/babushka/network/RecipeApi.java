package com.abik.babushka.network;

import com.abik.babushka.network.dto.CategoryDto;
import com.abik.babushka.network.dto.LoginResponseDto;
import com.abik.babushka.network.dto.RecipeResponseDto;
import com.abik.babushka.network.dto.UserDto;
import com.abik.babushka.network.dto.UserInfoDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit API definition for all backend endpoints.
 */
public interface RecipeApi {

    /**
     * Retrieves paginated recipes with optional search and category filtering.
     */
    @GET("/recipes")
    Call<List<RecipeResponseDto>> getRecipes(
            @Query("page") int page,
            @Query("size") int size,
            @Query("search") String search,
            @Query("categoryId") Long categoryId
    );

    /**
     * Retrieves the image of a specific recipe.
     */
    @GET("/recipes/{id}/image")
    Call<ResponseBody> getRecipeImage(@Path("id") long id);

    /**
     * Creates a new recipe.
     */
    @POST("/recipes")
    Call<RecipeResponseDto> createRecipe(@Body RecipeResponseDto receta);

    /**
     * Retrieves paginated recipes created by the authenticated user.
     */
    @GET("/users/me/recipes")
    Call<List<RecipeResponseDto>> getMyRecipes(
            @Query("page") int page,
            @Query("size") int size
    );

    /**
     * Retrieves paginated favorite recipes of the authenticated user.
     */
    @GET("/users/me/favorites")
    Call<List<RecipeResponseDto>> getFavoriteRecipes(
            @Query("page") int page,
            @Query("size") int size
    );

    /**
     * Retrieves information about the authenticated user.
     */
    @GET("/users/me/info")
    Call<UserInfoDto> getUserInfo();

    /**
     * Marks or unmarks a recipe as favorite.
     */
    @POST("/recipes/{id}/favorite")
    Call<Void> postFavoriteRecipes(
            @Path("id") long recipeId,
            @Query("favorite") boolean favorite
    );

    /**
     * Authenticates a user and returns a session token.
     */
    @POST("/sessions")
    Call<LoginResponseDto> login(@Body UserDto user);

    /**
     * Registers a new user.
     */
    @POST("/users")
    Call<ResponseBody> register(@Body UserDto user);

    /**
     * Retrieves all available categories.
     */
    @GET("/categories")
    Call<List<CategoryDto>> getCategories();

    /**
     * Retrieves the image of a specific category.
     */
    @GET("/categories/{id}/image")
    Call<ResponseBody> getCategoryImage(@Path("id") Long id);
}
