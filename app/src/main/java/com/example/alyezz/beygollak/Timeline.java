package com.example.alyezz.beygollak;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Timeline extends Fragment implements View.OnClickListener{

    LinearLayout llTimeline;
    ArrayList<Integer> posts = new ArrayList<Integer>();
    ArrayList<String> poster = new ArrayList<String>();
    ArrayList<String> post_content = new ArrayList<String>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_timeline,container,false);
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        llTimeline = (LinearLayout) getView().findViewById(R.id.llTimeline);
        populatePosts();
    }

    public void populatePosts()
    {
        String[] textArray = {"Sherif",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vel dolor vitae diam egestas viverra vitae id nunc. Maecenas cursus sodales Arcu at varius. Etiam varius ligula ac elit tincidunt, vel ante scelerisque eleifend.",
                "Aly",
                "cursus eget diam molestie EU. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc consectetuer male Suada lacus a hendrerit.",
                 "Mohamed",
                "Curabitur varius, nisi non convallis interdum, ipsum nibh tempor quam, eget fringilla tellus tortor a orci. Etiam consectetur pulvinar vehicula. Vestibulum tempor fermentum quam sit amet dapibus.",
                "Ahmed",
                 "Ut nec sollicitudin leo. Sed vitae maximus diam. Aliquam dictum quam et neque sodales bibendum. Fusce eget risus auctor, efficitur ligula in, porta ligula. Praesent id ex dapibus, bibendum lacus a, euismod nibh. Pellentesque vitae porta tellus. Morbi iaculis magna ligula, nec mollis nulla venenatis ac."};
        llTimeline.removeAllViews();
        for( int i = 0; i < textArray.length; i++ )
        {
            TextView textView = new TextView(getActivity());
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
                textView.setPadding(20,15,20,30);
            }

            llTimeline.addView(textView);
        }
    }

    public void onClick(View v) {

        for(int i = 0; i< posts.size(); i++) {
            if (v.getId() == posts.get(i))
            {
                Intent a = new Intent(getActivity().getApplicationContext(), Post.class);
                a.putExtra("name", poster.get(i));
                a.putExtra("content", post_content.get(i));
                startActivity(a);
                break;
            }
        }
    }

}
