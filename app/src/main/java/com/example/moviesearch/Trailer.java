package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Trailer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);


        Intent in=getIntent();
        //String u= in.getStringExtra("l");
        String u="https://www.youtube.com";
       // if(!u.isEmpty()){
            WebView we=(WebView) findViewById(R.id.movieTrailer);
           // we.getSettings().setJavaScriptEnabled(true);
            we.setWebViewClient(new WebViewClient());
            we.loadUrl(u);


        }
    }
