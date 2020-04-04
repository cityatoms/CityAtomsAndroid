package com.github.abdularis.trackmylocation.dagger;

import com.github.abdularis.trackmylocation.network.model.LoginRequest;
import com.github.abdularis.trackmylocation.network.model.LoginResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("users")
    Single<LoginResponse> login(@Body LoginRequest loginRequest);
}