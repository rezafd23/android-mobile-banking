package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.android_mobile_banking.R;

public class ResponseToken extends AppCompatActivity {

    private AppCompatTextView tv_token;
    private AppCompatButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_token);

        initView();
        setupView();

        btn_back.setOnClickListener(view -> {
            MainActivity.navigate(ResponseToken.this,true);
        });
    }

    private void initView(){
        tv_token=findViewById(R.id.tv_token);
        btn_back=findViewById(R.id.btn_back);
    }
    public static void navigation(Activity activity,String token){
        Intent intent =new Intent(activity,ResponseToken.class);
        intent.putExtra("token",token);
        activity.startActivity(intent);
    }

    private void setupView(){
        String no_token = getIntent().getStringExtra("token");
        String token = "";
        for (int i = 0; i < no_token.length(); i++) {
            if (i % 4 == 0) {
                token=token+" "+no_token.charAt(i);
            } else {
                token=token+no_token.charAt(i);
            }
        }
        tv_token.setText(token);
    }
}