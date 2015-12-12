package com.example.alyezz.util;


import com.example.alyezz.model.Review;
import com.example.alyezz.model.Street;
import com.example.alyezz.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface PublicApiRoutes {
    @POST("/api/sessions")
    @FormUrlEncoded
    void login(@Field("session[email]") String email, @Field("session[password]") String password,
               Callback<User> callback);
    @GET("/api/street")
    void getStreets(Callback<List<Street>> callback);

    @GET("/api/street/{street_id}")
    void getReview(@Path("street_id") long streetId, Callback<List<Review>> callback);

}