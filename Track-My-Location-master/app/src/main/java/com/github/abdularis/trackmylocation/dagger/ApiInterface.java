package com.github.abdularis.trackmylocation.dagger;

import com.github.abdularis.trackmylocation.network.model.LocationSymptomRequest;
import com.github.abdularis.trackmylocation.network.model.LoginRequest;
import com.github.abdularis.trackmylocation.network.model.LoginResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("users")
    Single<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("batch/datapoints")
    Single<Void> locationSymptomSync(@Header("x-auth-token") String token, @Body LocationSymptomRequest locationSymptomRequest);
}