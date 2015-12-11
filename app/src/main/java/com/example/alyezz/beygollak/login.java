package com.example.alyezz.beygollak;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;
import com.facebook.*;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Login extends BaseActivity implements View.OnClickListener {


    Button bLogin, bFacebook;
    LoginButton loginButton;
    EditText etEmail, etPassword;
    TextView tvRegisterLink;
    Intent intent;
    private CallbackManager mCallBackManager;

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            if (profile != null)
            {

            }
            startActivity(intent);

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
        mCallBackManager = CallbackManager.Factory.create();
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
       // bFacebook = (Button) findViewById(R.id.bFacebook);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(mCallBackManager,mCallback);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }

    public void showAlert(String title, String  message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.bLogin:

                if (etEmail.length() == 0 || etPassword.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Username Or Password Missing!",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
//                    String email = etEmail.getText().toString();
//                    String password = etPassword.getText().toString();
//                    final Intent intent = new Intent(this, MainActivity.class);
//                    ApiRouter.withoutToken().login(email, password, new Callback<User>() {
//                        @Override
//                        public void success(User user, Response response) {
//                            setCurrentUser(user);
//                            stopProgress();
//                            startActivity(intent);
//                        }
//                        @Override
//                        public void failure(RetrofitError e) {
//                            displayError(e);
//                            bLogin.setEnabled(true);
//                        }
//                    });
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;

            case R.id.tvRegisterLink:

                startActivity(new Intent(this, Register.class));
                break;

//            case R.id.bFacebook:
//
//                startActivity(new Intent(this, MainActivity.class));
//                break;
        }

    }

}
