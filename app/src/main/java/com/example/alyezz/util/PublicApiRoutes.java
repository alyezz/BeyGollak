package com.example.alyezz.util;


import com.example.alyezz.beygollak.FriendList;
import com.example.alyezz.model.Comment;
import com.example.alyezz.model.Friendship;
import com.example.alyezz.model.Post;
import com.example.alyezz.model.Review;
import com.example.alyezz.model.Street;
import com.example.alyezz.model.User;
import com.facebook.AccessToken;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
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

    @GET("/api/user")
    void getUsers(Callback<List<User>> callback);

    @GET("/api/review")
    void getreviewsSize(Callback<Integer> callback);

    @POST("/api/review")
    void post_review(@Body Review review, Callback<Review> callback);

    @POST("/api/user")
    void fb_auth(@Body User user, Callback<User> callback);

    @GET("/api/post")
    void getPosts(Callback<List<Post>> callback);

    @POST("/api/post")
    void post_post(@Body Post post, Callback<Post> callback);

    @GET("/api/comment/{post_id}")
    void getComment(@Path("post_id") long postId, Callback<List<Comment>> callback);

    @POST("/api/comment")
    void post_comment(@Body Comment comment, Callback<Comment> callback);

    @GET("/api/comment")
    void getCommentsSize(Callback<Integer> callback);

    @GET("/api/friendship/{user_id}")
    void getFriends(@Path("user_id") long userId, Callback<List<User>> callback);

    @GET("/api/friendship")
    void getFriendship(Callback<List<Friendship>> callback);

    @POST("/api/friendship")
    void post_friend(@Body Friendship friendship, Callback<Friendship> callback);

    @DELETE("/api/friendship/{id}/delete")
    void deleteFriendship(@Path("id") long id, Callback<Friendship> callback);
}