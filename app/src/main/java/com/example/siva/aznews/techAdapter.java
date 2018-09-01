package com.example.siva.aznews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by siva on 16/7/18.
 */

public class techAdapter extends ArrayAdapter<techList> {

    public techAdapter(Activity context, ArrayList<techList> tech) {
        super(context, 0, tech);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        String location_Separator = "T";
        String end = "...";

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.default_layout, parent, false);
        }


        techList currentWord = getItem(position);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_id);

        try {
            Picasso.get()
                    .load(currentWord.getmImage())
                    .into(imageView);
        } catch(NullPointerException e) {
            /* sets image to default image

             */
            imageView.setImageResource(R.drawable.default_img);

        }
        TextView title = (TextView) listItemView.findViewById(R.id.title_id);
        String final_string;
        try {
            if(currentWord.getmTitle().length()>75){
                final_string = currentWord.getmTitle().substring(0,75) + end;
                title.setText(final_string);
            }
            else
                title.setText(currentWord.getmTitle());
        } catch (NullPointerException e){

            title.setText("Visit article");
        }

        //String start = "";
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        try {

            String starting = currentWord.getmDate();
            String[] original_string = starting.split(location_Separator);
            String locationOffset = original_string[0];
            date.setText(locationOffset);
        } catch (NullPointerException e){
            date.setText("Check Article");
        }


        return listItemView;


    }


}
