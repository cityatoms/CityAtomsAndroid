package com.github.abdularis.trackmylocation.network;

import com.github.abdularis.trackmylocation.dagger.ApiInterface;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
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
    ApiInterface getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("x-cityatom-auth-token", "test")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        OkHttpClient client = httpClient.build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(ApiInterface.class);

    }

}
