package com.example.insightly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button logIn;
    private TextView signUp;
    private ImageView googleBtn, facebookBtn;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        logIn = findViewById(R.id.btn_login);
        googleBtn = findViewById(R.id.iv_google);
        facebookBtn = findViewById(R.id.iv_fb);
        signUp = findViewById(R.id.tv_signup);

        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.pb_progress);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Login pressed!", Toast.LENGTH_SHORT).show();

                String email1 = email.getText().toString();
                String password1 = password.getText().toString();

                if (email1.isEmpty()) {
                    email.setError("Email is empty!");
                    email.requestFocus();

                } else if (password1.isEmpty()) {
                    password.setError("Password is empty!");
                    password.requestFocus();

                }
                else {
                    //login via email and password
                    auth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);

                                    //checks if sign in successful or not
                                    if(task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();

                                        //Email verification, extra security
                                        if(user != null && user.isEmailVerified()) {
                                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            //Email is not verified
                                            Toast.makeText(getApplicationContext(), "Email is not verified!", Toast.LENGTH_SHORT).show();
                                            auth.signOut();
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                    );
                }



            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sign up using google!", Toast.LENGTH_SHORT).show();

            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sign up using fb!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}