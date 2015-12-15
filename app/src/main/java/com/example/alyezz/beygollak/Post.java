package com.example.alyezz.beygollak;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alyezz.model.Comment;
import com.example.alyezz.model.Review;
import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Post extends AppCompatActivity  implements View.OnClickListener  {

    TextView tvName, tvPostText;
    EditText etComment;
    Button bComment;
    LinearLayout llPost;
    Long postId;
    Long userId;
    Comment c;
    int commetSize;
    ProgressDialog progress;
    User currentUser;
    ArrayList<Integer> commenters = new ArrayList<Integer>();
    List<Comment> comments = new ArrayList<>();
    List<User> users = new ArrayList<User>();
    ArrayList<String> commenters_name = new ArrayList<String>();

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

        currentUser = ((MyApplication) getApplicationContext()).currentUser;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getLong("id");
            tvPostText.setText(extras.getString("content"));
            userId = extras.getLong("user");
        }

       getUsers();

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

    public void populateComments()
    {
       // String[] textArray = {"Sherif", " Duis vel dolor vitae diam egestas viverra vitae id nunc. Maecenas cursus sodales Arcu at varius. Etiam varius ligula ac elit tincidunt, vel ante scelerisque eleifend.", "Aly", "cursus eget diam molestie EU. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc consectetuer male Suada lacus a hendrerit."};
        llPost.removeAllViews();
        for( int i = 0; i < comments.size(); i++ )
        {
                TextView textView = new TextView(this);
                textView.setText(users.get(findUser(comments.get(i).getUser_id())).getEmail());
                textView.setId((int) comments.get(i).getUser_id());
                textView.setOnClickListener(this);
                textView.setTypeface(null, Typeface.BOLD);
               // textView.setId((int)comments.get(i).getId());
                textView.setPadding(20, 15, 20, 0);
                llPost.addView(textView);

                textView = new TextView(this);
                textView.setText(comments.get(i).getComment_content());
                textView.setOnClickListener(this);
                textView.setPadding(20, 15, 20, 20);
                //textView.setId((int)comments.get(i).getId());
                textView.setPadding(20,15,20,20);


            llPost.addView(textView);
        }
    }

    protected void getComments() {
        comments.clear();
        // startProgress();
        progress = ProgressDialog.show(this, "Fetching Data", "Please wait...", true);
        ApiRouter.withoutToken().getComment(postId, new Callback<List<Comment>>() {
            @Override
            public void success(List<Comment> result, Response response) {
                if (result != null) {
                    comments.addAll(result);
                    populateComments();
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

    protected void getUsers() {
        users.clear();
        // startProgress();
        ApiRouter.withoutToken().getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> result, Response response) {
                if (result != null) {
                    users.addAll(result);
                    getComments();
                    tvName.setText(users.get(findUser(userId)).getFirst_name() + " " + users.get(findUser(userId)).getLast_name());
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

    protected void postComment(String comment_text)
    {
        c = new Comment();
        c.setId(commetSize + 1);
        c.setUser_id(currentUser.getId());
        c.setPost_id(postId);
        c.setComment_content(comment_text);

        ApiRouter.withoutToken().post_comment(c, new Callback<Comment>() {
            @Override
            public void success(Comment result, Response response) {
                if (result != null) {
                    comments.add(c);
                    populateComments();
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

    protected void getCommentSize(final String comment)
    {
        ApiRouter.withoutToken().getCommentsSize(new Callback<Integer>() {
            @Override
            public void success(Integer result, Response response) {
                if (result != null) {
                    commetSize = result + 1;
                } else {
                    commetSize = 1;
                }
                postComment(comment);
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

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.bComment:
                if (etComment.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Cant post empty text",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    getCommentSize(etComment.getText().toString());
                    etComment.setText("");
                    break;
                }
            case R.id.tvName:
                Intent a = new Intent(getApplicationContext(), Other_Profile.class);
                a.putExtra("id", userId);
                startActivity(a);
                break;
        }

        for(int i = 0; i< comments.size();i++)
        {
            if (v.getId() == comments.get(i).getUser_id())
            {
                Intent a = new Intent(getApplicationContext(), Other_Profile.class);
                a.putExtra("id", comments.get(i).getUser_id());
                startActivity(a);
                break;
            }
        }

    }
}
