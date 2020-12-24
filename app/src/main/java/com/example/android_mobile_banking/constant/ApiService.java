package com.example.android_mobile_banking.constant;

public class ApiService {

    public static String BASE_URL="http://192.168.18.5:8080/";
    public static String BASE_URL_2="http://192.168.18.5:3000";

//    Auth
    public static String register=BASE_URL+"api/auth/register/";
    public static String submitOTP=BASE_URL+"api/auth/submitOtp/";
    public static String createPIN=BASE_URL+"api/auth/createPin/";
    public static String loginPIN=BASE_URL+"api/auth/loginPin/";
    public static String loginPhone=BASE_URL+"api/auth/login/";

//    Nasabah
    public static String getData=BASE_URL+"api/nasabah/getData/";
    public static String addPersonalData=BASE_URL+"api/nasabah/addPersonalData/";
    public static String addRelativeData=BASE_URL+"api/nasabah/addRelativeData/";
    public static String addWorkData=BASE_URL+"api/nasabah/addWorkData/";
    public static String finishRegister=BASE_URL+"api/nasabah/finishRegister/";
    public static String uploadEKTP=BASE_URL_2+"/app/api/user/uploadEktp";
    public static String getMutasi=BASE_URL+"/api/nasabah/getMutasi/{no_rekening}/{start_date}/{end_date}";

//    Token
    public static String getTokenList=BASE_URL+"api/token/getVoucer/";
    public static String checkUser=BASE_URL+"api/token/checkUser/{no_pelanggan}";
    public static String buyToken=BASE_URL+"api/token/buyToken/";



}
