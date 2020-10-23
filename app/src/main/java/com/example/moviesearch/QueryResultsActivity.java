package com.example.moviesearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QueryResultsActivity extends AppCompatActivity {

    private String username;
    private TextView tvTitle;
    private ListView lvSearchResults;
    private String[] title;
    private String[] year;
    private String[] director;
    private Boolean[] favourites;
    private Drawable favRed;
    private String[] rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_results);

        // gets the back button
        ImageButton backButton = findViewById(R.id.backButton);

        // attempts to set the back button image
        // sets the image for the logo from the assets.
        try{
            // gets the input stream, loads as drawable
            InputStream inputStream = getAssets().open("back_arrow.png");
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            // sets the imageView image
            backButton.setImageDrawable(drawable);

            inputStream.close();
        }
        catch (IOException ex) {
            return;
        }

        // sets up the back button to go back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvSearchResults=findViewById(R.id.listviewQueryResults);
        favRed = getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24);

        username = getIntent().getStringExtra("USER");

        final ArrayList<Movie> moviesList = (ArrayList<Movie>) getIntent().getSerializableExtra("LIST");
        tvTitle = findViewById(R.id.textViewTitle);
        tvTitle.setText("Showing top " + moviesList.size() + " results:");

        BufferedReader rawreader = null;
        String line;

        // gets the details for the movie that is being currently assessed.
        title = new String[moviesList.size()];
        year =  new String[moviesList.size()];
        director =  new String[moviesList.size()];
        favourites = new Boolean[moviesList.size()];
        rating =  new String[moviesList.size()];

        // for each item in the movie list, adds to the string arrays.
        for (int i = 0 ; i < moviesList.size(); i ++) {
            title[i] = moviesList.get(i).getName();
            year[i] = Integer.toString(moviesList.get(i).getYear());
            rating[i] = Double.toString(moviesList.get(i).getScore());
            director[i] = moviesList.get(i).getDirector();
            favourites[i] = false;
        }

        try { // note that Try-with-resources requires API level 19
            rawreader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.data), "UTF-8")); //the encoding is optional String line;
            while ((line = rawreader.readLine()) != null) { //read each line until end of file
                // break each line into tokens (note that we are reading a csv file (comma-separated values)
                String[] tokens = line.split(",");

                // if the username isn't null, check a user's favourites.
                if (username != null) {
                    if (tokens[0].contains(username)) {
                        for (int i = 1; i < tokens.length; i++) {
                            for (int j = 0 ; j<title.length; j++){
                                if (tokens[i].equals(title[j])) {
                                    favourites[j] = true;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"File not found",Toast.LENGTH_SHORT).show();
        }

        // creates a unique adapter object for the movie list.
        MyAdapter myAdapter = new MyAdapter(this, title, year, director, rating, favourites);

        lvSearchResults.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        //Creating a on click listener for list view
        lvSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

            // iterates over each movie in the movielist
            for (int x = 0; x < moviesList.size(); x++) {
                if (x == i) {
                     Intent in = new Intent(getApplicationContext(), MovieDes.class);
                     Bundle bun = new Bundle();

                     // pushes the movie title, year and director to the intent.
                     in.putExtra("mTitle", title[x]);
                     in.putExtra("mYear", year[x]);
                     in.putExtra("mDirector", director[x]);
                     in.putExtra("mRating", rating[x]);
                     in.putExtra("position",""+x+1);

                     startActivity(in);
                }
            }
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{
        // initialises variables
        Context context;
        String title[];
        String year[];
        String director[];
        String rating[];
        Boolean favourites[];

        // creates an adapter for the results list
        MyAdapter (Context c, String title[], String year[], String director[], String rating[], Boolean favourites[]){
            super(c, R.layout.row, R.id.textViewMovieTitle, title);
            this.context = c;
            this.title = title;
            this.year = year;
            this.director = director;
            this.rating = rating;
            this.favourites = favourites;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);

            // gets the row's views for formatting.
            TextView myTitle = row.findViewById(R.id.textViewMovieTitle);
            TextView myRating = row.findViewById(R.id.textViewRating);
            TextView myYear = row.findViewById(R.id.textViewYear);
            TextView myDirector = row.findViewById(R.id.textViewDirector);
            Button btnFavourite = row.findViewById(R.id.buttonFavourite);

            // if the movie has been favourited, then set the favourite button to red.
            if (favourites[position])
                btnFavourite.setBackground(favRed);

            // sets the title of the movie
            myTitle.setText(title[position]);

            // sets the rating, year and director for the movie
            myRating.setText(rating[position] + " Average Rating");
            myYear.setText("Year of release: " + year[position]);
            myDirector.setText("Directed by " + director[position]);

            return row;
        }
    }
}