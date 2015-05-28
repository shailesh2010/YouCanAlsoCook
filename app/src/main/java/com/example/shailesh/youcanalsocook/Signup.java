package com.example.shailesh.youcanalsocook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class Signup extends Activity {
    Button signup;
    EditText uname, pswd, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = (Button) findViewById(R.id.btnSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = (EditText) findViewById(R.id.signup_username);
                pswd = (EditText) findViewById(R.id.signup_password);
                email = (EditText) findViewById(R.id.email);
                if (uname.getText().equals("") || pswd.getText().equals("") || email.getText().equals("")) {
                    /*I took reference for alert dialog code from http://developer.android.com/guide/topics/ui/dialogs.html*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    builder.setMessage(R.string.enter_details).setTitle(R.string.info_missing).setPositiveButton(R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    /*I took syntax of parse from https://parse.com/docs/android/guide#users-signing-up*/
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(uname.getText().toString());
                    newUser.setPassword(pswd.getText().toString());
                    newUser.setEmail(email.getText().toString());
                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast t = Toast.makeText(getApplicationContext(), R.string.sign_up_successful,
                                        Toast.LENGTH_LONG);
                                t.setGravity(Gravity.CENTER, 0, 0);
                                t.show();
                                Intent intent = new Intent(Signup.this, Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {
                                 /*I took reference for alert dialog code from http://developer.android.com/guide/topics/ui/dialogs.html*/
                                AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                                builder.setMessage(e.getMessage()).setTitle(R.string.error).setPositiveButton(R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });

    }


}
