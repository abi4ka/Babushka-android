package com.example.babushka.network;

import com.example.babushka.categorias.CategoryImageDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApi {

    @GET("/recipe/get-page")
    Call<List<RecipeResponseDto>> getRecipes(
            @Query("page") int page,
            @Query("size") int size,
            @Query("search") String search
    );

    @GET("/recipe/{id}/get-image")
    Call<ResponseBody> getRecipeImage(@Path("id") long id);


    /*Este metodo hace una petición HTTP POST a la ruta /sessions del backend de Django,
    que es la que maneja el login.
    - Call <LoginResponseDto> es el tipo de respuesta que se recibe. "Call" se usa para manejar llamadas HTTP
    asíncronas.
    - LoginResponseDto es la clase que hemos creado que mapea el JSON que devuelve Django, en este caso, solo el token.
    - login(@Body UserDto user) define el metodo login que acepta un objeto UserDto como cuerpo de la petición.
    - @Body indica que Retrofit tomará ese objeto y lo convertirá a JSON automáticamente.
    - UserDto es el Dto que hemos creado para el usuario.
    */

    @POST("/sessions")
    Call<LoginResponseDto> login(@Body UserDto user);

    /*Este metodo hace una petición HTTP POST a la ruta /users del backend de Django,
  que es la que maneja el registro.
  - Call <ResponseBody> es el tipo de respuesta que se recibe.
  - ResponseBody es una clase genérica que recibe el cuerpo de la respuesta tal cual.
  - login(@Body UserDto user) define el metodo register que acepta un objeto UserDto como cuerpo de la petición.
  */
    @POST("/users")
    Call<ResponseBody> register(@Body UserDto user);

    @GET("/categories")
    Call<List<com.example.babushka.categorias.CategoryDto>> getCategories();

    @GET("/categories/{id}/image")
    Call<ResponseBody> getCategoryImage(@Path("id") int id);

}
