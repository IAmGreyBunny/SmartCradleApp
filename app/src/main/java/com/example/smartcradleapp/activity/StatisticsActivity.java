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
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

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

        //Create a line and add in graph
        temperatureSeries=new LineGraphSeries<>();
        temperatureGraph.addSeries(temperatureSeries);
        humiditySeries=new LineGraphSeries<>();
        humidityGraph.addSeries(humiditySeries);

        //Set range for graphview
        //temperatureGraph.getViewport().setMinY(15);
        //temperatureGraph.getViewport().setMaxY(35);
        //humidityGraph.getViewport().setMinY(20);
        //humidityGraph.getViewport().setMaxY(80);
//        temperatureGraph.getViewport().setYAxisBoundsManual(true);
//        humidityGraph.getViewport().setYAxisBoundsManual(true);

        //Set interaction for graphview
        temperatureGraph.getViewport().setScrollable(true);
        temperatureGraph.getViewport().setScalable(true);
        humidityGraph.getViewport().setScrollable(true);
        humidityGraph.getViewport().setScalable(true);

        //Format x-axis as time
//        DefaultLabelFormatter timeFormatter = new DefaultLabelFormatter() {
//            @Override
//            public String formatLabel(double value, boolean isValueX) {
//                if (isValueX) {
//                    Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Log.d("sensor",formatter.format(value));
//                    return formatter.format(value);
//                }
//                return super.formatLabel(value, isValueX);
//            }
//        };
//        temperatureGraph.getGridLabelRenderer().setLabelFormatter(timeFormatter);
//        humidityGraph.getGridLabelRenderer().setLabelFormatter(timeFormatter);

        //Format looks of graphview
        temperatureGraph.getGridLabelRenderer().setLabelsSpace(10);
        humidityGraph.getGridLabelRenderer().setLabelsSpace(10);
        temperatureGraph.getGridLabelRenderer().setHorizontalLabelsAngle(45);
        humidityGraph.getGridLabelRenderer().setHorizontalLabelsAngle(45);
        temperatureGraph.getGridLabelRenderer().setNumHorizontalLabels(10);
        humidityGraph.getGridLabelRenderer().setNumHorizontalLabels(10);
        temperatureGraph.setScaleX(1);
        humidityGraph.setScaleX(1);

        //Add listener to sensors from firebase rtdb
        ValueEventListener sensorsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sensorReadings = snapshot.getValue(SensorReadings.class);
                Calendar calendar = Calendar.getInstance();
                Date dateTime = calendar.getTime();
                Log.d("Sensor",dateTime.toString());
                temperatureSeries.appendData(new DataPoint(x,sensorReadings.temperature),false,100);
                humiditySeries.appendData(new DataPoint(x,sensorReadings.humidity),false,100);
                x++;
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