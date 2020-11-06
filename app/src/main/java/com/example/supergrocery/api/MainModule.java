package com.example.supergrocery.api;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.supergrocery.other.Links;
import com.example.supergrocery.room.ItemsDB;
import com.example.supergrocery.room.ItemsDao;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.concurrent.TimeUnit;

@Module
@InstallIn(ApplicationComponent.class)
public class MainModule {


    @Inject
    @Provides
    public API provideApi(TokenInterceptor tokenInterceptor) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(tokenInterceptor);
        builder.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(logging);
        OkHttpClient okHttpClient = builder
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Links.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(API.class);
    }

    @Provides
    static SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    ItemsDB provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context, ItemsDB.class, context.getPackageName())
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    ItemsDao provideCartItemDao(ItemsDB db) {
        return db.orderItemDao();
    }
}
