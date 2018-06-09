package com.patang.agora;

import android.net.Uri;

public class User {
    public String userEmail;
    public String password;
    public String userType;

    public int threshold_temperature_min = 0;
    public int threshold_temperature_max = 0;

    public int threshold_humidity_min = 0;
    public int threshold_humisdity_max = 0;
    public Uri photo;
    public int notifications = 0;

    //public String current_location = "Model Town";

    public User(String email,String type,Uri photo){
        this.userEmail=email;
        this.userType=type;
        this.photo=photo;
    }
    public User(String email,String type){
        this.userEmail=email;
        this.userType=type;
    }

    public User(){

    }
}
