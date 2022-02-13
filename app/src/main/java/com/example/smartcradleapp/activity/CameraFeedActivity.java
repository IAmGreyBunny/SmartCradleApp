package com.example.smartcradleapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.smartcradleapp.R;
import com.example.smartcradleapp.Settings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CameraFeedActivity extends AppCompatActivity {

    WebView webView;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_feed);

        webView = findViewById(R.id.webView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        //Add listener to user setting from firebase rtdb
        ValueEventListener settingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                settings = snapshot.getValue(Settings.class);
                webView.loadUrl(settings.webappAddress);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("listener","settings values listener failed");
            }
        };
        mDatabase.child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Settings")
                .addValueEventListener(settingsListener);
    }
}