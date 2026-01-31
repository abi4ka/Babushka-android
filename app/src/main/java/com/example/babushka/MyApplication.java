package com.example.babushka;

import android.app.Application;
import android.content.Context;

/**
 * Clase Application personalizada.
 * Se utiliza para almacenar y proporcionar el contexto global
 * de la aplicación, accesible desde cualquier clase.
 */
public class MyApplication extends Application {

    // Contexto de la aplicación guardado de forma estática
    private static Context appContext;

    /**
     * Metodo que se ejecuta cuando la aplicación se inicia.
     * Aquí se obtiene y se guarda el contexto de la aplicación.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Obtenemos el contexto de la aplicación
        appContext = getApplicationContext();
    }

    /**
     * Devuelve el contexto global de la aplicación.
     * Permite acceder al context desde cualquier clase.
     *
     * @return Context de la aplicación
     */
    public static Context getAppContext() {
        return appContext;
    }
}
