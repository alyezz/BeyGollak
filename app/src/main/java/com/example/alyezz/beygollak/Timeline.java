package com.example.alyezz.beygollak;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Timeline extends Fragment implements View.OnClickListener{

    LinearLayout llTimeline;
    User currentUser;
    List<com.example.alyezz.model.Post> post = new ArrayList<com.example.alyezz.model.Post>();
    List<User> friends = new ArrayList<User>();
    ProgressDialog progress;
    ArrayList<Integer> posts = new ArrayList<Integer>();
    List<User> users = new ArrayList<User>();
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
        currentUser = ((MyApplication) getActivity().getApplicationContext()).currentUser;

        llTimeline = (LinearLayout) getView().findViewById(R.id.llTimeline);
        getPosts();
    }

    public void populatePosts()
    {
        progress.dismiss();
        llTimeline.removeAllViews();
        for( int i = 0; i < post.size(); i++ )
        {
            for (int j = 0;j<friends.size();j++)
            {
                if (post.get(i).getUser_id() == friends.get(j).getId())
                {
                    TextView textView = new TextView(getActivity());
                    textView.setText(findUser(post.get(i).getUser_id()) + " → " + findUser(post.get(i).getReciever_id()));
                    textView.setOnClickListener(this);
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setPadding(20, 15, 20, 0);
                    llTimeline.addView(textView);

                    textView = new TextView(getActivity());
                    textView.setText(post.get(i).getPost_content());
                    textView.setOnClickListener(this);
                    textView.setId((int) post.get(i).getId());
                    textView.setPadding(20, 15, 20, 30);
                    llTimeline.addView(textView);
                    break;
                }
            }

        }
    }

    protected String findUser(Long id)
    {
        for(int i = 0; i<users.size();i++)
        {
            if (users.get(i).getId() == id)
            {
                return users.get(i).getEmail();
            }
        }
        return "";
    }

    protected void getFriends() {
        friends.clear();
        // startProgress();
        ApiRouter.withoutToken().getFriends(currentUser.getId(), new Callback<List<User>>() {
            @Override
            public void success(List<User> result, Response response) {
                if (result != null) {
                    friends.addAll(result);
                    getUsers();
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

    protected void getUsers() {
        users.clear();
        // startProgress();
        ApiRouter.withoutToken().getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> result, Response response) {
                if (result != null) {
                    users.addAll(result);
                    populatePosts();
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

    protected void getPosts() {
        post.clear();
        //startProgress();
        progress = ProgressDialog.show(this.getActivity(), "Fetching Data", "Please wait...", true);

        ApiRouter.withoutToken().getPosts(new Callback<List<com.example.alyezz.model.Post>>() {
            @Override
            public void success(List<com.example.alyezz.model.Post> result, Response response) {
                post.addAll(result);
                getFriends();
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
        Toast.makeText(this.getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
                .show();
    }

    public void onClick(View v) {

        for (int i = 0 ; i<post.size();i++)
        {
            if (post.get(i).getId() == v.getId())
            {
                Intent j = new Intent(getActivity().getApplicationContext(), com.example.alyezz.beygollak.Post.class);
                j.putExtra("id", post.get(i).getId());
                j.putExtra("content",post.get(i).getPost_content());
                j.putExtra("user",post.get(i).getUser_id());
                startActivity(j);
            }
        }
    }

}
