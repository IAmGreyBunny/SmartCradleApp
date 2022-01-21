package com.example.smartcradleapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.smartcradleapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    //Move to Camera Feed
    public void onViewCameraFeedButtonClick(View v){
        Intent moveToCameraFeedPage = new Intent(MainMenuActivity.this,CameraFeedActivity.class);
        startActivity(moveToCameraFeedPage);
    }

    //Move to Statistics Page
    public void onStatisticsButtonClick(View v){
        Intent moveToStatisticsPage = new Intent(MainMenuActivity.this,StatisticsActivity.class);
        startActivity(moveToStatisticsPage);
    }

    //Move to Settings Page
    public void onSettingsButtonClick(View v){
        Intent moveToSettingPage = new Intent(MainMenuActivity.this,SettingsActivity.class);
        startActivity(moveToSettingPage);
    }

    //Logs out of firebase
    public void onLogoutButtonClick(View v){
        FirebaseAuth.getInstance().signOut();
        Intent moveToLoginPage = new Intent(MainMenuActivity.this,LoginActivity.class);
        startActivity(moveToLoginPage);
        finish();
    }
}