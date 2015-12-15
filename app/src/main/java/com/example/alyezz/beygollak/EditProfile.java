package com.example.alyezz.beygollak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alyezz.model.User;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {


    Button bUpdate, bLogout;
    TextView etDateOfBirth, etGender, etFirstName, etLastName, etLocation,etEmail;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etFirstName = (TextView) findViewById(R.id.etFirstName);
        etLastName = (TextView) findViewById(R.id.etLastName);
        etLocation = (TextView) findViewById(R.id.etLocation);
        etDateOfBirth = (TextView) findViewById(R.id.etDateOfBirth);
        etGender = (TextView) findViewById(R.id.etGender);
        etEmail = (TextView) findViewById(R.id.etEmail);

        currentUser = ((MyApplication) getApplicationContext()).currentUser;

        etEmail.setText(currentUser.getEmail());
        etLastName.setText(currentUser.getLast_name());
        etFirstName.setText(currentUser.getFirst_name());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        startActivity(new Intent(this, Settings.class));
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {


    }
}
