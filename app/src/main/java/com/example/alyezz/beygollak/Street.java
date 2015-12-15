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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alyezz.model.Review;
import com.example.alyezz.model.User;
import com.example.alyezz.util.ApiRouter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Street extends AppCompatActivity implements View.OnClickListener {

    TextView tvName;
    EditText etComment;
    Button bReview;
    String name;
    LinearLayout llStreet;
    ProgressDialog progress;
    Review r;
    int reviewSize;
    List<com.example.alyezz.model.Street> streets = new ArrayList<com.example.alyezz.model.Street>();
    com.example.alyezz.model.Street current;
    List<Review> reviews = new ArrayList<Review>();
    List<User> users = new ArrayList<User>();
    ArrayList<Integer> commenters = new ArrayList<Integer>();
    ArrayList<String> commenters_name = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llStreet = (LinearLayout) findViewById(R.id.llStreet);
        tvName = (TextView) findViewById(R.id.tvName);
        etComment = (EditText) findViewById(R.id.etComment);
        bReview = (Button) findViewById(R.id.bReview);

        tvName.setOnClickListener(this);
        bReview.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            //tvName.setText(value);
        }
        getStreets();
      //  populateComments();

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
        llStreet.removeAllViews();
       // String[] textArray = {"Sherif", " Duis vel dolor vitae diam egestas viverra vitae id nunc. Maecenas cursus sodales Arcu at varius. Etiam varius ligula ac elit tincidunt, vel ante scelerisque eleifend.", "Aly", "cursus eget diam molestie EU. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc consectetuer male Suada lacus a hendrerit."};
        for( int i = 0; i < reviews.size(); i++ )
        {
            TextView textView = new TextView(this);

            for(int j = 0; j<users.size();j++)
            {
                if (reviews.get(i).getUser_id() == users.get(j).getId())
                {
                    textView.setText(users.get(j).getEmail());
                    textView.setId((int)users.get(j).getId());
                }
            }
                textView.setOnClickListener(this);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setPadding(20,15,20,0);
                llStreet.addView(textView);
                textView = new TextView(this);
                textView.setText(reviews.get(i).getReview_content());
                textView.setOnClickListener(this);
                textView.setPadding(20, 15, 20, 20);

            llStreet.addView(textView);
        }
    }
    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.tvName:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.bReview:
                if (etComment.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Cant post empty text",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    getReviewSize("" + etComment.getText());
                    etComment.setText("");
                    break;
                }
        }

        for(int i = 0; i< users.size();i++)
        {
            if (v.getId() == users.get(i).getId())
            {
                Intent a = new Intent(getApplicationContext(), Other_Profile.class);
                a.putExtra("id", users.get(i).getId());
                startActivity(a);
                break;
            }
        }

    }

    protected void getStreets() {
        streets.clear();
        //startProgress();
        progress = ProgressDialog.show(this, "Fetching Data", "Please wait...", true);

        ApiRouter.withoutToken().getStreets(new Callback<List<com.example.alyezz.model.Street>>() {
            @Override
            public void success(List<com.example.alyezz.model.Street> result, Response response) {
                streets.addAll(result);

                for (int i = 0; i < streets.size(); i++) {
                    if (streets.get(i).getName().equals(name)) {
                        current = streets.get(i);
                        getReviews();
                        break;
                    }
                }
                tvName.setText(current.getArea() + " - " + current.getName());
                progress.dismiss();
            }

            @Override
            public void failure(RetrofitError e) {
                progress.dismiss();
                displayError(e);
            }
        });

    }

    protected void getReviews() {
        reviews.clear();
       // startProgress();
        ApiRouter.withoutToken().getReview(current.getId(), new Callback<List<Review>>() {
            @Override
            public void success(List<Review> result, Response response) {
                if (result != null) {
                    reviews.addAll(result);
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

    protected void postReview(String review)
    {
        r = new Review();
        r.setId(reviewSize);
        r.setStreet_id(current.getId());
        r.setReview_content(review);
        r.setUser_id(1);
        ApiRouter.withoutToken().post_review(r, new Callback<Review>() {
            @Override
            public void success(Review result, Response response) {
                if (result != null) {
                    reviews.add(r);
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

    protected void getReviewSize(final String review)
    {
        ApiRouter.withoutToken().getreviewsSize(new Callback<Integer>() {
            @Override
            public void success(Integer result, Response response) {
                if (result != null)
                {
                    reviewSize = result + 1;
                }
                else
                {
                    reviewSize = 1;
                }
                postReview(review);
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

}

