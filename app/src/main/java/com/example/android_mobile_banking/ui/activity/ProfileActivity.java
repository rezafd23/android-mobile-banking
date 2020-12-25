package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.listener.DialogClickListener;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.util.WidgetUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProfileActivity extends SingleActivity {

    private AppCompatImageView ic_back;
    private AppCompatTextView tv_nama,tv_no_rekening,tv_no_hp,tv_email,tv_no_ktp,tv_npwp,
            tv_no_card,tv_cvv,tv_card_type,tv_limit,tv_card_category,tv_expired_at;
    private AppCompatButton btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
        setupView();

        ic_back.setOnClickListener(view -> {
            onBackPressed();
        });
        btn_logout.setOnClickListener(view -> {
            WidgetUtil.showDoubleButtonDialog(this, "Tunggu Dulu!", "Apakah Yakin untuk keluar dari akun anda?.",
                    "Ya", new DialogClickListener() {
                        @Override
                        public void onClick(Dialog dialog, View view) {
                            LoginActivity.navigate(ProfileActivity.this,true);
                            Util.removeData(getApplication(),"status_login");
                            Util.removeData(getApplication(),"data_nasabah");
                            Util.removeData(getApplication(),"username");
                        }
                    },
                    "Ya, Batalkan", new DialogClickListener() {
                        @Override
                        public void onClick(Dialog dialog, View view) {
                            dialog.dismiss();
                        }
                    }, true);
        });
    }

    public static void navigate(Activity activity,String app_status){
        Intent intent = new Intent(activity,ProfileActivity.class);
        intent.putExtra("app_status",app_status);
        activity.startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initView(){
        ic_back=findViewById(R.id.ic_back);

        tv_nama=findViewById(R.id.tv_nama);
        tv_no_hp=findViewById(R.id.tv_no_hp);
        tv_no_ktp=findViewById(R.id.tv_no_ktp);
        tv_email=findViewById(R.id.tv_email);
        tv_npwp=findViewById(R.id.tv_npwp);

        tv_no_rekening=findViewById(R.id.tv_no_rekening);
        tv_no_card=findViewById(R.id.tv_no_card);
        tv_cvv=findViewById(R.id.tv_cvv);
        tv_card_type=findViewById(R.id.tv_card_type);
        tv_limit=findViewById(R.id.tv_limit);
        tv_card_category=findViewById(R.id.tv_card_category);
        tv_expired_at=findViewById(R.id.tv_expired_at);
        btn_logout=findViewById(R.id.btn_logout);
    }

    private void setupView(){

        if (getIntent().getStringExtra("app_status").equals("NOT_YET")){
            tv_nama.setText("");
            tv_no_ktp.setText("");
            tv_no_hp.setText("");
            tv_email.setText("");
            tv_npwp.setText("");

            tv_no_rekening.setText("");
            tv_no_card.setText("");
            tv_cvv.setText("");
            tv_card_type.setText("");
            tv_limit.setText("");
            tv_card_category.setText("");
            tv_expired_at.setText("");
        } else {
            JsonParser parser =new JsonParser();

            JsonObject jsonObject = (JsonObject)parser.parse(Util.getData(getApplication(),"data_nasabah"));

            tv_nama.setText(jsonObject.getAsJsonObject("personal").get("nama").toString().replace("\"",""));
            tv_no_ktp.setText(jsonObject.getAsJsonObject("personal").get("no_ktp").toString().replace("\"",""));
            tv_no_hp.setText(jsonObject.get("username").toString().replace("\"",""));
            tv_email.setText(jsonObject.getAsJsonObject("personal").get("email").toString().replace("\"",""));
            tv_npwp.setText(jsonObject.getAsJsonObject("work").get("npwp").toString().replace("\"",""));

            tv_no_rekening.setText(jsonObject.getAsJsonObject("banking").get("no_rekening").toString().replace("\"",""));
            tv_no_card.setText(jsonObject.getAsJsonObject("banking").get("card_no").toString().replace("\"",""));
            tv_cvv.setText(jsonObject.getAsJsonObject("banking").get("cvv").toString().replace("\"",""));
            tv_card_type.setText(jsonObject.getAsJsonObject("banking").get("card_type").toString().replace("\"",""));
            tv_limit.setText(jsonObject.getAsJsonObject("banking").get("card_daily_limit").toString().replace("\"",""));
            tv_card_category.setText(jsonObject.getAsJsonObject("banking").get("card_category").toString().replace("\"",""));
            tv_expired_at.setText(jsonObject.getAsJsonObject("banking").get("expired_at").toString().replace("\"",""));
        }


    }
}