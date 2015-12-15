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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alyezz.model.Post;
import com.example.alyezz.model.Review;
import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Profile extends Fragment implements View.OnClickListener {


    LinearLayout llProfile;
    ImageView ivProfilePicture;
    TextView tvViewFriends,tvName ;
    Button bPost;
    Post p;
    EditText etPost;
    ProgressDialog progress;
    User currentUser;
    List<Post> post = new ArrayList<Post>();
    List<User> users = new ArrayList<User>();

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

        while (((MyApplication) getActivity().getApplicationContext()).currentUser == null);

        currentUser = ((MyApplication) getActivity().getApplicationContext()).currentUser;
        String temp = currentUser.getFirst_name();

        temp += " " + currentUser.getLast_name();

        tvName.setText(temp);

        bPost.setOnClickListener(this);
        ivProfilePicture.setOnClickListener(this);
        tvViewFriends.setOnClickListener(this);

        getPosts();

    }

    public void populatePosts()
    {
        llProfile.removeAllViews();
        // String[] textArray = {"Sherif", " Duis vel dolor vitae diam egestas viverra vitae id nunc. Maecenas cursus sodales Arcu at varius. Etiam varius ligula ac elit tincidunt, vel ante scelerisque eleifend.", "Aly", "cursus eget diam molestie EU. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc consectetuer male Suada lacus a hendrerit."};
        for( int i = 0; i < post.size(); i++ ) {

            if (post.get(i).getReciever_id() == currentUser.getId()) {

                TextView textView = new TextView(this.getActivity());
                for (int j = 0; j < users.size(); j++) {
                    if (post.get(i).getReciever_id() == users.get(j).getId()) {
                        textView.setText(users.get(j).getEmail());
                    }
                }

                // textView.setText(""+ reviews.get(i).getUser_id());//get actual user name
                textView.setOnClickListener(this);
                textView.setTypeface(null, Typeface.BOLD);
//                commenters.add(textView.getId());
//                commenters_name.add(""+ reviews.get(i).getUser_id());
                textView.setPadding(20, 15, 20, 0);
                llProfile.addView(textView);
                textView = new TextView(this.getActivity());
                textView.setText(post.get(i).getPost_content());
                textView.setOnClickListener(this);
                textView.setPadding(20, 15, 20, 20);
                textView.setId((int)post.get(i).getId());
                llProfile.addView(textView);
            }
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
                Intent j = new Intent(getActivity().getApplicationContext(), FriendList.class);
                j.putExtra("id", currentUser.getId());
                startActivity(j);
                break;

            case R.id.bPost:
                if (etPost.length() == 0)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Cant post empty text",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    postPost("" + etPost.getText());
                    etPost.setText("");
                    break;
                }
        }

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

    protected void getPosts() {
        post.clear();
        //startProgress();
        progress = ProgressDialog.show(this.getActivity(), "Fetching Data", "Please wait...", true);

        ApiRouter.withoutToken().getPosts(new Callback<List<Post>>() {
            @Override
            public void success(List<Post> result, Response response) {
                post.addAll(result);
                getUsers();
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

    protected void postPost(String post_text)
    {
        p = new Post();
        p.setId(post.size()+1);
        p.setUser_id(currentUser.getId());
        p.setReciever_id(currentUser.getId());
        p.setPost_content(post_text);
        ApiRouter.withoutToken().post_post(p, new Callback<Post>() {
            @Override
            public void success(Post result, Response response) {
                if (result != null) {
                    post.add(p);
                    populatePosts();
                }
                progress.dismiss();
            }

            @Override
            public void failure(RetrofitError e) {
                //  progress.dismiss();
                displayError(e);
            }
        });
    }

    protected void displayError(Exception e) {
        Toast.makeText(this.getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
                .show();
    }
}
