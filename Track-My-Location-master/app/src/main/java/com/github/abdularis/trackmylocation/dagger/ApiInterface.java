package com.github.abdularis.trackmylocation.dagger;

import com.github.abdularis.trackmylocation.network.model.LocationSymptomRequest;
import com.github.abdularis.trackmylocation.network.model.LoginRequest;
import com.github.abdularis.trackmylocation.network.model.LoginResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("users")
    Single<LoginResponse> getUser(@Header("x-auth-token") String token);

    @POST("users")
    Single<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("batch/datapoints")
    Completable locationSymptomSync(@Header("x-auth-token") String token, @Body List<LocationSymptomRequest> locationSymptomRequest);
}

