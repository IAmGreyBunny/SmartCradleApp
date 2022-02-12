package com.example.smartcradleapp;

public class SensorReadings {
    public double temperature,humidity;
    //Creates empty reading
    public SensorReadings(){

    }
    public SensorReadings(double temperature,double humidity)
    {
        this.temperature=temperature;
        this.humidity=humidity;
    }
}
