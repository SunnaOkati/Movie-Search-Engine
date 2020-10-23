package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class HelpActivity extends AppCompatActivity {
    ArrayList<String> questions = new ArrayList<>(
            Arrays.asList("How do you enter queries?",
                    "What kind of fields can I use?"));
    ListView list;
    ArrayAdapter arradap;
    TextView faqs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // gets the back button
        ImageButton backButton = findViewById(R.id.backButton);

        // attempts to set the back button image
        // sets the image for the logo from the assets.
        try {
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

        // gets the list and FAQ elements.
        list = findViewById(R.id.list);
        faqs = findViewById(R.id.text);

        arradap = new ArrayAdapter(HelpActivity.this, R.layout.question_row, questions){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // for each element in the list, add a list item.
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
                return textView;
            }
        };

        // attaches an adapter to the list.
        list.setAdapter(arradap);
        arradap.notifyDataSetChanged();

        // for each item in the list, attaches a click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // gets the answer corresponding to the FAQ question
                String value = (String) arradap.getItem(i);

                // creates the intent to move to the FAQ answer page
                Intent inQuestion = new Intent(HelpActivity.this, QuestionActivity.class);
                inQuestion.putExtra("Question", value);
                inQuestion.putStringArrayListExtra("listOfQuestions", questions);

                // starts the activity
                startActivity(inQuestion);
            }
        });
    }
}