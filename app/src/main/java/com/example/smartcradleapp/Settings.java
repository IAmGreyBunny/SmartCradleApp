package com.example.smartcradleapp;

public class Settings {
    public String rpiAddress,webappAddress;

    //Creates empty user
    public Settings(){

    }

    //Pass username and email to user
    //This will be the nodes under firebase rtdb
    public Settings(String rpiAddress,String webappAddress)
    {
        this.rpiAddress = rpiAddress;
        this.webappAddress = webappAddress;
    }
}
