package com.example.babushka.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    /* Cambiamos BASE_URL de 127.0.0.1 a 10.0.2.2 porque en este caso, 127.0.0.1 NO representa nuestro ordenador
    donde está alojado y corriendo el server, sino que representa el propio emulador de Android Studio, lo que provoca
    que no se pueda conectar correctamente al back y salga error de conexión.
    En Android Studio, la manera de representar la máquina anfitriona es usando la dirección 10.0.2.2.
    */
    private static final String BASE_URL = "http://10.0.2.2:25565/";
    //private static final String BASE_URL = "http://82.26.150.189:25569/"; //Spring

    private static Retrofit retrofit;
    public static RecipeApi getApi() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(); //Debug
            logging.setLevel(HttpLoggingInterceptor.Level.BODY); //Debug

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging) //Debug
                    .addInterceptor(new AuthInterceptor()) //Enviar token del usuario
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create()) //permite convertir automaticamente JSON a objetos
                    .build();
        }
        //Retrofit crea una implementación de la interfaz RecipeApi
        return retrofit.create(RecipeApi.class);
    }
}