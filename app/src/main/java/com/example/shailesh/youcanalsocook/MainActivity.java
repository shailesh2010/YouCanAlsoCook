package com.example.shailesh.youcanalsocook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class MainActivity extends Activity {
    TextView t1, t2, t3;
    Button b1, b2, b3, b4;
    EditText et1;
    public static final String Recipe_Name = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            t1 = (TextView) findViewById(R.id.main_username);
            t2 = (TextView) findViewById(R.id.main_message);
            b1 = (Button) findViewById(R.id.search_btn);
            b2 = (Button) findViewById(R.id.last_viewed);
            b3 = (Button) findViewById(R.id.most_popular);
            b4 = (Button) findViewById(R.id.get_check_boxes);
            et1 = (EditText) findViewById(R.id.search_text);
            t1.setText("Welcome " + currentUser.getUsername().toString());
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*This code syntax is taken from https://parse.com/docs/android/guide#queries */
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
                    query.whereMatches("RecipeName", et1.getText().toString());
                    query.countInBackground(new CountCallback() {
                        @Override
                        public void done(int i, ParseException e) {
                            if (i > 0) {
                                Intent intent = new Intent(MainActivity.this, ViewResult.class);
                                intent.putExtra(Recipe_Name, et1.getText().toString());
                                intent.putExtra("unique_word", "search_keyword");
                                startActivity(intent);
                            } else {
                                t2.setText(R.string.no_recipe_found);
                            }
                        }
                    });
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ViewResult.class);
                    intent.putExtra("unique_word", "last_viewed");
                    startActivity(intent);
                }
            });
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ViewResult.class);
                    intent.putExtra("unique_word", "most_popular");
                    startActivity(intent);
                }
            });
            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Spice_Vegetable_CheckBoxes.class);
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout1:
                ParseUser.logOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



