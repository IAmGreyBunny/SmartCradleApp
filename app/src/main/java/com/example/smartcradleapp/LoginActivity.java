package com.example.smartcradleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Declaration of UI Elements
    private Button loginButton;
    private Button registerButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Assigning UI Elements
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        mAuth = FirebaseAuth.getInstance();
    }

    //onClick listener for login button
    public void loginButtonClick(View v){

        //Gets user input
        String email =  emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        //Email validations
        if(email.isEmpty()){
            emailEditText.setError("Email field cannot be empty");
            emailEditText.requestFocus();
            return;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email format is invalid");
            emailEditText.requestFocus();
            return;
        }

        //Password validations
        if(password.isEmpty())
        {
            passwordEditText.setError("Password field cannot be empty");
            passwordEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //LOGIN SUCCESSFUL(To be implemented)
                    Intent goToMainMenu = new Intent(LoginActivity.this,MainMenuActivity.class);
                    startActivity(goToMainMenu);
                    finish(); // Closes login activity
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Login Unsuccessful, Please Check Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //onClick listener for register button
    //Change activity to Register Activity
    public void registerButtonClick(View v){
        Log.d("registerButtonClick","Transferring to register page...");
        Intent launchRegisterActivity = new Intent(this,RegisterActivity.class);
        startActivity(launchRegisterActivity);
    }
}