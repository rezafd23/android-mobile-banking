package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.viewmodel.AuthViewModel;
import com.example.android_mobile_banking.viewmodel.TokenViewModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class OtpVerificationActivity extends SingleActivity {

    private AppCompatTextView tv_OTP;
    private PinEntryEditText pinEntry;
    private AppCompatTextView tv_alert;
    private AppCompatTextView tv_resend_otp;
    private AuthViewModel authViewModel;
    private TokenViewModel tokenViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        initView();

       setupView();

        pinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    pinEntry.setError(false);
                    pinEntry.setPinLineColors(getColorStateList(R.color.dark_blue));
                    tv_alert.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
//                    Log.v("isiPIN: ","ISI"+str.toString());
//                    Log.v("isiPIN: ","ISIuser"+getIntent().getStringExtra("username"));
//                    Log.v("isiPIN: ","ISIPIN"+ str.toString());
//                    Log.v("isiPIN: ","ISIOTPID"+ getIntent().getStringExtra("otpId"));
                    submitOTP(Util.getData(getApplication(), "username"),
                            str.toString(),
                            Util.getData(getApplication(), "otpId"));
//                    Toast.makeText(getApplicationContext(),str.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void setupView(){
        switch (getIntent().getStringExtra("otp_status")) {
            case "register_phone":
                tv_resend_otp.setOnClickListener(view -> {
                    resendOtp(Util.getData(getApplication(), "username"));
                });
                break;
            case "validasi_token":
                tv_resend_otp.setOnClickListener(view -> {
                    resendOtp2(Util.getData(getApplication(), "username"));
                });
                break;
            case "login_phone":
                tv_resend_otp.setOnClickListener(view -> {
                    resendOtp2(Util.getData(getApplication(), "username"));
                });
                break;
        }
    }

    public static void navigate(Activity activity, String otp_status) {
        Intent intent = new Intent(activity, OtpVerificationActivity.class);
        intent.putExtra("otp_status", otp_status);
        activity.startActivity(intent);

    }
    public static void navigate(Activity activity, String otp_status,String params) {
        Intent intent = new Intent(activity, OtpVerificationActivity.class);
        intent.putExtra("otp_status", otp_status);
        intent.putExtra("params", params);
        activity.startActivity(intent);

    }

    private void initView() {
        tv_OTP = findViewById(R.id.tv_OTP);
        tv_alert = findViewById(R.id.tv_alert);
        tv_resend_otp = findViewById(R.id.tv_resend_otp);
        pinEntry = findViewById(R.id.otp_verification_txt);
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        tv_OTP.setText(Util.getData(getApplication(), "otp"));
        tokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
    }

    private void submitOTP(String username, String otp, String otpId) {
        showProgressDialog();
        try {
            JSONObject object = new JSONObject();
            object.put("username", username);
            object.put("otp", otp);
            object.put("otpId", otpId);
            authViewModel.submitOtpRegister(object)
                    .observe(this, authResponse -> {
                        dismissProgressDialog();
                        if (authResponse.getResponse().equals("200")) {
                            switch (getIntent().getStringExtra("otp_status")) {
                                case "register_phone":
                                    CreatePinActivity.navigate(OtpVerificationActivity.this);
                                    break;
                                case "login_phone":
                                    Util.setData(getApplication(),"status_login","true");
                                    LoginPinActivity.navigate(OtpVerificationActivity.this,true);
                                    break;
                                case "validasi_token":
                                    buyToken();
                                    break;
                            }
                        } else {
                            pinEntry.setText("");
                            pinEntry.setPinLineColors(getColorStateList(R.color.colorTextError));
                            tv_alert.setVisibility(View.VISIBLE);
                            pinEntry.setError(true);
                        }
                    });
        } catch (Exception e) {
            Log.v("error1: ", "Try Set Username");
            e.printStackTrace();
        }
    }

    private void resendOtp(String username){
        showProgressDialog();
        try {
            JSONObject object = new JSONObject();
            object.put("username", username);
            authViewModel.registerPhone(object)
                    .observe(this, registerResponse -> {
                        dismissProgressDialog();
                        if (registerResponse.getResponse().equals("200")) {
                            tv_OTP.setText(registerResponse.getOtp());
                            Util.setData(getApplication(),"otp",registerResponse.getOtp());
                            Util.setData(getApplication(),"otpId",registerResponse.getOtpId());
                        } else {
                            Toast.makeText(getApplicationContext(), "Sistem Error, Mohon Tunggu!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Log.v("error1: ", "Try Set Username");
            e.printStackTrace();
        }
    }

    private void resendOtp2(String phone_number){
        showProgressDialog();
        try {
            authViewModel.generateOtp(phone_number)
                    .observe(this, authResponse -> {
                        dismissProgressDialog();
                        if (authResponse.getResponse().equals("200")) {
                           try {
                               tv_OTP.setText(authResponse.getPayloadData().getString("otp"));
                               Util.setData(getApplication(),"otpId",authResponse.getPayloadData().getString("id"));
                           }catch (Exception e){
                               Log.v("error: ","error set OTP");
                               e.printStackTrace();
                           }
                        } else {
                            Toast.makeText(getApplicationContext(), "Sistem Error, Mohon Tunggu!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Log.v("error1: ", "Try Set Username");
            e.printStackTrace();
        }
    }

    private void buyToken(){
        showProgressDialog();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject1= (JsonObject) parser.parse(getIntent().getStringExtra("params"));


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nominal", jsonObject1.get("nominal").toString());
            jsonObject.put("id_nasabah_card", Integer.parseInt(jsonObject1.get("id_nasabah_card").toString()));
            jsonObject.put("nama_transaksi", jsonObject1.get("nama_transaksi").toString().replace("\"",""));
            jsonObject.put("no_pelanggan", jsonObject1.get("no_pelanggan").toString().replace("\"",""));
            jsonObject.put("id_voucer", Integer.parseInt(jsonObject1.get("id_voucer").toString()));

            tokenViewModel.buyToken(jsonObject,Util.getData(getApplication(),"access_token")).
                    observe( this, tokenResponse -> {
                        dismissProgressDialog();
                        if (tokenResponse.getResponse().equals("200")) {
                            try {
                                Log.v("isiResponseBuyToken: ", tokenResponse.getPayloadUser().toString());
                                ResponseToken.navigation(OtpVerificationActivity.this, tokenResponse.getPayloadUser().getString("token"));
                            } catch (Exception e) {
                                DialogActivity.navigateDialog(OtpVerificationActivity.this, "failed");
                                Log.v("Error: ", "Check user");
                                e.printStackTrace();
                            }
                        } else {
                            DialogActivity.navigateDialog(OtpVerificationActivity.this, "failed_token");
//                            Toast.makeText(getApplicationContext(),tokenResponse.getStatus(),Toast.LENGTH_SHORT).show();
                        }
                    });

        }catch (Exception e){
            dismissProgressDialog();
            Log.v("error: ","Error Beli Token");
            e.printStackTrace();
        }
    }
}