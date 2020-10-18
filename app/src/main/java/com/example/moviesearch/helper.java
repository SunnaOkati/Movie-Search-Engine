package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class helper extends AppCompatActivity {

    private TextView helpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        helpContent=findViewById(R.id.helpCon);

        String htmlText = "<p> This App would give results based on the query entered by the user </p> <p> query format is name of movie and year and,genre </p> <p> The top 5 relevant results are displayed based on the query entered  </p>";
        helpContent.setText(Html.fromHtml(htmlText));
    }
}