package com.example.smartcradleapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smartcradleapp.BabyMonitoringService;
import com.example.smartcradleapp.R;
import com.example.smartcradleapp.Settings;
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
    Button saveSettingsButton;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://iot-smart-cradle-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Assigning UI Elements
        rpiAddressEditText = findViewById(R.id.rpiAddressEditText);
        webappAddressEditText = findViewById(R.id.webappAddressEditText);
        saveSettingsButton= findViewById(R.id.saveSettingsButton);

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

    //changes settings in firebase rtdb
    public void onSaveSettingsButtonClick(View v)
    {
        //VALIDATION TO BE ADDED LATER

        //Gets input data and store in an object
        settings.rpiAddress = rpiAddressEditText.getText().toString();
        settings.webappAddress = webappAddressEditText.getText().toString();

        //Set new setting values in firebase
        mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Settings").setValue(settings);
    }


    //Start and stop baby monitoring service, activated by clicking on buttons
    public void startBabyMonitoringService(View v){
        Intent serviceIntent  = new Intent(this, BabyMonitoringService.class);
        startService(serviceIntent);
    }

    public void stopBabyMonitoringService(View v)
    {
        Intent serviceIntent  = new Intent(this, BabyMonitoringService.class);
        stopService(serviceIntent);
    }

}