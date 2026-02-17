package com.abik.babushka.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton factory for creating and configuring the Retrofit instance.
 */
public class RetrofitClient {

    /**
     * Base URL for the backend API.
     * 10.0.2.2 is used to access the host machine from the Android emulator.
     */
    private static final String BASE_URL = "http://10.0.2.2:8000/";

    private static Retrofit retrofit;

    /**
     * Returns a configured implementation of the RecipeApi interface.
     * Initializes Retrofit only once (lazy singleton).
     */
    public static RecipeApi getApi() {

        if (retrofit == null) {

            // HTTP logging interceptor for debugging network requests
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Configure OkHttp client with logging and authentication interceptor
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(new AuthInterceptor())
                    .build();

            // Build Retrofit instance with JSON converter
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(RecipeApi.class);
    }
}
