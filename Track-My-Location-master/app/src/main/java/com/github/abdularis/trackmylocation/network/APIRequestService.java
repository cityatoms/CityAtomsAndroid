package com.github.abdularis.trackmylocation.network;

import com.github.abdularis.trackmylocation.Enitity.LoginEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.github.abdularis.trackmylocation.GlobalData.ServeiceUrls.LOGIN_URL;

public interface APIRequestService {
    @POST(LOGIN_URL)
    Call<LoginEntity> callLogin(
                                @Path("first_name") String first_name,
                                @Path("last_name") String last_name,
                                @Path("instance_id") String instance_id,
                                @Path("time_zone") String time_zone,
                                @Path("country_code") String country_code,
                                @Header("x-cityatom-auth-token") String cityatom_auth
                                );

}


