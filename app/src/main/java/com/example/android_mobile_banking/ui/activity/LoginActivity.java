package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.listener.DialogClickListener;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.viewmodel.AuthViewModel;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class LoginActivity extends SingleActivity {

    private TextInputEditText et_phone;
    private AppCompatTextView tv_sign_up, tv_title1, tv_title2, tv_tnc;
    private AppCompatButton btn_signup;
    private AppCompatCheckBox cb_tnc;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setSignUpPage();
        Log.v("isi Token: ", "isi: "+Util.getData(getApplication(),"status_login"));

        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_sign_up.getText().equals("SIGNUP")) {
                    setSignUpPage();
                } else {
                    setLoginPage();
                }
            }
        });

        cb_tnc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    LoginTermDialog dialog = new LoginTermDialog(LoginActivity.this);
                    dialog.show();
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            MainActivity.navigate(LoginActivity.this, true);
                        }
                    });
                    dialog.setButtonAgree("Saya Setuju", new DialogClickListener() {
                        @Override
                        public void onClick(Dialog dialog, View view) {
                            dialog.dismiss();
                        }
                    });
                    btn_signup.setEnabled(true);
                } else {
                    btn_signup.setEnabled(false);
                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.setData(getApplication(),"status_login","true");
                showProgressDialog();
                if (btn_signup.getText().equals("SIGNUP")) {
                    boolean valid = checkPhoneNumber(et_phone.getText().toString());
                    if (valid) {
                        registerData(et_phone.getText().toString());
                    } else {
                        dismissProgressDialog();
                        et_phone.setError("Nomor Anda Tidak Valid");
                    }
                } else {
                    boolean valid = checkPhoneNumber(et_phone.getText().toString());
                    if (valid){
                        loginData(et_phone.getText().toString());
                    } else {
                        dismissProgressDialog();
                        et_phone.setError("Nomor Anda Tidak Valid");
                    }
                }
            }
        });

    }

    private boolean checkPhoneNumber(String s) {
        if (s.length() > 9 && s.length() <= 13 && s.startsWith("08")) {
            return true;
        } else {
            return false;
        }
    }

    private void registerData(String username){
        try {
            JSONObject object = new JSONObject();
            object.put("username", username);
            authViewModel.registerPhone(object)
                    .observe(this, registerResponse -> {
                        dismissProgressDialog();
                        if (registerResponse.getResponse().equals("200")) {
                            Util.setData(getApplication(),"username",et_phone.getText().toString());
                            Util.setData(getApplication(),"otp",registerResponse.getOtp());
                            Util.setData(getApplication(),"otpId",registerResponse.getOtpId());
                            OtpVerificationActivity.navigate(LoginActivity.this,"register_phone");
                        } else if (registerResponse.getPayload().equals("Phone Number is Registered!")) {
                            et_phone.setError("Nomor Anda Telah Terdaftar");
                        } else {
                            Toast.makeText(getApplicationContext(), "Sistem Error, Mohon Tunggu!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Log.v("error1: ", "Try Set Username");
            e.printStackTrace();
        }
    }

    private void loginData(String username){
        try {
            JSONObject object = new JSONObject();
            object.put("username", username);
            authViewModel.loginPhone(object)
                    .observe(this, registerResponse -> {
                        dismissProgressDialog();
                        if (registerResponse.getResponse().equals("200")) {
                            Util.setData(getApplication(),"username",et_phone.getText().toString());
                            Util.setData(getApplication(),"otp",registerResponse.getOtp());
                            Util.setData(getApplication(),"otpId",registerResponse.getOtpId());
                            OtpVerificationActivity.navigate(LoginActivity.this,"login_phone");
                        } else if (registerResponse.getPayload().equals("Phone Number is Registered!")) {
                            et_phone.setError("Nomor Anda Telah Terdaftar");
                        } else {
                            Toast.makeText(getApplicationContext(), "Sistem Error, Mohon Tunggu!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Log.v("error1: ", "Try Set Username");
            e.printStackTrace();
        }
    }

    public static void navigate(Activity activity){
        Intent intent = new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void navigate(Activity activity,boolean clearPrevStack){
        Intent intent = new Intent(activity,LoginActivity.class);
        if (clearPrevStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }

    private void initView() {
        et_phone = findViewById(R.id.et_phone);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        tv_title1 = findViewById(R.id.tv_title1);
        tv_title2 = findViewById(R.id.tv_title2);
        btn_signup = findViewById(R.id.btn_signup);
        tv_tnc = findViewById(R.id.tv_tnc);
        cb_tnc = findViewById(R.id.cb_tnc);

        tv_tnc.setText(Html.fromHtml(getResources().getString(R.string.agree_checked)));
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        authViewModel.init();

    }

    private void setSignUpPage() {
        et_phone.setText("");
        cb_tnc.setChecked(false);
        tv_title1.setText("Ayo Gunakan");
        tv_title2.setText("mySyaria");
        tv_sign_up.setText("LOGIN");
        btn_signup.setText("SIGNUP");
    }

    private void setLoginPage() {
        et_phone.setText("");
        cb_tnc.setChecked(false);
        tv_title1.setText("Selamat \nDatang Kembali");
        tv_title2.setText("di mySyaria");
        tv_sign_up.setText("SIGNUP");
        btn_signup.setText("LOGIN");
    }
}