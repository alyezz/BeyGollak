package com.example.alyezz.model;

/**
 * Created by Aly Ezz on 12/12/2015.
 */
public class Review {

    private long id;
    private long user_id;
    private String review_content;
    private long street_id;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    public long getStreet_id() {
        return street_id;
    }

    public void setStreet_id(long street_id) {
        this.street_id = street_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
