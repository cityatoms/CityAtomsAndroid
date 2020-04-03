package com.github.abdularis.trackmylocation.dagger;

import com.github.abdularis.trackmylocation.dto.UserData;
import com.github.abdularis.trackmylocation.network.model.LoginRequest;
import com.github.abdularis.trackmylocation.network.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("users")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}