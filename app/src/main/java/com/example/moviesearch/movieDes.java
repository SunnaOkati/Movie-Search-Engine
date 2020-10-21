package com.example.moviesearch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class movieDes extends AppCompatActivity {

    private TextView titleMovie,yearMovie,directorMovie;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_des);

        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }
        titleMovie=findViewById(R.id.movTitle);
        yearMovie=findViewById(R.id.movYear);
        directorMovie=findViewById(R.id.movDirector);


            Intent intent=getIntent();

           // Bundle bundle=this.getIntent().getExtras();

            String moTitle=intent.getStringExtra("mTitle");
            String moYear=intent.getStringExtra("mYear");
            String moDirector=intent.getStringExtra("mDirector");

            //Log.d("Des activity", "Des Activity: " + moTitle+moYear+moDirector);
            titleMovie.setText(moTitle);
            yearMovie.setText("Released in"+moYear);
            directorMovie.setText("Directed By "+moDirector);

            actionBar.setTitle(moTitle);


    }
}