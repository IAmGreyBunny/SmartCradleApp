package com.example.smartcradleapp.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcradleapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    //Notification channel
    public static final String CHANNEL_ID = "Baby Monitoring Service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        createNotificationChannel();

        Intent moveToLoginPage = new Intent(this,LoginActivity.class);
        startActivity(moveToLoginPage);
        finish();
    }

    //Creates Notification Channel
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID,
                    "Baby Monitoring Service",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
            Log.d("listener","Notification Channel Created");

        }
    }
}
