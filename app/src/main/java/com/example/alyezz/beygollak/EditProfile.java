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

public class EditProfile extends AppCompatActivity implements View.OnClickListener {


    Button bUpdate, bLogout;
    EditText etFirstName, etLastName, etLocation;
    TextView etDateOfBirth, etGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        etFirstName = (EditText) findViewById(R.id.etFirstName);
//        etLastName = (EditText) findViewById(R.id.etLastName);
//        etLocation = (EditText) findViewById(R.id.etLocation);
//        etDateOfBirth = (TextView) findViewById(R.id.etDateOfBirth);
//        etGender = (TextView) findViewById(R.id.etGender);
        bLogout = (Button) findViewById(R.id.bLogout);
        bUpdate = (Button) findViewById(R.id.bUpdate);


        bLogout.setOnClickListener(this);
        bUpdate.setOnClickListener(this);

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

        switch(v.getId())
        {
            case R.id.bLogout:

                startActivity(new Intent(this, Login.class));

                break;

        }

    }
}
