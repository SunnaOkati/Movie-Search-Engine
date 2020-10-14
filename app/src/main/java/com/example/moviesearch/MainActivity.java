package com.example.moviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Button btnSignIn;
    Button btnSignUp;

    private Button StButton;
    private ImageView logo;
    private EditText query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // gets the logo image
        ImageView logo = findViewById(R.id.logo_image);

        //gets the buttons
        btnSignUp = findViewById(R.id.buttonSignUp);
        btnSignIn = findViewById(R.id.buttonSignIn);

        // sets the image for the logo from the assets.
        try {
            // gets the input stream, loads as drawable
            InputStream inputStream = getAssets().open("logo_app_1.png");
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            // sets the imageView image
            logo.setImageDrawable(drawable);

            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        StButton = findViewById(R.id.button2);
        query = findViewById(R.id.queryText);

        StButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String q = query.getText().toString();
                if (q.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty Query", Toast.LENGTH_SHORT).show();
                } else {
                    Intent in = new Intent(getApplicationContext(), QueryResults.class);
                    startActivity(in);

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
            }
        });
    }
}