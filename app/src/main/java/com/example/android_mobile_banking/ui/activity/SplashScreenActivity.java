package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;

public class SplashScreenActivity extends SingleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    if (Util.getData(getApplication(), "status_login").equals("true")) {
                        Intent intent = new Intent(getApplicationContext(), LoginPinActivity.class);
                        SplashScreenActivity.this.startActivity(intent);
                        finish();
                    } else {
                       LoginActivity.navigate(SplashScreenActivity.this,true);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}