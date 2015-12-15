package com.example.alyezz.beygollak;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class other_editProfile extends AppCompatActivity implements View.OnClickListener {


    TextView etDateOfBirth, etGender, etFirstName, etLastName, etLocation,etEmail;
    User currentUser;
    ProgressDialog progress;
    Long userId;
    List<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etFirstName = (TextView) findViewById(R.id.etFirstName);
        etLastName = (TextView) findViewById(R.id.etLastName);
        etLocation = (TextView) findViewById(R.id.etLocation);
        etDateOfBirth = (TextView) findViewById(R.id.etDateOfBirth);
        etGender = (TextView) findViewById(R.id.etGender);
        etEmail = (TextView) findViewById(R.id.etEmail);

        currentUser = ((MyApplication) getApplicationContext()).currentUser;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }

        getUsers();

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

    protected int findUser(Long id)
    {
        for(int i = 0; i<users.size();i++)
        {
            if (users.get(i).getId() == id)
            {
                return i;
            }
        }
        return -1;
    }

    protected void populateInfo()
    {
        etEmail.setText(users.get(findUser(userId)).getEmail());
        etLastName.setText(users.get(findUser(userId)).getLast_name());
        etFirstName.setText(users.get(findUser(userId)).getFirst_name());
    }

    protected void getUsers() {
        // startProgress();
        users.clear();
        progress = ProgressDialog.show(this, "Fetching Data", "Please wait...", true);
        ApiRouter.withoutToken().getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> result, Response response) {
                if (result != null) {
                    users.addAll(result);
                    populateInfo();
                }
                progress.dismiss();
            }

            @Override
            public void failure(RetrofitError e) {
                progress.dismiss();
                displayError(e);
            }
        });
        progress.dismiss();
    }

    protected void displayError(Exception e) {
        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onClick(View v) {


    }
}
