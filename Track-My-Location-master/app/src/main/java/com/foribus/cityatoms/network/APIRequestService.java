package com.foribus.cityatoms.network;

import com.foribus.cityatoms.Enitity.LoginEntity;
import com.foribus.cityatoms.network.model.LocationSymptomRequest;
import com.foribus.cityatoms.network.model.LoginRequest;
import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIRequestService {

    @GET("me/users")
    Call<LoginEntity> getUser(@Header("x-cityatom-auth-token") String cityatom_auth,
                              @Header("x-auth-token") String auth_token);

    @POST("me/users")
    Call<LoginEntity> callLogin(@Header("x-cityatom-auth-token") String cityatom_auth,
                                @Body LoginRequest request);

    @POST("me/batch/datapoints")
    Call<JsonArray> postBatchDataPoints(@Header("x-cityatom-auth-token") String cityatom_auth,
                                        @Header("x-auth-token") String instance_id,
                                        @Body List<LocationSymptomRequest> list);
}


