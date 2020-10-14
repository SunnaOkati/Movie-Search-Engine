package com.example.moviesearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {
    EditText emailID, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        btnSignUp = findViewById(R.id.buttonSignUp);
        tvSignIn = findViewById(R.id.textViewSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if (email.isEmpty()) {
                    emailID.setError("Please enter email id");
                    emailID.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter password");
                    password.requestFocus();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(register.this, "Sign up unsuccessful, Please try again", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(register.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                                Intent intSignIn = new Intent(register.this, login.class);
                                startActivity(intSignIn);
                            }
                        }
                    });
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register.this, login.class));
            }
        });
    }

}
