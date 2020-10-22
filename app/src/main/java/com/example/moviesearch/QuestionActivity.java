package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

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

        System.out.println("\nNext activity reached");
        String textQuestion;
        String textAnswer = "";

        Intent intent =  getIntent();
        String question = intent.getStringExtra("Question");
        ArrayList<String> questionList = intent.getStringArrayListExtra("listOfQuestions");

        System.out.println("hi");
        textQuestion = question;
        if(question.compareTo(questionList.get(0)) == 0)
        {
            textAnswer = "Query format : \"movie\" : movie_name \"year\" < or = or > year \"genre\" : genre_name \n\n" +
                    "The query consists of three categories. All the categories need to be mentioned. These" +
                    " categories are: movie name, year and genre. The query needs to be entered in the same order as" +
                    "shown above.\n\n"+
                    "Example:\n"+
                    "\"movie\" : cat \"year\" = 2001 \"genre\" : comedy";
        }
        if(question.compareTo(questionList.get(1)) == 0)
        {
            textAnswer = "The query results into list of movies related to the query Entered. If the movie"+
                    " doesn't exist in the database, it would still display some movies.";
        }
        if(question.compareTo(questionList.get(2)) == 0)
        {
            textAnswer = "Do's\n"+
                    "1. Strictly Follow the Query Format.\n"+
                    "2. Have a little patience while the App searches for your movie.\n\n"+
                    "Dont's\n"+
                    "1. Search doesn't support search for only one category. So all the three " +
                    "categories, i.e., movie name, year and genre should be included in the query.\n"+
                    "2. Don't include number in movie name.";
        }
        helpQuestion.setText(textQuestion);
        helpAnswer.setText(textAnswer);
    }
}