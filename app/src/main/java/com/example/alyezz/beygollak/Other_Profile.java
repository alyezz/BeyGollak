package com.example.alyezz.beygollak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.example.alyezz.model.Friendship;
import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Other_Profile extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llProfile;
    ImageView ivProfilePicture;
    TextView tvViewFriends,tvName,tvFriend ;
    Button bPost;
    Friendship f;
    Long userId;
    int sizeOfFriends;
    User currentUser;
    EditText etPost;
    com.example.alyezz.model.Post p;
    ProgressDialog progress;
    List<com.example.alyezz.model.Post> post = new ArrayList<com.example.alyezz.model.Post>();
    List<User> users = new ArrayList<User>();
    List<User> friends = new ArrayList<User>();

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

        currentUser = ((MyApplication) getApplicationContext()).currentUser;

        ivProfilePicture.setOnClickListener(this);
        bPost.setOnClickListener(this);
        tvViewFriends.setOnClickListener(this);
        tvFriend.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        getFriendship();
        getPosts();
       // populatePosts();

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
        progress.dismiss();
        llProfile.removeAllViews();
        // String[] textArray = {"Sherif", " Duis vel dolor vitae diam egestas viverra vitae id nunc. Maecenas cursus sodales Arcu at varius. Etiam varius ligula ac elit tincidunt, vel ante scelerisque eleifend.", "Aly", "cursus eget diam molestie EU. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc consectetuer male Suada lacus a hendrerit."};
        for( int i = 0; i < post.size(); i++ ) {

            if ( post.get(i).getReciever_id() == userId) {

                TextView textView = new TextView(this);
                textView.setText(users.get(findUser(post.get(i).getUser_id())).getEmail());

                // textView.setText(""+ reviews.get(i).getUser_id());//get actual user name
                textView.setOnClickListener(this);
                textView.setTypeface(null, Typeface.BOLD);
//                commenters.add(textView.getId());
//                commenters_name.add(""+ reviews.get(i).getUser_id());
                textView.setPadding(20, 15, 20, 0);
                llProfile.addView(textView);
                textView = new TextView(this);
                textView.setText(post.get(i).getPost_content());
                textView.setOnClickListener(this);
                textView.setPadding(20, 15, 20, 20);
                textView.setId((int)post.get(i).getId());
                llProfile.addView(textView);
            }
        }
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

    protected void getPosts() {
        post.clear();
        //startProgress();
        progress = ProgressDialog.show(this, "Fetching Data", "Please wait...", true);

        ApiRouter.withoutToken().getPosts(new Callback<List<com.example.alyezz.model.Post>>() {
            @Override
            public void success(List<com.example.alyezz.model.Post> result, Response response) {
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
        progress.dismiss();
        users.clear();
        // startProgress();
        progress = ProgressDialog.show(this, "Fetching Data", "Please wait...", true);
        ApiRouter.withoutToken().getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> result, Response response) {
                if (result != null) {
                    users.addAll(result);
                    tvName.setText(users.get(findUser(userId)).getFirst_name() + " " + users.get(findUser(userId)).getLast_name());
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
        progress.dismiss();
    }


    protected void getFriendship() {
        post.clear();

        ApiRouter.withoutToken().getFriendship(new Callback<List<Friendship>>() {
            @Override
            public void success(List<Friendship> result, Response response) {

                sizeOfFriends = result.size();
                for (int i = 0; i<result.size();i++)
                {
                    if (result.get(i).getUser_id() == currentUser.getId() && result.get(i).getFriend_id() == userId)
                    {
                        f = new Friendship();
                        f = result.get(i);
                        tvFriend.setText("unFollow");
                        break;
                    }
                    if (result.get(i).getUser_id() == userId && result.get(i).getFriend_id() == currentUser.getId())
                    {
                        f = new Friendship();
                        f = result.get(i);
                        tvFriend.setText("unFollow");
                        break;
                    }
                    tvFriend.setText("Follow");
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


    protected void postFriendship()
    {
        f = new Friendship();
        f.setId(sizeOfFriends + 1);
        f.setUser_id(currentUser.getId());
        f.setFriend_id(userId);
        ApiRouter.withoutToken().post_friend(f, new Callback<Friendship>() {
            @Override
            public void success(Friendship result, Response response) {
                if (result != null) {
                    f = result;
                    tvFriend.setText("unFollow");
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

    protected void removeFriendship()
    {
        tvFriend.setText("Follow");
        ApiRouter.withoutToken().deleteFriendship(f.getId(), new Callback<Friendship>() {
            @Override
            public void success(Friendship result, Response response) {
                if (result != null) {
                   // tvFriend.setText("Follow");
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

    protected void postPost(String post_text)
    {
        p = new com.example.alyezz.model.Post();
        p.setId(post.size() + 1);
        p.setUser_id(currentUser.getId());
        p.setReciever_id(userId);
        p.setPost_content(post_text);
        ApiRouter.withoutToken().post_post(p, new Callback<com.example.alyezz.model.Post>() {
            @Override
            public void success(com.example.alyezz.model.Post result, Response response) {
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
    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.ivProfilePicture:
                Intent k = new Intent(getApplicationContext(), other_editProfile.class);
                k.putExtra("id", userId);
                startActivity(k);
                break;
            case R.id.tvFriend:
                if (tvFriend.getText().toString().equals("unFollow"))
                {
                    removeFriendship();
                }
                else
                {
                    postFriendship();
                }
                break;
            case R.id.tvViewFriends:
                Intent j = new Intent(getApplicationContext(), FriendList.class);
                j.putExtra("id", userId);
                startActivity(j);
                break;

            case R.id.bPost:
                if (etPost.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Cant post empty text",
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
                Intent j = new Intent(getApplicationContext(), com.example.alyezz.beygollak.Post.class);
                j.putExtra("id", post.get(i).getId());
                j.putExtra("content",post.get(i).getPost_content());
                j.putExtra("user",post.get(i).getUser_id());
                startActivity(j);
            }
        }
    }

    protected void displayError(Exception e) {
        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
                .show();
    }
}
