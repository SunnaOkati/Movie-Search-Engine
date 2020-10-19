package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

/*
* import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;*/

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class QueryResults extends AppCompatActivity {

    private ImageView reslogo;
    private ListView searchRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_results);

        reslogo=findViewById(R.id.resLogo);
        searchRes=findViewById(R.id.Qres);
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
        //ArrayList<String> mArray = new ArrayList<String>();

        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("LIST");

        ArrayAdapter<String> ArrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);

        searchRes.setAdapter(ArrAdapter);

        //mArray.add(url);
        ArrAdapter.notifyDataSetChanged();
    }

    // ListView lView = (ListView) findViewById(R.id.Qres);





}