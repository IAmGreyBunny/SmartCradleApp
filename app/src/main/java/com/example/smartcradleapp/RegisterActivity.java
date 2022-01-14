package com.example.smartcradleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    // Declaring UI Elements
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    // Firebase Auth Instance
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://iot-smart-cradle-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Assign UI Elements
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);

        //Get Firebase Authentication Instance
        mAuth = FirebaseAuth.getInstance();
    }

    public void onRegisterButtonClick(View v) {

        //Gets user input
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        //User name validation
        if (username.isEmpty()) {
            usernameEditText.setError("Name is required");
            usernameEditText.requestFocus();
            return;
        }

        //Email Validation
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid Email Format");
            emailEditText.requestFocus();
            return;
        }

        //Password Validation
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        } else if (password.length() < 6) {
            passwordEditText.setError("Password length must be more than 6 characters");
            passwordEditText.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Log.d("Password", password);
            Log.d("Confirm Password", confirmPassword);
            confirmPasswordEditText.setError("Passwords are not the same");
            confirmPasswordEditText.requestFocus();
            return;
        }

        // Creates user in firebase auth, adds data to rtdb as well
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            //Registration Successful
            if (task.isSuccessful()) {
                //User object is used as nodes in RTDB data
                User user = new User(username, email);

                //Sends Data(username, email) To RTDB
                firebaseDatabase.getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(
                        task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                Log.d("Complete Registration","Added User Data To Database");
                            }
                        });

                //Goes back to Login Page(Incomplete)
                //Creates alertbox for successful registration instead of toast to make this less confusing
                //finish();

            }else{
                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
            }

        });
    }
}