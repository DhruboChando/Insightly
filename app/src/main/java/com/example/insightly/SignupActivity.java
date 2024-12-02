package com.example.insightly;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private EditText name, email, password, confirmPassword;
    Button signUp;
    TextView logIn;
    ImageView hide1,hide2;
    //pattern
    private String patternEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$";
    private String patternPassword = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$";
    private Pattern emailPattern = Pattern.compile(patternEmail);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        confirmPassword = findViewById(R.id.et_confirm_password);
        signUp = findViewById(R.id.btn_signup);
        logIn = findViewById(R.id.tv_login);
        hide1 = findViewById(R.id.iv_hide1);
        hide2 = findViewById(R.id.iv_hide2);

        String name1 = name.getText().toString();
        String email1 = email.getText().toString();
        String password1 = password.getText().toString();
        String confirmPassword1 = confirmPassword.getText().toString();

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().matches(emailPattern))
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name1.isEmpty()) {
                    name.setError("Fill up name");
                    name.requestFocus();
                } else if(email1.isEmpty()) {
                    email.setError("Fill up email");
                    email.requestFocus();
                } else if(password1.isEmpty()) {
                    password.setError("Fill up password");
                    password.requestFocus();
                } else if(confirmPassword1.isEmpty()) {
                    confirmPassword.setError("Fill up password");
                    confirmPassword.requestFocus();
                }
            }
        });
    }
}