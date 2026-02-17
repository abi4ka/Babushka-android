package com.abik.babushka.network;

import android.content.Context;

import com.abik.babushka.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor de autenticación.
 * Este interceptor se encarga de añadir automáticamente el token de sesión
 * a todos los requests HTTP que se envían desde la aplicación.
 */
public class AuthInterceptor implements Interceptor {

    /**
     * Obtiene el token de sesión guardado en SharedPreferences.
     * El token se utiliza para autenticar al usuario en el backend.
     *
     * @return token de sesión o null si no existe
     */
    private String getSessionToken() {
        // Obtenemos el contexto global de la aplicación
        Context context = MyApplication.getAppContext();

        // Si el contexto no está disponible, devolvemos null
        if (context == null) return null;

        // Accedemos a SharedPreferences con el nombre "session"
        // y obtenemos el valor asociado a la clave "sessionToken"
        return context
                .getSharedPreferences("session", Context.MODE_PRIVATE)
                .getString("sessionToken", null);
    }

    /**
     * Metodo intercept que se ejecuta antes de enviar cada request HTTP.
     * Aquí se añade el token en el header Authorization si existe.
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        // Request original que se va a enviar
        Request originalRequest = chain.request();

        // Obtenemos el token de sesión
        String token = getSessionToken();

        // Si no hay token, enviamos el request sin modificar
        if (token == null) {
            return chain.proceed(originalRequest);
        }

        // Creamos un nuevo request añadiendo el header Authorization
        // con el token en formato Bearer
        Request newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        // Enviamos el request modificado
        return chain.proceed(newRequest);
    }
}
