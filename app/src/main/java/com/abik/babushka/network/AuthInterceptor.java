package com.abik.babushka.network;

import android.content.Context;

import com.abik.babushka.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Authentication interceptor that automatically attaches
 * the stored session token to outgoing HTTP requests.
 */
public class AuthInterceptor implements Interceptor {

    /**
     * Retrieves the session token stored in SharedPreferences.
     *
     * @return the session token, or null if not available
     */
    private String getSessionToken() {
        Context context = MyApplication.getAppContext();
        if (context == null) return null;

        return context
                .getSharedPreferences("session", Context.MODE_PRIVATE)
                .getString("sessionToken", null);
    }

    /**
     * Intercepts outgoing requests and adds the Authorization header
     * using the Bearer token if a session token exists.
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = getSessionToken();

        if (token == null) {
            return chain.proceed(originalRequest);
        }

        Request newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }
}
