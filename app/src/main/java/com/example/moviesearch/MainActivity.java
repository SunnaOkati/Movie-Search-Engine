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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moviesearch.BinaryTree.BinaryTree;
import com.example.moviesearch.BinaryTree.EmptyBinaryTree;
import com.example.moviesearch.BinaryTree.NonEmptyBinaryTree;
import com.example.moviesearch.Utils.Soundex;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String user;
    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnSearch;
    private Button btnLogOut;
    private EditText editTextquery;
    private List<Movie> movies = new ArrayList<>();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static Tokenizer tokenizer;
    private static Parser parser;
    private TextView helpText;
    private BinaryTree tree;

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
       btnLogOut = findViewById(R.id.buttonLogOut);
       editTextquery = findViewById(R.id.queryText);
       helpText=findViewById(R.id.helpScr);

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


        //Help activity for help screen.
        helpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inHelper = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(inHelper);

            }
        });


        //verifies if the user is already signed in
        mFirebaseAuth = FirebaseAuth.getInstance();
        if(mFirebaseAuth.getCurrentUser() != null){
            btnSignIn.setVisibility(View.INVISIBLE);
            btnSignUp.setVisibility(View.INVISIBLE);
        }

        //Describes the action for search button click action
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextquery.getText().toString().isEmpty()) //if the query is left empty
                    Toast.makeText(getApplicationContext(),"Please enter a query", Toast.LENGTH_SHORT).show();
                else {

                    tokenizer = new Tokenizer(editTextquery.getText().toString());

                    //Parsing given tokens
                    parser = new Parser(tokenizer);

                    //Extracting search terms
                    List<SearchTerm> searchTerms = parser.getSearchTerms();

                    //Filter the data provided in "json" file wrt to query
                    List<Movie> re;
                    re=filterMovieData(searchTerms);
                    Intent intent = new Intent(getApplicationContext(), QueryResultsActivity.class);
                    intent.putExtra("LIST",(Serializable) re);
                    intent.putExtra("USER", user);
                    startActivity(intent);
                }
            }
        });

        //Brings up the Sign up page
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intSignUp);
            }
        });

        //Brings up the Sign In page
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignIn = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intSignIn);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.getInstance().signOut();
                if (mFirebaseAuth.getCurrentUser() == null){
                    btnLogOut.setVisibility(View.INVISIBLE);
                    btnSignIn.setVisibility(View.VISIBLE);
                    btnSignUp.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean findStringSimilarity(String name, String movieName) {
        //Log.d("Query: ", editTextquery.getText().toString().toLowerCase());
        // if the movie name is longer than 0, ie. it actually got put in, then
        // perform the query to find string similarity. else, consider them similar.
        if (movieName.length() > 0) {
            return name.toLowerCase().charAt(0) == movieName.toLowerCase().charAt(0);
        }
        else {
            return true;
        }

    }

    /**
     * Load a pre-existing movie dataset from an JSON file.
     * Filter the movies read based on the first letter of the movie
     * i.e.,movie data read from json is added to list only if the first letter of that movie
     * is same as the first letter of movie name in user query
     * @param file The file to load from. This is guaranteed to exist.
     */
    public void loadFromJSONFile(String file, String movieName, int searchYearQuant, int searchYear) {

        String json;
        tree = new EmptyBinaryTree();
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

                String ID = Soundex.encode(name);
                String genre = jsonObject.getString("genre");
                int year = Integer.parseInt(jsonObject.getString("year"));
                String country = jsonObject.getString("country");
                String director = jsonObject.getString("director");
                String rating = jsonObject.getString("rating");
                Double score = Double.parseDouble(jsonObject.getString("score"));
                String star = jsonObject.getString("star");
                String writer = jsonObject.getString("writer");
                int runtime = Integer.parseInt(jsonObject.getString("runtime"));
                int price = Integer.parseInt(jsonObject.getString("price"));
                ID = ID + year + genre;

                Movie m = new Movie(ID, country, director, genre, name, rating, runtime, score, star, writer, year, price);
                //Added to the array list if first character matches
                //ID is computed using Soundex algorithm and oncatenated with year and genre

                //Create an instance of empty binary tree which will be later used to build a tree on which
                //searching is going to be performed

                if(findStringSimilarity(name, movieName)){

                    if(searchYearQuant == 62){
                        if (m.getYear() > searchYear) {
                            tree = tree.insert(m);
                            count++;
                        }
                    }
                    else if (searchYearQuant == 60){
                        if (m.getYear() < searchYear) {
                            tree = tree.insert(m);
                            count++;
                        }
                    }
                    else if(searchYearQuant == 61){
                        if (m.getYear() == searchYear) {
                            tree = tree.insert(m);
                            count++;
                        }
                    }

                    //movies.add(new Movie(ID, country, director, genre, name, rating, runtime, score, star, writer, year, price));
                    //movies.add(new Movie(ID, name, genre, year));
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

    /**
     * Filtering:---------
     * Initially filtering happens in "loadFromJSONFile(...)"
     * Secondary filtering is performed using release year and its qunatifiers.
     * Initialy filtered results are stored in array list and
     * secondary results are inserted into tree.
     * Searching:----------
     * First step is to find the leaf node most relavant
     * Next, traverse backwards till we reach the root node
     * @param searchTerms output from the parser
     */
    private List filterMovieData(List<SearchTerm> searchTerms) {

        String fileName = "dataset.json";

        int searchYear;
        int searchYearQuant;
        String searchGenre;
        String searchMovieName;
        String searchID;
        Movie searchMovie;

        // Creating an instance of movie with the user query

        // initialises the following values by creating generic searchterms
        searchYear = 0;
        searchYearQuant = (int) '>';
        searchGenre = "";
        searchMovieName = "";

        // for each element in the search terms, sets the search query data based on this
        // information.
        for (SearchTerm searchTerm : searchTerms) {
            if (searchTerm != null) {
                // gets the search term's search query type
                String queryField = searchTerm.getSearchCategory();

                System.out.println(queryField);

                // based on the search category, changes the search year and other attributes
                if (queryField.equals("\"movie\"")) {
                    // casts the term to searchMatch
                    SearchMatch searchMatch = (SearchMatch) searchTerm;

                    // based on this, sets the moviename
                    searchMovieName = searchMatch.getSearchQuery();
                }
                else if (queryField.equals("\"genre\"")) {
                    // casts the term to searchMatch
                    SearchMatch searchMatch = (SearchMatch) searchTerm;

                    // based on this, sets the genre
                    searchGenre = searchMatch.getSearchQuery();
                }
                if (queryField.equals("\"year\"")) {
                    // casts the term to searchQuant
                    SearchQuant searchQuant = (SearchQuant) searchTerm;

                    // based on this, sets the year quantifier and year
                    searchYearQuant = (int) searchQuant.getQuantifier().charAt(0);
                    searchYear = searchQuant.getQuantity();
                }
            }
        }

        searchID = Soundex.encode(searchMovieName) + searchYear + searchGenre;

        searchMovie = new Movie(searchID, searchMovieName, searchGenre, searchYear);

        //Log.d("Display activity", "Movie searched: " + searchMovie + " Quantifier: " + searchYearQuant);

        //Loading data from json
        loadFromJSONFile(fileName, searchMovieName, searchYearQuant, searchYear);
        //Log.d("Insert activity", "Nodes inserted: " + tree.size() + " Height of the tree: " + tree.height());

        //The leaf node that is most relavant to query is found
        tree = tree.find(searchMovie);

        //Tree is traversed from bottom to top (i.e leaf to root)
        //which gives us the path. The ones in the path are considered as candidate results
        List<Movie> results = new ArrayList<Movie>();

        if (tree != null) {
            results = ((NonEmptyBinaryTree)tree).relavantResults();
        }

        return results;
    }

}