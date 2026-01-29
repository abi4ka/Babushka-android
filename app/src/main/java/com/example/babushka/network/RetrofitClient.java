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
        // Comprueba si Retrofit ya fue creado.
        // Si no fue creado, se inicializa y sino se reutiliza.
        if (retrofit == null) {
            //Debug
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHTTPClient es el objeto que configuramos para poder hacer las solicitudes.
            // Creamos un OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
                    // Debug
                    .addInterceptor(logging)

                    // Enviar token del usuario
                    .addInterceptor(new AuthInterceptor())

                    // Contruimos el cliente final que sera empleado por Retrofit
                    .build();

            //Creamos la instancia de Retrofit
            retrofit = new Retrofit.Builder()
                    // Define la URL base de la API
                    .baseUrl(BASE_URL)

                    // Asigna el cliente HTTP que definimos con logging
                    .client(client)

                    // Permite convertir automaticamente JSON a objetos
                    .addConverterFactory(GsonConverterFactory.create())

                    // Contruimos el cliente final que sera empleado por Retrofit
                    .build();
        }
        // Retrofit crea una implementación de la interfaz RecipeApi
        return retrofit.create(RecipeApi.class);
    }
}