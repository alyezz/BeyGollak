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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment implements View.OnClickListener {


    LinearLayout llProfile;
    ImageView ivProfilePicture;
    TextView tvViewFriends,tvName ;
    Button bPost;
    EditText etPost;
    ArrayList<Integer> posts = new ArrayList<Integer>();
    ArrayList<String> poster = new ArrayList<String>();
    ArrayList<String> post_content = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_profile,container,false);
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        llProfile = (LinearLayout) getView().findViewById(R.id.llProfilePosts);

        ivProfilePicture = (ImageView) getView().findViewById(R.id.ivProfilePicture);
        tvViewFriends = (TextView) getView().findViewById(R.id.tvViewFriends);
        tvName = (TextView) getView().findViewById(R.id.tvName);
        etPost = (EditText) getView().findViewById(R.id.etPost);
        bPost = (Button) getView().findViewById(R.id.bPost);

        bPost.setOnClickListener(this);
        ivProfilePicture.setOnClickListener(this);
        tvViewFriends.setOnClickListener(this);
       populatePosts();


    }

    public void populatePosts()
    {
        String[] textArray = {"Sherif", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vel dolor vitae diam egestas viverra vitae id nunc. Maecenas cursus sodales Arcu at varius. Etiam varius ligula ac elit tincidunt, vel ante scelerisque eleifend.", "Aly", "cursus eget diam molestie EU. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc consectetuer male Suada lacus a hendrerit."};
        llProfile.removeAllViews();
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

                startActivity(new Intent(getActivity(), EditProfile.class));
                break;

            case R.id.tvViewFriends:
                startActivity(new Intent(getActivity(), FriendList.class));
                break;

            case R.id.bPost:
                if (etPost.length() == 0)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Cant post empty text",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Posted: " + etPost.getText(),
                            Toast.LENGTH_LONG).show();
                }
        }

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
