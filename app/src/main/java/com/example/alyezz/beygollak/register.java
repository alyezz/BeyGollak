package com.example.alyezz.beygollak;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class register extends AppCompatActivity implements View.OnClickListener {

    Button bRegister;
    EditText etEmail, etPassword, etConfirmPassword, etFirstName, etLastName, etLocation, etDateOfBirth, etGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etDateOfBirth = (EditText) findViewById(R.id.etDateOfBirth);
        etGender = (EditText) findViewById(R.id.etGender);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.bRegister:

                    startActivity(new Intent(this, login.class));

                break;

        }

    }

}
