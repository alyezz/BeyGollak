package com.example.alyezz.model;

/**
 * Created by Aly Ezz on 15/12/2015.
 */
public class Friendship {

    private long user_id;
    private long friend_id;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(long friend_id) {
        this.friend_id = friend_id;
    }
}
