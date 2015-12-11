package com.example.alyezz.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.client.Response;

public class Street {

    private String id;
    private String name;
    private String area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }



//    public static Street parseJSON(String response) {
//        Gson gson = new GsonBuilder().create();
//        Street street = gson.fromJson(response, Response.class);
//        return street;
//    }
}
