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
    private LinearLayoutCompat btn_capture_ktp,btn_capture_face,
            personal_detail;

//    layout Personal
    private AppCompatTextView tv_ktp,tv_email,tv_pendidikan_terakhir,tv_status_perkawinan,
        tv_alamat_rumah,tv_provinsi,tv_kabupaten,tv_kecamatan,tv_kelurahan,tv_rtrw,tv_status_rumah;

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
        activity.finish();
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

//        layout Personal data
        personal_detail=findViewById(R.id.personal_detail);
        tv_ktp=findViewById(R.id.tv_ktp);
        tv_email=findViewById(R.id.tv_email);
        tv_pendidikan_terakhir=findViewById(R.id.tv_pendidikan_terakhir);
        tv_status_perkawinan=findViewById(R.id.tv_status_perkawinan);
        tv_alamat_rumah=findViewById(R.id.tv_alamat_rumah);
        tv_provinsi=findViewById(R.id.tv_provinsi);
        tv_kabupaten=findViewById(R.id.tv_kabupaten);
        tv_kecamatan=findViewById(R.id.tv_kecamatan);
        tv_kelurahan=findViewById(R.id.tv_kelurahan);
        tv_rtrw=findViewById(R.id.tv_rtrw);
        tv_status_rumah=findViewById(R.id.tv_status_rumah);


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
                            setupPage(userResponse.getPayload());
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
            String personal_status=jsonObject.getJSONObject("regis_data_status").getString("personal");
            String relative_status=jsonObject.getJSONObject("regis_data_status").getString("relative");
            String work_info=jsonObject.getJSONObject("regis_data_status").getString("work");
            String capture_ktp=jsonObject.getJSONObject("regis_data_status").getString("ektp");
            String capture_face=jsonObject.getJSONObject("regis_data_status").getString("ekyc");
            if (personal_status.equals("Y")){
                JSONObject responData=jsonObject.getJSONObject("personal");
                progressBar.setProgress(20);
                tv_progress.setText("20% Compleated");
                personal_detail.setVisibility(View.VISIBLE);
                tv_ktp.setText(responData.getString("no_ktp"));
                tv_email.setText(responData.getString("email"));
                tv_pendidikan_terakhir.setText(responData.getString("education"));
                tv_status_perkawinan.setText(responData.getString("marital"));
                tv_alamat_rumah.setText(responData.getString("address"));
                tv_provinsi.setText(responData.getString("province"));
                tv_kabupaten.setText(responData.getString("city"));
                tv_kecamatan.setText(responData.getString("district"));
                tv_kelurahan.setText(responData.getString("sub_district"));
                tv_rtrw.setText(responData.getString("rt")+"/"+responData.getString("rw"));
                tv_status_rumah.setText(responData.getString("living_status"));
            }
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