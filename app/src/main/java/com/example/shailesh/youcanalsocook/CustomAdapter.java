package com.example.shailesh.youcanalsocook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/*I took the idea for making custom adapter from youtube.com but didn't copied any code and implemented this adapter
 in my way since the video was implementing custom adapter with some other view*/
public class CustomAdapter extends ArrayAdapter<ParseObject> {
    List<ParseObject> list = null;
    Context context;

    public CustomAdapter(Context context, List<ParseObject> resource) {
        super(context, R.layout.row, resource);
        this.context = context;
        this.list = resource;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = (View) inflater.inflate(R.layout.row, parent, false);
        TextView t1 = (TextView) rowView.findViewById(R.id.view_item_heading);
        /*TextView t2 = (TextView) rowView.findViewById(R.id.view_item_times);*/
        final ImageView img=(ImageView)rowView.findViewById(R.id.small_image);
        String s = list.get(position).getString("RecipeName").toString();
        ParseFile abc = (ParseFile)list.get(position).get("Image");
        abc.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if(bytes!=null){
                                /*I took reference for this line of code from stackoverflow.com*/
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    img.setImageBitmap(bitmap);
                }
            }
        });
        if (s.length() > 20) {
            t1.setText(s.substring(0, 15) + "...");
        } else {
            t1.setText(s);
        }
        /*t2.setText("  Viewed:" + list.get(position).getInt("SearchedTime") + " Times");*/



        return rowView;
    }
}
