package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moviesearch.Tree.Node;
import com.example.moviesearch.Tree.RBTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnSearch;
    private EditText editTextquery;
    private List<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // gets the logo image
        ImageView logo = findViewById(R.id.logo_image);

        //gets the buttons and edit texts
       btnSignUp = findViewById(R.id.buttonSignUp);
       btnSignIn = findViewById(R.id.buttonSignIn);
       btnSearch = findViewById(R.id.buttonSearch);
       editTextquery = findViewById(R.id.queryText);

        // sets the image for the logo from the assets.
        try{
            // gets the input stream, loads as drawable
            InputStream inputStream = getAssets().open("logo_app_1.png");
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            // sets the imageView image
            logo.setImageDrawable(drawable);

            inputStream.close();
        }
        catch(IOException ex)
        {
            return;
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextquery.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Please enter a query", Toast.LENGTH_SHORT).show();
                else {
                //TODO: Ashok please edit
                readMovieData();
                RBTree tree = new RBTree();

                for (Movie m: movies){
                    tree.insert(m);
                }
                //Log.d("My activity", tree.preOrder());
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp = new Intent(MainActivity.this, register.class);
                startActivity(intSignUp);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, login.class));
            }
        });

    }

    private void readMovieData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        try {
            //skip the headers
            reader.readLine();

            while((line= reader.readLine())!=null){

                //Split the line by ","
                String[] tokens = line.split(",");

                if(tokens[1].charAt(0) == editTextquery.getText().toString().charAt(0)){
                    //Read the data
                    Movie movie = new Movie(tokens[1], Integer.parseInt(tokens[2]), tokens[3], Integer.parseInt(tokens[0]));
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            Log.wtf("Error reading the data file on line " + line, e);
            e.printStackTrace();
        }
    }
}