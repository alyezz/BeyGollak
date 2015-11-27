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

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button bLogin, bFacebook;
    EditText etEmail, etPassword;
    TextView tvRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bFacebook = (Button) findViewById(R.id.bFacebook);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        bFacebook.setOnClickListener(this);
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
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;

            case R.id.tvRegisterLink:

            startActivity(new Intent(this, Register.class));
            break;

            case R.id.bFacebook:

                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }

}
