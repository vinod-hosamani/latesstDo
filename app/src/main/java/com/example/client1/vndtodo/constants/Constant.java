package com.example.client1.vndtodo.constants;

/**
 * Created by client1 on 9/8/2017.
 */

public class Constant
{
    public static final String mobilepattern="^(\\+91-|\\+91|0)?[7-9]{1}([0-9]){9}$";
    /*public static String email_pattern="^([a-z][A-Z][0-9]\\.){3,}@([a-z]){3,}\\.[a-z]+$";*/
    public static final String email_pattern="^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()" +
            "\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}" +
            "\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    public static final String key_firebase_user = "users";
}
