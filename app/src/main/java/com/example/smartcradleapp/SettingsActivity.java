package com.example.smartcradleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    //Declaration of UI Elements
    EditText rpiAddressEditText;
    EditText webappAddressEditText;
    Button changeRpiAddressButton;
    Button changeWebappAddressButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Assigning UI Elements
        rpiAddressEditText = findViewById(R.id.rpiAddressEditText);
        webappAddressEditText = findViewById(R.id.webappAddressEditText);
        changeRpiAddressButton= findViewById(R.id.changeRpiAddressButton);
        changeWebappAddressButton = findViewById(R.id.changeWebappAddressButton);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onChangeRpiAddressButtonClick(View v)
    {

    }

    public void onChangeWebappAddressButtonClick(View v)
    {

    }
}