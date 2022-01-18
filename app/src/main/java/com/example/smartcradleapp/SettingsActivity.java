package com.example.smartcradleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    //Declaration of UI Elements
    EditText rpiAddressEditText;
    EditText webappAddressEditText;
    Button changeRpiAddressButton;
    Button changeWebappAddressButton;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://iot-smart-cradle-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Assigning UI Elements
        rpiAddressEditText = findViewById(R.id.rpiAddressEditText);
        webappAddressEditText = findViewById(R.id.webappAddressEditText);
        changeRpiAddressButton= findViewById(R.id.changeRpiAddressButton);
        changeWebappAddressButton = findViewById(R.id.changeWebappAddressButton);


        //Add listener to user setting from firebase rtdb
        ValueEventListener settingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                settings = snapshot.getValue(Settings.class);
                rpiAddressEditText.setText(settings.rpiAddress);
                webappAddressEditText.setText(settings.webappAddress);
                Log.d("listener","setting text to: " + settings.rpiAddress + "," + settings.webappAddress);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("listener","settings values listener failed");
            }
        };
        mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Settings").addValueEventListener(settingsListener);
    }

    public void onChangeRpiAddressButtonClick(View v)
    {

    }

    public void onChangeWebappAddressButtonClick(View v)
    {

    }
}