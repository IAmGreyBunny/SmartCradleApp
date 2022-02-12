package com.example.smartcradleapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.smartcradleapp.R;
import com.example.smartcradleapp.SensorReadings;
import com.example.smartcradleapp.Settings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StatisticsActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> temperatureSeries;
    LineGraphSeries<DataPoint> humiditySeries;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    SensorReadings sensorReadings;

    private static int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        GraphView temperatureGraph = (GraphView) findViewById(R.id.temperatureGraph);
        GraphView humidityGraph = (GraphView) findViewById(R.id.humidityGraph);
        temperatureSeries=new LineGraphSeries<>();
        humiditySeries=new LineGraphSeries<>();
        temperatureGraph.addSeries(temperatureSeries);
        humidityGraph.addSeries(humiditySeries);

        //Add listener to sensors from firebase rtdb
        ValueEventListener sensorsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sensorReadings = snapshot.getValue(SensorReadings.class);
                temperatureSeries.appendData(new DataPoint(x++,sensorReadings.temperature),false,100);
                humiditySeries.appendData(new DataPoint(x++,sensorReadings.humidity),false,100);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("listener","settings values listener failed");
            }
        };
        mDatabase.child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sensors")
                .addValueEventListener(sensorsListener);

    }
}