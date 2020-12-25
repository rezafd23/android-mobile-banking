package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.ui.helper.SingleActivity;

public class DialogActivity extends SingleActivity {

    private AppCompatImageView iv_icon,ic_back;
    private AppCompatButton btn_continue;
    private AppCompatTextView tv_title,tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initView();
        setupView();
    }


    private void initView(){
        iv_icon=findViewById(R.id.iv_icon);
        ic_back=findViewById(R.id.ic_back);
        btn_continue=findViewById(R.id.btn_continue);
        tv_title=findViewById(R.id.tv_title);
        tv_description=findViewById(R.id.tv_description);
    }

    public static void navigateDialog(Activity activity,String status){
        Intent intent = new Intent(activity,DialogActivity.class);
        intent.putExtra("status",status);
        activity.startActivity(intent);
    }

    private void setupView(){
        switch (getIntent().getStringExtra("status")){
            case "success_register":
                ic_back.setOnClickListener(view -> {
                    MainActivity.navigate(DialogActivity.this,true);
                });
                btn_continue.setOnClickListener(view -> {
                    MainActivity.navigate(DialogActivity.this,true);
                });
                break;
            case "failed":
                tv_title.setText(getString(R.string.system_error_title));
                tv_description.setText(getString(R.string.system_error_desc));
                iv_icon.setImageResource(R.drawable.ic_technicalissue);
                btn_continue.setText("Kembali");
                btn_continue.setOnClickListener(view -> {
                    finish();
                });
                ic_back.setOnClickListener(view -> {
                    finish();
                });
                break;
            case "failed_token":
                tv_title.setText(getString(R.string.system_error_title));
                tv_description.setText(getString(R.string.system_error_desc_token));
                iv_icon.setImageResource(R.drawable.ic_technicalissue);
                btn_continue.setText("Kembali");
                btn_continue.setOnClickListener(view -> {
                    MainActivity.navigate(DialogActivity.this,true);
                });
                ic_back.setOnClickListener(view -> {
                    MainActivity.navigate(DialogActivity.this,true);
                });
                break;
        }
    }
}