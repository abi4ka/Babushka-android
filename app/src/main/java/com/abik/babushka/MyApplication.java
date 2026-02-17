package com.abik.babushka;

import android.app.Application;
import android.content.Context;

/**
 * Custom Application class.
 * Stores and provides a global application context accessible from any class.
 */
public class MyApplication extends Application {

    // Static reference to the application context
    private static Context appContext;

    /**
     * Called when the application is created.
     * Saves the application context for global access.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    /**
     * Returns the global application context.
     *
     * @return Application context
     */
    public static Context getAppContext() {
        return appContext;
    }
}
