package com.example.shailesh.youcanalsocook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class SearchResult extends Activity {

    TextView t1, t2, t3, t4;
    ImageView i1;
    static final String TAG = "";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        t1 = (TextView) findViewById(R.id.search_result_recipe_name);
        t2 = (TextView) findViewById(R.id.search_result_recipe_ingredients_spices);
        t3 = (TextView) findViewById(R.id.search_result_recipe_ingredients_vegetables);
        t4 = (TextView) findViewById(R.id.search_result_recipe_direction);
        i1=(ImageView)findViewById(R.id.image);
        Intent intent = getIntent();
        String name = intent.getStringExtra(ViewResult.selected);
        /*I took the parse syntax from https://parse.com/docs/android/guide#queries*/
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
        query.whereEqualTo("RecipeName", name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject != null) {
                    t1.setText(parseObject.getString("RecipeName").toString());
                    ParseFile img = (ParseFile)parseObject.get("Image");
                    img.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, ParseException e) {
                            if(bytes!=null){
                                /*I took reference for this line of code from stackoverflow.com*/
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                i1.setImageBitmap(bitmap);
                        }
                        }
                    });

                    String s = parseObject.getJSONArray("Spices").toString().replaceAll("\\[|\\]|\"", "");
                    t2.setText(s);
                    s = parseObject.getJSONArray("Vegetables").toString().replaceAll("\\[|\\]|\"", "");
                    t3.setText(s);
                    t4.setText(parseObject.getString("Direction").toString());
                    if (savedInstanceState == null) {
                        parseObject.increment("SearchedTime");
                        parseObject.saveInBackground();
                        try {
                            ParseObject obj = new ParseObject("User_Recipe");
                            obj.put("UserName", ParseUser.getCurrentUser().getObjectId().toString());
                            obj.put("Recipe", parseObject);
                            obj.saveInBackground();
                        } catch (Exception e2) {
                            Log.e(TAG, e2.toString());
                        }
                    }
                } else {
                    t1.setText(R.string.no_recipe_found);
                }
            }
        });

    }

}
