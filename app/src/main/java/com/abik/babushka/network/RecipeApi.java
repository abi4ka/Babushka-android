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

public interface RecipeApi {

    /**
     * Peticion sacar todas recetas
     */
    @GET("/recipes")
    Call<List<RecipeResponseDto>> getRecipes(
            @Query("page") int page,
            @Query("size") int size,
            @Query("search") String search,
            @Query("categoryId") Long categoryId);

    /**
     * Peticion imagen de la receta
     */
    @GET("/recipes/{id}/image")
    Call<ResponseBody> getRecipeImage(
            @Path("id") long id);

    /*
     * Petición para publicar receta nueva
     * */
    @POST("/recipes")
    Call<RecipeResponseDto> createRecipe(
            @Body RecipeResponseDto receta
    );

    /**
     * Peticion sacar todos recetas creados por usuario
     */
    @GET("/users/me/recipes")
    Call<List<RecipeResponseDto>> getMyRecipes(
            @Query("page") int page,
            @Query("size") int size);

    /**
     * Peticion sacar todos recetas favoritas del usuario
     */
    @GET("/users/me/favorites")
    Call<List<RecipeResponseDto>> getFavoriteRecipes(
            @Query("page") int page,
            @Query("size") int size);

    /**
     * Peticion para sacar datos sobre usuario
     */
    @GET("/users/me/info")
    Call<UserInfoDto> getUserInfo();

    /**
     * Peticion para marcar/desmarcar favorito
     */
    @POST("/recipes/{id}/favorite")
    Call<Void> postFavoriteRecipes(
            @Path("id") long recipeId,
            @Query("favorite") boolean favorite);

    /*Este metodo hace una petición HTTP POST a la ruta /sessions del backend de Django,
     * que es la que maneja el login.
     * - Call <LoginResponseDto> es el tipo de respuesta que se recibe. "Call" se usa para manejar llamadas HTTP
     * asíncronas.
     * - LoginResponseDto es la clase que hemos creado que mapea el JSON que devuelve Django, en este caso, solo el token.
     * - login(@Body UserDto user) define el metodo login que acepta un objeto UserDto como cuerpo de la petición.
     * - @Body indica que Retrofit tomará ese objeto y lo convertirá a JSON automáticamente.
     * - UserDto es el Dto que hemos creado para el usuario.
     */
    @POST("/sessions")
    Call<LoginResponseDto> login(@Body UserDto user);

    /*Este metodo hace una petición HTTP POST a la ruta /users del backend de Django,
     * que es la que maneja el registro.
     * - Call <ResponseBody> es el tipo de respuesta que se recibe.
     * - ResponseBody es una clase genérica que recibe el cuerpo de la respuesta tal cual.
     * - login(@Body UserDto user) define el metodo register que acepta un objeto UserDto como cuerpo de la petición.
     */
    @POST("/users")
    Call<ResponseBody> register(@Body UserDto user);

    // Petición GET al endpoint "/categories"
    // Devuelve una lista de categorías
    @GET("/categories")
    Call<List<CategoryDto>> getCategories();

    // Petición GET al endpoint "/categories/{id}/image"
    // Recibe el id de una categoría y devuelve su imagen
    @GET("/categories/{id}/image")
    Call<ResponseBody> getCategoryImage(@Path("id") Long id);

}
