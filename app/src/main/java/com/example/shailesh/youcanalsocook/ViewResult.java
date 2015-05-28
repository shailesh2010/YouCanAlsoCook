package com.example.shailesh.youcanalsocook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ViewResult extends Activity {
    List<ParseObject> list = new ArrayList<>();
    ListView lv;
    TextView t1;
    Context context;
    ArrayList<String> arr = new ArrayList<>();
    ArrayList<String> arr1 = new ArrayList<>();
    public static final String selected = "";
    public static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        context = this;
        lv = (ListView) findViewById(R.id.list_view_1);
        t1 = (TextView) findViewById(R.id.t1);
        Intent intent = getIntent();
        String btn_clicked = intent.getStringExtra("unique_word");
        if (btn_clicked.equals("search_keyword")) {
            String name = intent.getStringExtra(MainActivity.Recipe_Name);
            /*I took the parse syntax from https://parse.com/docs/android/guide#queries*/
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
            query.whereMatches("RecipeName", name);
            query.setLimit(100);

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {

                    try {
                        CustomAdapter adapter = new CustomAdapter(context, parseObjects);
                        lv.setAdapter(adapter);
                    } catch (Exception ex) {
                        Log.v(TAG, ex.toString());
                    }
                }
            });
        } else if (btn_clicked.equals("last_viewed")) {
            try {
                /*I took the parse syntax from https://parse.com/docs/android/guide#queries*/
                ParseQuery<ParseObject> find_last_viewed = ParseQuery.getQuery("User_Recipe");
                find_last_viewed.whereMatches("UserName", ParseUser.getCurrentUser().getObjectId().toString());
                find_last_viewed.selectKeys(Arrays.asList("Recipe"));
                find_last_viewed.orderByDescending("updatedAt");
                find_last_viewed.include("Recipe");
                find_last_viewed.setLimit(100);
                find_last_viewed.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        if (parseObjects.size() > 0) {
                            for (ParseObject obj : parseObjects) {
                                ParseObject obj1 = obj.getParseObject("Recipe");
                                list.add(obj1);
                            }
                            CustomAdapter adapter = new CustomAdapter(context, list);
                            lv.setAdapter(adapter);

                        } else {
                            t1.setText("No Recipes Found");
                        }
                    }
                });
            } catch (Exception ex3) {
                Log.v(TAG, ex3.toString());
            }
        } else if (btn_clicked.equals("most_popular")) {
            /*I took the parse syntax from https://parse.com/docs/android/guide#queries*/
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
            query.orderByDescending("SearchedTime");
            query.setLimit(5);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    CustomAdapter adapter = new CustomAdapter(context, parseObjects);
                    lv.setAdapter(adapter);
                }
            });
        } else if (btn_clicked.equals("spices_list")) {
            arr = intent.getStringArrayListExtra(Spice_Vegetable_CheckBoxes.other_intent);
            arr1 = intent.getStringArrayListExtra(Spice_Vegetable_CheckBoxes.other_intent1);
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Recipe");
            /*I took the parse syntax from https://parse.com/docs/android/guide#queries*/
            if (arr.size() > 0) {
                query1.whereContainedIn("Spices", arr);
            }
            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Recipe");
            if (arr1.size() > 0) {
                query2.whereContainedIn("Vegetables", arr1);
            }
            List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
            queries.add(query1);
            queries.add(query2);
            ParseQuery<ParseObject> main_query = ParseQuery.or(queries);
            main_query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    if (parseObjects.size() > 0) {
                        CustomAdapter adapter = new CustomAdapter(context, parseObjects);
                        lv.setAdapter(adapter);
                    }
                }
            });
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*I took the parse syntax from https://parse.com/docs/android/guide#queries*/
                ParseObject obj = (ParseObject) adapterView.getItemAtPosition(i);
                String rname = obj.getString("RecipeName");
                Intent intent = new Intent(ViewResult.this, SearchResult.class);
                intent.putExtra(selected, rname);
                startActivity(intent);
            }
        });
    }


}
