package com.example.shailesh.youcanalsocook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class Login extends Activity {
    Button login, signup, reset_password;
    EditText uname, pswd;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        if (isNetworkAvailable()) {
            Toast t = Toast.makeText(getApplicationContext(), R.string.welcome,
                    Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();

        } else {
            Toast t = Toast.makeText(getApplicationContext(), R.string.connect_to_internet,
                    Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
        }

        login = (Button) findViewById(R.id.btnLogin);
        signup = (Button) findViewById(R.id.btnSignup);
        uname = (EditText) findViewById(R.id.username);
        pswd = (EditText) findViewById(R.id.password);
        reset_password = (Button) findViewById(R.id.reset_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = uname.getText().toString().trim(), password = pswd.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                        /*I took reference for alert dialog code from http://developer.android.com/guide/topics/ui/dialogs.html*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage(R.string.enter_details_2).setTitle(R.string.info_missing).setPositiveButton(R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                        /*I took syntax of parse from from https://parse.com/docs/android/guide#users-logging-in*/
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e == null) {
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                    /*I took reference for alert dialog code from http://developer.android.com/guide/topics/ui/dialogs.html*/
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage(e.getMessage()).setTitle(R.string.info_missing).setPositiveButton(R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ResetPassword.class);
                startActivity(intent);
            }
        });

    }

    /*took referece for network checking from http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html*/
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
