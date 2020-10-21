package com.example.moviesearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EventListener;

public class QueryResultsActivity extends AppCompatActivity {

    private TextView tvTitle;
    private ListView lvSearchResults;
    private String[] title;
    private String[] year;
    private String[] director;

    //private String[] genre;
    //private String[] rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_results);

        lvSearchResults=findViewById(R.id.listviewQueryResults);
        //ArrayList<String> mArray = new ArrayList<String>();

        final ArrayList<Movie> moviesList = (ArrayList<Movie>) getIntent().getSerializableExtra("LIST");
        tvTitle = findViewById(R.id.textViewTitle);
//        tvTitle.setText("Showing top " + moviesList.size() + " results:");

        title = new String[moviesList.size()];
        year =  new String[moviesList.size()];
        director =  new String[moviesList.size()];
        //genre =  new String[moviesList.size()];
        //rating =  new String[moviesList.size()];

        //Log.d("View activity", "Size of the array: " + moviesList.size());
        for(int i = 0 ; i < moviesList.size(); i ++){
            title[i] = moviesList.get(i).getName();
            year[i] = Integer.toString(moviesList.get(i).getYear());
            director[i] = moviesList.get(i).getDirector();


        }
        MyAdapter myAdapter = new MyAdapter(this, title, year, director);

        lvSearchResults.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        //Creating a on click listener for list view
        lvSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        for(int x=0;x<moviesList.size();x++){
            if(x==i){
                 Intent in=new Intent(getApplicationContext(),movieDes.class);
                 Bundle bun=new Bundle();

                 in.putExtra("mTitle",title[x]);
                 in.putExtra("mYear",year[x]);
                in.putExtra("mDirector",director[x]);
                 in.putExtra("position",""+x+1);
                System.out.println(title[x]+year[x]);
                Log.d("view activity", "inside intent: " + title[x]+year[x]);
                 startActivity(in);

            }
        }
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String title[];
        String year[];
        String director[];

        MyAdapter (Context c, String title[], String year[], String director[]){
            super(c, R.layout.row, R.id.textViewMovieTitle, title);
            this.context = c;
            this.title = title;
            this.year = year;
            this.director = director;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView myTitle = row.findViewById(R.id.textViewMovieTitle);
            TextView myYear = row.findViewById(R.id.textViewYear);
            TextView myDirector = row.findViewById(R.id.textViewDirector);
            myTitle.setText(title[position]);
            myYear.setText("Year of release: " +year[position]);
            myDirector.setText("Directed by " + director[position]);
            return row;
        }
    }
}