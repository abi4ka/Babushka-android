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
    //private static final String BASE_URL = "http://82.26.150.189:25569/";

    private static Retrofit retrofit;

    //RecipeApi es la interfaz donde se definen los endpoints de la API
    public static RecipeApi getApi() {
        //Comprueba si Retrofit ya fue creado. Si no fue creado, se inicializa y sino se reutiliza.
        if (retrofit == null) {
            //Se crea el interceptor de logging, que registra todas las solicitudes y respuestas HTTP
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            //Indicamos que se va a mostrar el cuerpo completo de la petición y respuesta.
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            //OkHTTPClient es el objeto que configuramos para poder hacer las solicitudes.
            //Creamos un OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
                    //Le añadimos el interceptor de logging que creamos arriba
                    .addInterceptor(logging)
                    //Modifica
                    .addInterceptor(new AuthInterceptor())
                    //contruimos el cliente final que sera empleado por Retrofit
                    .build();

            //Creamos la instancia de Retrofit
            retrofit = new Retrofit.Builder() //inicia la construcción del cliente de Retrofit
                    .baseUrl(BASE_URL) //define la URL base de la API
                    .client(client) //asigna el cliente HTTP que definimos con logging
                    .addConverterFactory(GsonConverterFactory.create()) //permite convertir automaticamente JSON a objetos Java y viceversa
                    .build(); //finaliza y crea la instancia de Retrofit
        }
        //Retrofit crea una implementación de la interfaz RecipeApi
        return retrofit.create(RecipeApi.class);
    }
}