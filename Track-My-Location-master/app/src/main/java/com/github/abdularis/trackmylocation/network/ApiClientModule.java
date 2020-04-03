package com.github.abdularis.trackmylocation.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiClientModule {

    public final String BASE_URL;

    public ApiClientModule(String url) {
        this.BASE_URL = url;
    }

    @Provides
    @Singleton
    Retrofit getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("x-cityatom-auth-token", "test")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        OkHttpClient client = httpClient.build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

}
