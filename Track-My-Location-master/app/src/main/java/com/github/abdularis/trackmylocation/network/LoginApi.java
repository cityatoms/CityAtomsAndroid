package com.github.abdularis.trackmylocation.network;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LoginApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("/posts")
    Observable<List<Void>> getPosts();
}
