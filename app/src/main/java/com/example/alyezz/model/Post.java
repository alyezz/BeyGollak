package com.example.alyezz.model;

/**
 * Created by Aly Ezz on 12/12/2015.
 */
public class Post {
    private long id;
    private long user_id;
    private String post_content;
    private long reciever_id;

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(long reciever_id) {
        this.reciever_id = reciever_id;
    }



}
