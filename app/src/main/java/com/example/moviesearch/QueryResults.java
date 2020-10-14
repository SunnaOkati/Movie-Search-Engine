package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class QueryResults extends AppCompatActivity {

    private ImageView reslogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_results);

        reslogo=findViewById(R.id.resLogo);
        try
        {
            // gets the input stream, loads as drawable
            InputStream inputStream = getAssets().open("logo_app_1.png");
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            // sets the imageView image
            reslogo.setImageDrawable(drawable);

            inputStream.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }


}