package com.example.shailesh.youcanalsocook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.ParseException;
import com.parse.RequestPasswordResetCallback;


public class ResetPassword extends Activity {
    String s = "a";
    EditText email;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email = (EditText) findViewById(R.id.reset_email);
        b1 = (Button) findViewById(R.id.reset_button_1);
        b2 = (Button) findViewById(R.id.move_to_login1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s = email.getText().toString();
                if (s.equals("")) {
                    Toast t = Toast.makeText(getApplicationContext(), R.string.enter_email,
                            Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                } else {
                    try {
                        /*I took the parse syntax from https://parse.com/docs/android/guide#users-resetting-passwords*/
                        ParseUser.requestPasswordResetInBackground(s, new RequestPasswordResetCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast t = Toast.makeText(getApplicationContext(), R.string.link_sent,
                                            Toast.LENGTH_LONG);
                                    t.setGravity(Gravity.CENTER, 0, 0);
                                    t.show();
                                } else {
                                    /*I took reference for alert dialog code from http://developer.android.com/guide/topics/ui/dialogs.html*/
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassword.this);
                                    builder.setMessage(e.getMessage()).setTitle(R.string.error).setPositiveButton(R.string.ok, null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }
                        });
                    } catch (Exception ex4) {
                        Log.i("Error : ", ex4.getMessage().toString());
                    }
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPassword.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

}
