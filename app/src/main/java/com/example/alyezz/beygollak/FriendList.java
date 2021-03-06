package com.example.alyezz.beygollak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alyezz.model.Review;
import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FriendList extends AppCompatActivity {


    User currentUser;
    Long userId;
    ProgressDialog progress;
    ListView lvFriends;
    List<User> friends = new ArrayList<>();
    List<String> names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentUser = ((MyApplication) getApplicationContext()).currentUser;

        lvFriends = (ListView) findViewById(R.id.lvFriends);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }

        getFriends();
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

        public void populateFriends()
    {
        names.clear();
        for (int i = 0; i<friends.size();i++)
        {
            String s = "";
            s+= friends.get(i).getEmail();
            names.add(s);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                names );

        lvFriends.setAdapter(arrayAdapter);

        lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Long user = findFriend(names.get(position));
                Intent i = new Intent(getApplicationContext(), Other_Profile.class);
                i.putExtra("id", user);
                startActivity(i);
            }
        });
    }

    protected Long findFriend(String email)
    {
        for (int i = 0; i<friends.size();i++)
        {
            if(friends.get(i).getEmail().equals(email))
                return friends.get(i).getId();
        }
        return new Long(-1);
    }
    protected void getFriends() {
        friends.clear();
        // startProgress();
        progress = ProgressDialog.show(this, "Fetching Data", "Please wait...", true);
        ApiRouter.withoutToken().getFriends(userId, new Callback<List<User>>() {
            @Override
            public void success(List<User> result, Response response) {
                if (result != null) {
                    friends.addAll(result);
                    populateFriends();
                }
                progress.dismiss();
            }

            @Override
            public void failure(RetrofitError e) {
                progress.dismiss();
                displayError(e);
            }
        });
    }

    protected void displayError(Exception e) {
        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
                .show();
    }
}
