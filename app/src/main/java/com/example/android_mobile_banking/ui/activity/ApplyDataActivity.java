package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.viewmodel.UserViewModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class ApplyDataActivity extends SingleActivity {

    private UserViewModel userViewModel;

    private AppCompatTextView tv_progress;
    private ContentLoadingProgressBar progressBar;
    private AppCompatImageView iv_arrow_personal,iv_arrow_relevan,iv_arrow_work;
    private RelativeLayout btn_relevan_info,btn_work_info;
    private LinearLayoutCompat btn_capture_ktp,btn_capture_face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_data);

        initView();
        getDataUser(Util.getData(getApplication(),"access_token"));


    }
    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, ApplyDataActivity.class);
        activity.startActivity(intent);
    }

    private void initView(){
        progressBar=findViewById(R.id.progressBar);
        tv_progress=findViewById(R.id.tv_progress);
        iv_arrow_personal=findViewById(R.id.iv_arrow_personal);
        iv_arrow_relevan=findViewById(R.id.iv_arrow_relevan);
        iv_arrow_work=findViewById(R.id.iv_arrow_work);

//        layout button
        btn_relevan_info=findViewById(R.id.btn_relevan_info);
        btn_work_info=findViewById(R.id.btn_work_info);
        btn_capture_ktp=findViewById(R.id.btn_capture_ktp);
        btn_capture_face=findViewById(R.id.btn_capture_face);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        btn_relevan_info.setEnabled(false);
        btn_work_info.setEnabled(false);
        btn_capture_ktp.setEnabled(false);
        btn_capture_face.setEnabled(false);

        addPersonalData();
        addRelativeData();
        addWorkData();

    }

    private void getDataUser(String auth) {
        showProgressDialog();
        userViewModel.getUserData(auth)
                .observe(this, userResponse -> {
                    dismissProgressDialog();
                    Log.v("isiResponse: ", userResponse.getResponse());
                    try {
                        if (userResponse.getResponse().equals("200")) {
                            Log.v("isiResponse: ", userResponse.getPayload().toString());
                            setupPage(userResponse.getPayload().getJSONObject("regis_data_status"));
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan, Mohon Ulangi", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Log.v("isiError: ",e.getMessage());
                    }

                });
    }

    private void addPersonalData(){
        iv_arrow_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyPersonalDataActivity.navigate(ApplyDataActivity.this);
            }
        });
    }
    private void addRelativeData(){
        iv_arrow_relevan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyPersonalDataActivity.navigate(ApplyDataActivity.this);
            }
        });
    }

    private void addWorkData(){
        iv_arrow_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyPersonalDataActivity.navigate(ApplyDataActivity.this);
            }
        });
    }

    private void setupPage(JSONObject jsonObject){
        try {
            String relative_status=jsonObject.getString("relative");
            String work_info=jsonObject.getString("work");
            String capture_ktp=jsonObject.getString("ektp");
            String capture_face=jsonObject.getString("ekyc");
            if (relative_status.equals("Y")){
                btn_relevan_info.setEnabled(true);
            }
            if (work_info.equals("Y")){
                btn_work_info.setEnabled(true);
            }
            if (capture_ktp.equals("Y")){
                btn_capture_ktp.setEnabled(true);
            }
            if (capture_face.equals("Y")){
                btn_capture_face.setEnabled(true);
            }
        }catch (Exception e){
            Log.v("Error: ","Error Set View Status");
        }
    }


}