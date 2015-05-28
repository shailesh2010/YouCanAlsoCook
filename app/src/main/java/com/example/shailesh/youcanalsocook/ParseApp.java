package com.example.shailesh.youcanalsocook;

import android.app.Application;

import com.parse.Parse;


public class ParseApp extends Application {
   @Override
    public void onCreate(){
       super.onCreate();
       /*I took the below code from parse.com*/
       Parse.initialize(this,"uoz0IyUxL02823NfAiU0bW9kyroIxfu1q61ewILk","tm6k9bRzip2iKhU6yoc7KtPs0Ma7fSjXQTeRYpZz");
   }
}
