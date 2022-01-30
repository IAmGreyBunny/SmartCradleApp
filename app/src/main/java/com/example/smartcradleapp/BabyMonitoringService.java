package com.example.smartcradleapp;

import static com.example.smartcradleapp.activity.SplashScreenActivity.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.smartcradleapp.activity.CameraFeedActivity;
import com.example.smartcradleapp.activity.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class BabyMonitoringService extends Service {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Detections detections;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    public void onCreate(){
        super.onCreate();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        try {
            mediaPlayer.setDataSource(this,uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){

        Intent notificationIntent = new Intent(this, CameraFeedActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Baby Monitoring Service")
                .setContentText("Listening to database for baby cry")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .build();

        //Add listener to user setting from firebase rtdb
        ValueEventListener detectionsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                detections = snapshot.getValue(Detections.class);
                if (detections.audioCryDetected == 1 && detections.visualCryDetected == 1)
                {

                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    if(mediaPlayer.isPlaying())
                    {
                        mediaPlayer.stop();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("listener","values listener failed");
            }
        };
        mDatabase.child("Users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("Detections")
                .addValueEventListener(detectionsListener);

        startForeground(1,notification);
        //Log.d("listener","settings values listener failed");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
    }


    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
}
