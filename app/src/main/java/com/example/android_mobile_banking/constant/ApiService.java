package com.example.android_mobile_banking.constant;

public class ApiService {

    public static String BASE_URL="http://192.168.18.5:8080/";

//    Auth
    public static String register=BASE_URL+"api/auth/register/";
    public static String submitOTP=BASE_URL+"api/auth/submitOtp/";
    public static String createPIN=BASE_URL+"api/auth/createPin/";
    public static String loginPIN=BASE_URL+"api/auth/loginPin/";

//    Nasabah
    public static String getData=BASE_URL+"api/nasabah/getData/";
    public static String addPersonalData=BASE_URL+"api/nasabah/addPersonalData/";



}
