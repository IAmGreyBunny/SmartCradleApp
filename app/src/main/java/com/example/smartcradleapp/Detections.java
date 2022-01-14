package com.example.smartcradleapp;

public class Detections {
    public int visualCryDetected,audioCryDetected;

    //Creates empty user
    public Detections(){

    }

    //Pass username and email to user
    //This will be the nodes under firebase rtdb
    public Detections(int visualCryDetected,int audioCryDetected)
    {
        this.visualCryDetected = visualCryDetected;
        this.audioCryDetected = audioCryDetected;
    }
}
