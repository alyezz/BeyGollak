package com.example.alyezz.beygollak;

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

import java.util.ArrayList;
import java.util.List;

public class FriendList extends AppCompatActivity {


    ListView lvFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvFriends = (ListView) findViewById(R.id.lvFriends);

        populateFriends();

        lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                startActivity(new Intent(FriendList.this, MainActivity.class));
            }
        });

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

        List<String> your_array_list = new ArrayList<String>();

        your_array_list.add("Abdallah");
        your_array_list.add("Ahmed");
        your_array_list.add("Aly");
        your_array_list.add("Anas");
        your_array_list.add("Aziz");
        your_array_list.add("Mohamed");
        your_array_list.add("Mostafa");
        your_array_list.add("Ramy");
        your_array_list.add("Sherif");
        your_array_list.add("Yakan");
        your_array_list.add("Youssef");
        your_array_list.add("wael");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        lvFriends.setAdapter(arrayAdapter);
    }


}
