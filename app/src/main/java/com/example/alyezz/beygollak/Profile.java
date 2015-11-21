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
import android.widget.TextView;

public class Profile extends AppCompatActivity implements View.OnClickListener {


    Button bUpdate, bFriendships, bLogout;
    EditText etFirstName, etLastName, etLocation;
    TextView etDateOfBirth, etGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        etFirstName = (EditText) findViewById(R.id.etFirstName);
//        etLastName = (EditText) findViewById(R.id.etLastName);
//        etLocation = (EditText) findViewById(R.id.etLocation);
//        etDateOfBirth = (TextView) findViewById(R.id.etDateOfBirth);
//        etGender = (TextView) findViewById(R.id.etGender);
        bLogout = (Button) findViewById(R.id.bLogout);
        bUpdate = (Button) findViewById(R.id.bUpdate);
        bFriendships = (Button) findViewById(R.id.bFriendships);

        bLogout.setOnClickListener(this);
        bUpdate.setOnClickListener(this);
        bFriendships.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.bLogout:

                startActivity(new Intent(this, login.class));

                break;

        }

    }
}
