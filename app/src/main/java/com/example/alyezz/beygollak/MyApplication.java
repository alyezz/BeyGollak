package com.example.alyezz.beygollak;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Aly Ezz on 28/11/2015.
 */
public class MyApplication extends Application {

    public User currentUser;
    @Override
    public void onCreate()
    {
        super.onCreate();
        getUsers();
    }
    protected void getUsers() {
        // startProgress();
        ApiRouter.withoutToken().getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> result, Response response) {
                if (result != null && result.size()>0) {
                    currentUser = result.get(0);
                }
            }

            @Override
            public void failure(RetrofitError e) {

            }
        });
    }
}
