package com.example.moviesearch;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviesearch.BinaryTree.BinaryTree;
import com.example.moviesearch.BinaryTree.EmptyBinaryTree;
import com.example.moviesearch.BinaryTree.NonEmptyBinaryTree;
import com.example.moviesearch.Tree.RBTree;
import com.example.moviesearch.Tree.Soundex;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnSearch;
    private EditText editTextquery;
    private List<Movie> movies = new ArrayList<>();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    int count = 0;
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

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mFirebaseUser!=null){
                    Toast.makeText(MainActivity.this, "You are logged in" + mFirebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
                    btnSignIn.setVisibility(View.INVISIBLE);
                    btnSignUp.setVisibility(View.INVISIBLE);
                }
            }
        };
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextquery.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Please enter a query", Toast.LENGTH_SHORT).show();
                else {
                //TODO: Ashok please edit
                    filterMovieData();

                    BinaryTree tree = new EmptyBinaryTree();
                    for (Movie m: movies){
                        tree = tree.insert(m);
                        count++;
                    }
                    Log.d("My activity", "   count: " + count );
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
    /**
     * Load a pre-existing movie dataset from an JSON file.
     *
     * @param file The file to load from. This is guaranteed to exist.
     */
    public void loadFromJSONFile(String file) {


        String json;
        try {
            InputStream is = getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");

                if(findStringSimilarity(name)){
                    String ID = Soundex.encode(name);
                    String genre = jsonObject.getString("genre");
                    int year = Integer.parseInt(jsonObject.getString("year"));
                    movies.add(new Movie(ID, name, genre, year));
                }
            }
            //Log.d("Added activity", "Movies added.");

        }
        catch(Exception e)
        {
            Log.wtf("Load activity", "Error occured while reading file " + file);
            e.printStackTrace();
        }

    }

    private boolean findStringSimilarity(String name) {
        //Log.d("Query: ", editTextquery.getText().toString().toLowerCase());
        if(name.toLowerCase().charAt(0) == editTextquery.getText().toString().toLowerCase().charAt(0))
            return true;

        return false;
    }

    private void filterMovieData() {

        String fileName = "dataset.json";
        loadFromJSONFile(fileName);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}