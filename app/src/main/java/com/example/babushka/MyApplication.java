package com.example.babushka;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    // Clase para utilizar context en toda la aplicación
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
