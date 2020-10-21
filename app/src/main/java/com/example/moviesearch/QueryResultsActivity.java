package com.example.moviesearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EventListener;

public class QueryResultsActivity extends AppCompatActivity {

    private String username;
    private TextView tvTitle;
    private ListView lvSearchResults;
    private String[] title;
    private String[] year;
    private String[] director;
    private Boolean[] favourites;
    private Drawable favRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_results);

        lvSearchResults=findViewById(R.id.listviewQueryResults);
        favRed = getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24);

        ArrayList<Movie> moviesList = (ArrayList<Movie>) getIntent().getSerializableExtra("LIST");
        username = getIntent().getStringExtra("USER");
        Log.d("LogIn" , "Logged in " + username);
        tvTitle = findViewById(R.id.textViewTitle);
        tvTitle.setText("Showing top " + moviesList.size() + " results:");

        BufferedReader rawreader = null;
        String line;
        title = new String[moviesList.size()];
        year =  new String[moviesList.size()];
        director =  new String[moviesList.size()];
        favourites = new Boolean[moviesList.size()];

        Log.d("View activity", "Size of the array: " + moviesList.size());
        for(int i = 0 ; i < moviesList.size(); i ++){
            title[i] = moviesList.get(i).getName();
            year[i] = Integer.toString(moviesList.get(i).getYear());
            director[i] = moviesList.get(i).getDirector();
            favourites[i] = false;
        }

        try{ //note that Try-with-resources requires API level 19
            rawreader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.data), "UTF-8")); //the encoding is optional String line;
            while ((line = rawreader.readLine()) != null) { //read each line until end of file
                String[] tokens = line.split(","); //break each line into tokens (note that we are reading a csv file (comma-separated values)
                if(tokens[0].contains(username)) {
                    Log.d("Login Activity", "User Name: " + username + " file Name: " + tokens[0]);
                    for (int i = 1; i < tokens.length; i++) {
                        for (int j = 0 ; j<title.length; j++){
                            if(tokens[i].equals(title[j])) {
                                favourites[j] = true;
                                Log.d("Favourite activity", "Favourite: " + title[j]);
                            }
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"File not found",Toast.LENGTH_SHORT).show();
        }

        MyAdapter myAdapter = new MyAdapter(this, title, year, director, favourites);

        lvSearchResults.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        //Creating a on click listener for list view
        lvSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String title[];
        String year[];
        String director[];
        Boolean favourites[];
        MyAdapter (Context c, String title[], String year[], String director[], Boolean favourites[]){
            super(c, R.layout.row, R.id.textViewMovieTitle, title);
            this.context = c;
            this.title = title;
            this.year = year;
            this.director = director;
            this.favourites = favourites;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView myTitle = row.findViewById(R.id.textViewMovieTitle);
            TextView myYear = row.findViewById(R.id.textViewYear);
            TextView myDirector = row.findViewById(R.id.textViewDirector);
            Button btnFavourite = row.findViewById(R.id.buttonFavourite);

            if (favourites[position])
                btnFavourite.setBackground(favRed);
            myTitle.setText(title[position]);
            myYear.setText("Year of release: " +year[position]);
            myDirector.setText("Directed by " + director[position]);
            return row;
        }
    }
}