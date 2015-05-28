package com.example.shailesh.youcanalsocook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class Spice_Vegetable_CheckBoxes extends Activity {
    LinearLayout l1;
    LinearLayout l2;
    Button b1;
    String s2 = "";
    CheckBox c1;
    CheckBox c2;
    ArrayList<String> arr = new ArrayList<>();
    ArrayList<String> arr1 = new ArrayList<>();
    static final String restore_check_box = "ABC";
    static final String restore_check_box1 = "ABCD";
    static final String s2_value = "xyz";
    static final String other_intent = "mno";
    static final String other_intent1 = "mnop";
    static final String TAG = "lajk";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_spice__vegetable__check_boxes);
            l1 = (LinearLayout) findViewById(R.id.linear_layout1);
            l2 = (LinearLayout) findViewById(R.id.linear_layout2);
            b1 = (Button) findViewById(R.id.find_recipe);
            if (savedInstanceState != null) {
                arr = savedInstanceState.getStringArrayList(restore_check_box);
                arr1 = savedInstanceState.getStringArrayList(restore_check_box1);
                s2 = savedInstanceState.getString(s2_value);
            }

            /*I took parse syntax from https://parse.com/docs/android/guide#queries*/
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Spices");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    for (ParseObject obj : parseObjects) {
                        c1 = new CheckBox(Spice_Vegetable_CheckBoxes.this);
                        c1.setText(obj.getString("Spice"));
                        if (arr.contains(obj.getString("Spice")) && savedInstanceState != null) {
                            c1.setChecked(true);
                        }

                        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b == true) {
                                    arr.add(compoundButton.getText().toString());
                                } else {
                                    arr.remove(compoundButton.getText().toString());
                                }
                            }
                        });

                        l1.addView(c1);

                    }

                }
            });
            /*I took parse syntax from https://parse.com/docs/android/guide#queries*/
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Vegetables");
            query1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    for (ParseObject obj : parseObjects) {
                        c2 = new CheckBox(Spice_Vegetable_CheckBoxes.this);
                        c2.setText(obj.getString("Vegetable"));
                        if (arr1.contains(obj.getString("Spice")) && savedInstanceState != null) {
                            c2.setChecked(true);
                        }

                        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b == true) {
                                    arr1.add(compoundButton.getText().toString());
                                } else {
                                    arr1.remove(compoundButton.getText().toString());
                                }
                            }
                        });

                        l2.addView(c2);

                    }

                }
            });

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (arr.size() > 0 || arr1.size() > 0) {
                        Intent intent = new Intent(Spice_Vegetable_CheckBoxes.this, ViewResult.class);
                        intent.putExtra("unique_word", "spices_list");
                        intent.putStringArrayListExtra(other_intent, arr);
                        intent.putStringArrayListExtra(other_intent1, arr1);
                        startActivity(intent);
                    } else {
                        Toast t = Toast.makeText(getApplicationContext(), R.string.please_select_options,
                                Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();
                    }
                }
            });
        } catch (Exception e2) {
            Log.i(TAG, e2.toString());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(restore_check_box, arr);
        outState.putStringArrayList(restore_check_box1, arr1);
        outState.putString(s2_value, s2);
        super.onSaveInstanceState(outState);
    }
}
