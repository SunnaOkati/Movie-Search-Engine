package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionActivity extends AppCompatActivity {
    private TextView helpQuestion, helpAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        helpQuestion = findViewById(R.id.helpQues);
        helpAnswer = findViewById(R.id.helpAns);

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

        String textQuestion;
        String textAnswer = "";

        // gets the passed over intent
        Intent intent =  getIntent();
        String question = intent.getStringExtra("Question");
        ArrayList<String> questionList = intent.getStringArrayListExtra("listOfQuestions");

        // based on the extracted question, gets the answer text.
        textQuestion = question;
        if (question.compareTo(questionList.get(0)) == 0) {
            textAnswer = "To use the search, you can input your search as follows:\n\n"+
                    "Field : Query\n\n"+
                    "You can use as many fields in the query as you want, all you'll need to do" +
                    "is separate them using a comma! For example, you can use the following to" +
                    "search for a movie that have titles similar to 'super' from 1984 onwards:\n\n" +
                    "movie: super, year >= 1984\n\n" +
                    "Some fields use colons, though some fields use 'quantifiers' like >, = or <= " +
                    "instead. Don't worry if you get mixed up, the system will automatically convert" +
                    "colons to quantifiers and vice-versa.";
        }
        else if (question.compareTo(questionList.get(1)) == 0) {
            textAnswer = "You can use the following fields to search for movies:\n\n" +
                    "genre - Searches by a movie's genre, uses colons.\n\n" +
                    "movie - Searches based on a movie's name, uses colons.\n\n" +
                    "year - Searches by a movie's release year, uses quantifiers.";
        }

        // sets the question and answer text
        helpQuestion.setText(textQuestion);
        helpAnswer.setText(textAnswer);
    }
}