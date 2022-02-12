package com.example.smartcradleapp;

public class Settings {
    public String rpiAddress,webappAddress;
    public int toyMotorStrength,swingMotorStrength;

    //Creates empty user
    public Settings(){

    }

    //Pass username and email to user
    //This will be the nodes under firebase rtdb
    public Settings(String rpiAddress,String webappAddress,int toyMotorStrength,int swingMotorStrength)
    {
        this.rpiAddress = rpiAddress;
        this.webappAddress = webappAddress;
        this.toyMotorStrength = toyMotorStrength;
        this.swingMotorStrength = swingMotorStrength;
    }
}
