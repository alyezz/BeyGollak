package com.example.alyezz.beygollak;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Post extends AppCompatActivity  implements View.OnClickListener  {

    TextView tvName, tvPostText;
    EditText etComment;
    Button bComment;
    LinearLayout llPost;
    ArrayList<Integer> commenters = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llPost = (LinearLayout) findViewById(R.id.llPost);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPostText = (TextView) findViewById(R.id.tvPostText);
        etComment = (EditText) findViewById(R.id.etComment);
        bComment = (Button) findViewById(R.id.bComment);

        tvName.setOnClickListener(this);
        bComment.setOnClickListener(this);

        populateComments();

    }

    public void populateComments()
    {
        String[] textArray = {"Sherif", " Duis vel dolor vitae diam egestas viverra vitae id nunc. Maecenas cursus sodales Arcu at varius. Etiam varius ligula ac elit tincidunt, vel ante scelerisque eleifend.", "Aly", "cursus eget diam molestie EU. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc consectetuer male Suada lacus a hendrerit."};
        
        for( int i = 0; i < textArray.length; i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(textArray[i]);
            textView.setOnClickListener(this);
            if(i%2 == 0)
            {
                textView.setTypeface(null, Typeface.BOLD);
                textView.setId(i);
                commenters.add(textView.getId());
                textView.setPadding(20,15,20,0);
            }
            else
            {
                textView.setPadding(20,15,20,20);
            }

            llPost.addView(textView);
        }
    }
    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.bComment:
                break;
            case R.id.tvName:
                startActivity(new Intent(this, Profile.class));
                break;
        }

        for(int i = 0; i< commenters.size();i++)
        {
            if (v.getId() == commenters.get(i))
            {
                startActivity(new Intent(this, Profile.class));
                break;
            }
        }

    }
}
