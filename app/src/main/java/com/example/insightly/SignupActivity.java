package com.example.insightly;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private EditText name, email, password, confirmPassword;
    private Button signUp;
    private TextView logIn;
    private ImageView hide1, hide2;

    // Patterns
    private final String patternEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$";
    private final String patternPassword = ".{6,}"; // At least 6 characters

    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // Initialize UI components
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_confirm_password);
        signUp = findViewById(R.id.btn_signup);
        logIn = findViewById(R.id.tv_login);
        hide1 = findViewById(R.id.iv_hide1);
        hide2 = findViewById(R.id.iv_hide2);
        progressBar = findViewById(R.id.pb_progress);

        auth = FirebaseAuth.getInstance();

        // Email Validation
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches(patternEmail)) {
                    email.setError("Invalid format! Example: xxx@gmail.com");
                } else {
                    email.setError(null);
                }
            }
        });

        // Password Validation
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 6) {
                    password.setError("Password must be at least 6 characters long");
                } else {
                    password.setError(null);
                }
            }
        });

        // Sign Up Button
        signUp.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            String nameText = name.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString();
            String confirmPasswordText = confirmPassword.getText().toString();

            if (nameText.isEmpty()) {
                name.setError("Please enter your name");
                name.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (emailText.isEmpty()) {
                email.setError("Please enter your email");
                email.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (!emailText.matches(patternEmail)) {
                email.setError("Invalid email format");
                email.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (passwordText.isEmpty()) {
                password.setError("Please enter a password");
                password.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (passwordText.length() < 6) {
                password.setError("Password must be at least 6 characters");
                password.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (!passwordText.equals(confirmPasswordText)) {
                confirmPassword.setError("Passwords do not match");
                confirmPassword.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Firebase Sign Up
            auth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(verificationTask -> {
                                            if (verificationTask.isSuccessful()) {
                                                Toast.makeText(SignupActivity.this,
                                                        "Sign-up successful! Verification email sent.",
                                                        Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(SignupActivity.this,
                                                        "Failed to send verification email.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Log In Button
        logIn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}
