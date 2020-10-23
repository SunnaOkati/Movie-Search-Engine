package com.example.moviesearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {
    EditText emailID, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // gets the back button
        ImageButton backButton = findViewById(R.id.backButton);

        // gets the logo image
        ImageView logo = findViewById(R.id.logo_image2);

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

        try {
            // gets the input stream, loads as drawable
            InputStream inputStream = getAssets().open("logo_app_1.png");
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            // sets the imageView image
            logo.setImageDrawable(drawable);

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

        // gets the firebase authorisation instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        // gets the field elements in the activity
        emailID = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);

        // gets the log in button and sign up redirect
        btnSignUp = findViewById(R.id.buttonSignUp);
        tvSignIn = findViewById(R.id.textViewSignUp);

        // begins the sign up process.
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // gets the input email and password.
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                // checks whether or not the input email or password are empty.
                if (email.isEmpty()) {
                    emailID.setError("Please enter email id");
                    emailID.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter password");
                    password.requestFocus();
                } else {
                    // sends the field information over firebase, creates a new user using the data.
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(RegisterActivity.this, "Sign up unsuccessful, Please try again", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(RegisterActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                                Intent intSignIn = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intSignIn);
                            }
                        }
                    });
                }
            }
        });

        // redirects to the login page.
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

}
