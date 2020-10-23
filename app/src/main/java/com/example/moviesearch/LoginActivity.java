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
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {
    EditText emailID, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // gets the back button
        ImageButton backButton = findViewById(R.id.backButton);

        // gets the logo image
        ImageView logo = findViewById(R.id.logo_image2);

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
        catch(IOException ex)
        {
            return;
        }

        System.out.println(logo == null);

        try{
            // gets the input stream, loads as drawable
            InputStream inputStream = getAssets().open("logo_app_1.png");
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            // sets the imageView image
            logo.setImageDrawable(drawable);

            inputStream.close();
        }
        catch(IOException ex)
        {
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
        btnSignIn = findViewById(R.id.buttonSignIn);
        tvSignUp = findViewById(R.id.textViewSignUp);

        // listens for any changes in the state of the firebase authorisation object.
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mFirebaseUser!=null){
                    Toast.makeText(LoginActivity.this, "You are logged in ", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // begins the login process
        btnSignIn.setOnClickListener(new View.OnClickListener() {
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
                    // sends the field information over firebase, verifies if the user credentials
                    // are valid.
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                                Toast.makeText(LoginActivity.this, "Sign in Unsuccessful, Please try again", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(LoginActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        }
                    });
                }
            }
        });

        // redirects to the sign up page.
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

}