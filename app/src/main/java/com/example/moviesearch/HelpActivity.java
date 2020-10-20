package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class HelpActivity extends AppCompatActivity {
    ArrayList<String> questions = new ArrayList<>(
            Arrays.asList("How to enter query? (Query Format)",
                    "What does the query output?",
                    "Do's and Dont's"));
    ListView list;
    ArrayAdapter arradap;
    TextView faqs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        list = findViewById(R.id.list);
        faqs = findViewById(R.id.text);

        arradap = new ArrayAdapter(HelpActivity.this, android.R.layout.simple_list_item_1, questions){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
                return textView;
            }
        };
        list.setAdapter(arradap);
        arradap.notifyDataSetChanged();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String) arradap.getItem(i);
                System.out.println(value);
                Intent inQuestion = new Intent(HelpActivity.this, QuestionActivity.class);
                inQuestion.putExtra("Question", value);
                inQuestion.putStringArrayListExtra("listOfQuestions", questions);
                startActivity(inQuestion);
            }
        });
    }
}