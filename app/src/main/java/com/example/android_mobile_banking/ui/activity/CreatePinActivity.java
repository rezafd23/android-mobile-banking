package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.listener.DialogClickListener;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.util.WidgetUtil;
import com.example.android_mobile_banking.viewmodel.AuthViewModel;

import org.json.JSONObject;

public class CreatePinActivity extends SingleActivity {

    private boolean isConfirmation,wrongPin;
    private String pinNumber;

    private PinEntryEditText pinEntry;
    private AppCompatTextView tv_title,tv_description,tv_info;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);
        initView();

        doCreatePin();


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        WidgetUtil.showDoubleButtonDialog(this, "Tunggu Dulu!", "Kamu akan meninggalkan pendaftaranmu\ndan akan mulai dari awal. Apabila sudah\nyakin, pilih lanjutkan.",
                "Tidak, Kembali", new DialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {
                        dialog.dismiss();
                    }
                },
                "Ya, Batalkan", new DialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {
                        dialog.dismiss();
                        LoginActivity.navigate(CreatePinActivity.this,true);
                    }
                });
    }

    public static void navigate(Activity activity){
        Intent intent = new Intent(activity,CreatePinActivity.class);
        activity.startActivity(intent);
    }

    private void initView(){
        pinEntry=findViewById(R.id.txt_pin_entry);
        tv_title=findViewById(R.id.tv_title);
        tv_description=findViewById(R.id.tv_description);
        tv_info=findViewById(R.id.tv_info);

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
    }




    private void doCreatePin(){
        pinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0){
                    if (tv_info.getText().equals(getString(R.string.confirm_pin_invalid))&&
                            tv_info.getVisibility()==View.VISIBLE){
                        tv_info.setVisibility(View.INVISIBLE);
                    }
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
                    if (!isConfirmation){
                        pinNumber=str.toString();
                        gotoConfimation();
                    } else {
                        if (pinNumber.equals(str.toString())) {
//                            Toast.makeText(getApplicationContext(),"PIN SESUAI",Toast.LENGTH_SHORT).show();
//                            showProgressDialog();
                            savePin(Util.getData(getApplication(),"username"),pinNumber);
                        } else {
                            pinEntry.setText("");
                            pinEntry.setError(true);
                            tv_info.setText(getString(R.string.confirm_pin_invalid));
                            tv_info.setTextColor(Color.RED);
                            tv_info.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }

    private void savePin(String username,String pin){
        showProgressDialog();
        try {
            JSONObject object = new JSONObject();
            object.put("username", username);
            object.put("pin", pin);
            authViewModel.createPIN(object)
                    .observe(this, registerResponse -> {
                        dismissProgressDialog();
                        if (registerResponse.getResponse().equals("200")) {
                            Util.setData(getApplication(),"status_login","true");
                            LoginPinActivity.navigate(CreatePinActivity.this,true);
                        } else {
                            pinEntry.setText("");
                            tv_title.setText(getString(R.string.create_pin));
                            tv_info.setText("PIN harus berjumlah 6 angka, \nhindari menggunakan angka berulang");
                            tv_description.setText(getString(R.string.create_secure_pin_message));
                            Toast.makeText(getApplicationContext(),"Terjadi Kesalahan, Mohon Ulangi",Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Log.v("error1: ", "Try Set Username");
            e.printStackTrace();
        }

    }

    private void gotoConfimation() {
        isConfirmation = true;
        tv_title.setText(getString(R.string.confirm_pin));
        tv_description.setText(getString(R.string.confirm_pin_description));
        tv_info.setVisibility(View.INVISIBLE);
        pinEntry.setError(false);
        pinEntry.setText("");
    }
}