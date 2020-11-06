package com.example.supergrocery.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class TokenInterceptor implements Interceptor {

    private SharedPreferences preferences;
    private Context context;

    @Inject
    public TokenInterceptor(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        if (request.header("No-Authentication") == null) {
            String currentToken = preferences.getString("Token", null);
            if (currentToken == null) {
                Log.e("error", "Authentication token must be defined!");
            }
            requestBuilder.addHeader("Authorization", String.format("Bearer %s", currentToken));
        } else {
            requestBuilder.removeHeader("No-Authentication");
        }

        return chain.proceed(requestBuilder.build());
    }
}
