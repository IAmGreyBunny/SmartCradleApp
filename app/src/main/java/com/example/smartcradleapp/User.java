package com.example.smartcradleapp;

public class User {
    public String username,email;

    //Creates empty user
    public User(){

    }

    //Pass username and email to user
    //This will be the nodes under firebase rtdb
    public User(String username,String email)
    {
        this.username = username;
        this.email = email;
    }
}
