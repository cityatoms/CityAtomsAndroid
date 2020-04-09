package com.foribus.cityatoms.network;

import com.foribus.cityatoms.Enitity.LoginEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIRequestService {
    @FormUrlEncoded
    @POST("me/users")
    Call<LoginEntity> callLogin(@Header("x-cityatom-auth-token") String cityatom_auth,
                                @Field("first_name") String first_name,
                                @Field("last_name") String last_name,
                                @Field("instance_id") String instance_id,
                                @Field("time_zone") String time_zone,
                                @Field("country_code") String country_code
    );

}


