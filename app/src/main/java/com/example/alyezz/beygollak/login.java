package com.example.alyezz.beygollak;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alyezz.model.Review;
import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;
import com.facebook.*;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Login extends BaseActivity implements View.OnClickListener {


    Button bLogin, bFacebook;
    LoginButton loginButton;
    EditText etEmail, etPassword;
    TextView tvRegisterLink;
    Intent intent;
    ProgressDialog progress;
    private CallbackManager mCallBackManager;
    User current = new User();
    Intent i;

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {


            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                           AccessToken currentAccessToken) {
                    if (currentAccessToken == null) {
                        startActivity(i);
                    }
                }
            };
            accessTokenTracker.startTracking();
            get_remaining_data(accessToken);

//            User x = new User();
//            x = ((MyApplication) getApplicationContext()).currentUser;
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = new Intent(this,MainActivity.class);
        i = new Intent(this,Login.class);
        mCallBackManager = CallbackManager.Factory.create();
       // etEmail = (EditText) findViewById(R.id.etEmail);
      //  etPassword = (EditText) findViewById(R.id.etPassword);
      //  bLogin = (Button) findViewById(R.id.bLogin);
       // bFacebook = (Button) findViewById(R.id.bFacebook);
       // tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(mCallBackManager, mCallback);

       // bLogin.setOnClickListener(this);
      //  tvRegisterLink.setOnClickListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }


    protected void auth()
    {
        ApiRouter.withoutToken().fb_auth(current, new Callback<User>() {
            @Override
            public void success(User result, Response response) {
                progress.dismiss();
                Log.d("el user", "" + result.getId());
                ((MyApplication) getApplicationContext()).currentUser = result;
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError e) {
                progress.dismiss();
               // Log.d("MyApp",e.toString());
                displayError(e);
            }
        });
    }


    protected  void get_remaining_data(AccessToken accessToken)
    {
        progress = ProgressDialog.show(this, "Logging in", "Please wait...", true);
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            Log.d("MyApp", object.toString());
                            current.setFacebook_id(Long.parseLong(object.get("id").toString()));
                            current.setFirst_name(object.getString("first_name"));
                            current.setLast_name(object.getString("last_name"));
                            current.setEmail(object.get("email").toString());
                            auth();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("MyApp", e.toString());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,id,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void onClick(View v) {

        switch(v.getId())
        {

        }

    }

}
