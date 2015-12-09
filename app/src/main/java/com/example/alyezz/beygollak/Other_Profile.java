package com.example.alyezz.beygollak;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Other_Profile extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llProfile;
    ImageView ivProfilePicture;
    TextView tvViewFriends,tvName,tvFriend ;
    Button bPost;
    EditText etPost;
    ArrayList<Integer> posts = new ArrayList<Integer>();
    ArrayList<String> poster = new ArrayList<String>();
    ArrayList<String> post_content = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other__profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llProfile = (LinearLayout) findViewById(R.id.llProfilePosts);

        ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);
        tvViewFriends = (TextView) findViewById(R.id.tvViewFriends);
        tvFriend = (TextView) findViewById(R.id.tvFriend);
        tvName = (TextView) findViewById(R.id.tvName);
        etPost = (EditText) findViewById(R.id.etPost);
        bPost = (Button) findViewById(R.id.bPost);

        bPost.setOnClickListener(this);
        tvViewFriends.setOnClickListener(this);
        tvFriend.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("name");
            tvName.setText(value);
        }

        populatePosts();

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

    public void populatePosts()
    {
        String[] textArray = {"Sherif", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vel dolor vitae diam egestas viverra vitae id nunc. Maecenas cursus sodales Arcu at varius. Etiam varius ligula ac elit tincidunt, vel ante scelerisque eleifend.", "Aly", "cursus eget diam molestie EU. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc consectetuer male Suada lacus a hendrerit."};
        llProfile.removeAllViews();
        for( int i = 0; i < textArray.length; i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(textArray[i]);
            textView.setOnClickListener(this);
            if(i%2 == 0)
            {
                textView.setTypeface(null, Typeface.BOLD);
                textView.setPadding(20, 15, 20, 0);
                poster.add(textArray[i]);
            }
            else
            {
                textView.setId(i);
                posts.add(textView.getId());
                post_content.add(textArray[i]);
                textView.setPadding(20,15,20,20);
            }

            llProfile.addView(textView);
        }
    }
    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.tvViewFriends:
                startActivity(new Intent(this, FriendList.class));
                break;

            case R.id.bPost:
                if (etPost.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Cant post empty text",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Posted: " + etPost.getText(),
                            Toast.LENGTH_LONG).show();
                }

        }

        for(int i = 0; i< posts.size(); i++) {
            if (v.getId() == posts.get(i))
            {
                Intent a = new Intent(getApplicationContext(), Post.class);
                a.putExtra("name", poster.get(i));
                a.putExtra("content", post_content.get(i));
                startActivity(a);
                break;
            }
        }
    }

}
