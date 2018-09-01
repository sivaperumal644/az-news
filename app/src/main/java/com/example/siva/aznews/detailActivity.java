package com.example.siva.aznews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URI;

/**
 * Created by siva on 20/7/18.
 */

public final class detailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);
        getIncomingIntent();
    }
    private void getIncomingIntent(){
        if(getIntent().hasExtra("image")&&getIntent().hasExtra("title")&&getIntent().hasExtra("author")&&getIntent().hasExtra("description")&&getIntent().hasExtra("url")){
            String imageUrl = getIntent().getStringExtra("image");
            String title = getIntent().getStringExtra("title");
            String author = getIntent().getStringExtra("author");
            String description = getIntent().getStringExtra("description");
            final String url = getIntent().getStringExtra("url");
            setNews(imageUrl,title,author,description,url);
        }
    }

    public void setNews(String image, String title, String author, String description, final String url) {
        TextView Title = (TextView) findViewById(R.id.title_id);
        Title.setText(title);
        new DownloadImageTask((ImageView) findViewById(R.id.image_id))
                .execute(image);
        String start = "- ";
        String author_name;
        String author_default = "newsapi.org";
        if(author == "null")
        {
            author_name = start + author_default;
        }
        else{
            author_name = start + author;
        }
        TextView Author = (TextView) findViewById(R.id.author_id);
        Author.setText(author_name);
        TextView Description = (TextView) findViewById(R.id.description_id);
        Description.setText(description);
        TextView URL = (TextView) findViewById(R.id.url_id);
        URL.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                //Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(url);

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

    }
        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }

}
