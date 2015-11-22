package com.example.alyezz.beygollak;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity implements View.OnClickListener {


    LinearLayout llProfile;
    ImageView ivProfilePicture;
    TextView tvViewFriends,tvName ;
    Button bPost;
    EditText etPost;
    ArrayList<Integer> posts = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llProfile = (LinearLayout) findViewById(R.id.llProfile);

        ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);
        tvViewFriends = (TextView) findViewById(R.id.tvViewFriends);
        tvName = (TextView) findViewById(R.id.tvName);
        etPost = (EditText) findViewById(R.id.etPost);
        bPost = (Button) findViewById(R.id.bPost);

        ivProfilePicture.setOnClickListener(this);
        tvViewFriends.setOnClickListener(this);
        bPost.setOnClickListener(this);
        populatePosts();

    }

    public void populatePosts()
    {
        String[] textArray = {"Sherif", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vel dolor vitae diam egestas viverra vitae id nunc. Maecenas cursus sodales Arcu at varius. Etiam varius ligula ac elit tincidunt, vel ante scelerisque eleifend.", "Aly", "cursus eget diam molestie EU. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc consectetuer male Suada lacus a hendrerit."};


        for( int i = 0; i < textArray.length; i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(textArray[i]);
            textView.setOnClickListener(this);
            if(i%2 == 0)
            {
                textView.setTypeface(null, Typeface.BOLD);
                textView.setPadding(20,15,20,0);
            }
            else
            {
                textView.setId(i);
                posts.add(textView.getId());
                textView.setPadding(20,15,20,20);
            }

            llProfile.addView(textView);
        }
    }
    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.ivProfilePicture:

                startActivity(new Intent(this, EditProfile.class));
                break;

            case R.id.tvViewFriends:
                startActivity(new Intent(this, FriendList.class));
                break;

        }

        for(int i = 0; i< posts.size();i++)
        {
            if (v.getId() == posts.get(i))
            {
                startActivity(new Intent(this, Post.class));
                break;
            }
        }
    }

}
