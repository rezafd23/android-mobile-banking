package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.repositories.AuthController;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.viewmodel.AuthViewModel;

import org.json.JSONObject;

public class OtpVerificationActivity extends SingleActivity {

    private AppCompatTextView tv_OTP;
    private PinEntryEditText pinEntry;
    private AppCompatTextView tv_alert;
    private AppCompatTextView tv_resend_otp;
    private AuthController authController = new AuthController();
    private AuthViewModel authViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        initView();


        pinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0){
                    pinEntry.setError(false);
                    pinEntry.setPinLineColors(getColorStateList(R.color.dark_blue));
                    tv_alert.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (pinEntry!=null){
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
//                    Log.v("isiPIN: ","ISI"+str.toString());
//                    Log.v("isiPIN: ","ISIuser"+getIntent().getStringExtra("username"));
//                    Log.v("isiPIN: ","ISIPIN"+ str.toString());
//                    Log.v("isiPIN: ","ISIOTPID"+ getIntent().getStringExtra("otpId"));
                    submitOTP(Util.getData(getApplication(),"username"),
                            str.toString(),
                            Util.getData(getApplication(),"otpId"));
//                    Toast.makeText(getApplicationContext(),str.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public static void navigate(Activity activity){
        Intent intent = new Intent(activity,OtpVerificationActivity.class);
        activity.startActivity(intent);

    }
    private void initView(){
        tv_OTP=findViewById(R.id.tv_OTP);
        tv_alert=findViewById(R.id.tv_alert);
        tv_resend_otp=findViewById(R.id.tv_resend_otp);
        pinEntry=findViewById(R.id.otp_verification_txt);
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        tv_OTP.setText(Util.getData(getApplication(),"otp"));
    }

    private void submitOTP(String username, String otp, String otpId){
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
                           CreatePinActivity.navigate(OtpVerificationActivity.this);
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
}