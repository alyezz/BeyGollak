package com.example.alyezz.util;


import com.example.alyezz.model.Street;
import com.example.alyezz.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface PublicApiRoutes {
    @POST("/sessions")
    @FormUrlEncoded
    void login(@Field("session[email]") String email, @Field("session[password]") String password,
               Callback<User> callback);
    @GET("/api/street")
    void getStreets(Callback<List<Street>> callback);
}