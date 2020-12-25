package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.constant.AppConstant;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.util.WidgetUtil;
import com.example.android_mobile_banking.viewmodel.UserViewModel;
import com.example.android_mobile_banking.widget.CustomSpinner;
import com.example.android_mobile_banking.widget.CustomTextInputLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.xw.repo.XEditText;

import org.json.JSONObject;

public class ApplyWorkDataActivity extends SingleActivity {

    private CustomTextInputLayout input_npwp,input_pendapatan,input_perusahaan;
    private XEditText et_npwp,et_pendapatan,et_perusahaan;
    private CustomSpinner sp_income,sp_job_type,sp_status_kerja;
    private AppCompatButton btn_simpan;
    private AppCompatImageView ic_back;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_work_data);

        initView();
        setupView();

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void navigate(Activity activity, String personal_data) {
            Intent intent = new Intent(activity, ApplyWorkDataActivity.class);
            intent.putExtra("work_data",personal_data);
            activity.startActivity(intent);
    }
    private void initView(){
        ic_back=findViewById(R.id.ic_back);

        sp_income=findViewById(R.id.sp_income);
        sp_job_type=findViewById(R.id.sp_job_type);
        sp_status_kerja=findViewById(R.id.sp_status_kerja);

        input_npwp=findViewById(R.id.input_npwp);
        input_pendapatan=findViewById(R.id.input_pendapatan);
        input_perusahaan=findViewById(R.id.input_perusahaan);

        et_npwp=findViewById(R.id.et_npwp);
        et_pendapatan=findViewById(R.id.et_pendapatan);
        et_perusahaan=findViewById(R.id.et_perusahaan);

        btn_simpan=findViewById(R.id.btn_simpan);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void setupView(){
        try {
            setTextChange(et_npwp,input_npwp);
            setTextChange(et_pendapatan,input_pendapatan);
            setTextChange(et_perusahaan,input_perusahaan);

            sp_income.setArrowColor(getColor(R.color.dark_blue));
            sp_job_type.setArrowColor(getColor(R.color.dark_blue));
            sp_status_kerja.setArrowColor(getColor(R.color.dark_blue));

            sp_income.setHintColor(getColor(R.color.black));
            sp_job_type.setHintColor(getColor(R.color.black));
            sp_status_kerja.setHintColor(getColor(R.color.black));

            sp_income.setBaseColor(getColor(R.color.black));
            sp_job_type.setBaseColor(getColor(R.color.black));
            sp_status_kerja.setBaseColor(getColor(R.color.black));

            Typeface rubik_regular = ResourcesCompat.getFont(this, R.font.rubik_regular);
            sp_income.setTypeface(rubik_regular);
            sp_job_type.setTypeface(rubik_regular);
            sp_status_kerja.setTypeface(rubik_regular);

            WidgetUtil.setupSpinner(sp_income, AppConstant.listIncome);
            WidgetUtil.setupSpinner(sp_job_type, AppConstant.listJobType);
            WidgetUtil.setupSpinner(sp_status_kerja, AppConstant.listStatusJob);

            if (!getIntent().getStringExtra("work_data").equals("-")) {
                JSONObject workData = new JSONObject(getIntent().getStringExtra("work_data"));
                et_npwp.setText(workData.getString("npwp"));
                et_pendapatan.setText(String.valueOf(workData.getInt("income")));
                et_perusahaan.setText(workData.getString("work_office"));

                WidgetUtil.setSpinnerSelection(sp_income,workData.getString("income_src"),true);
                WidgetUtil.setSpinnerSelection(sp_job_type,workData.getString("work_type"),true);
                WidgetUtil.setSpinnerSelection(sp_status_kerja,workData.getString("work_status"),true);
            }
        } catch (Exception e){
            Log.v("error: ",e.getMessage());
        }

    }

    private void setTextChange(XEditText editText, final TextInputLayout inputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEnabledButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputLayout.getError() != null && inputLayout.getError().toString().length() > 0) {
                    inputLayout.setError(null);
                }
                checkEnabledButton();
            }
        });
    }

    private void checkEnabledButton() {
        String npwp = et_npwp.getText().toString();
        String pendapatan = et_pendapatan.getText().toString();
        String tmpt_kerja = et_perusahaan.getText().toString();

        boolean flag = true;

        if (npwp.isEmpty() || pendapatan.isEmpty()|| tmpt_kerja.isEmpty()||sp_income.getSelectedItemPosition() < 0
                ||sp_job_type.getSelectedItemPosition() < 0 ||sp_status_kerja.getSelectedItemPosition() < 0 ){
            flag = false;
        }


        if (flag) {
            btn_simpan.setEnabled(true);
        } else {
            btn_simpan.setEnabled(false);
        }
    }

    private void saveData(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("npwp",et_npwp.getText().toString());
            jsonObject.put("income_src",sp_income.getSelectedItem().toString());
            jsonObject.put("income",et_pendapatan.getText().toString());
            jsonObject.put("work_type",sp_job_type.getSelectedItem().toString());
            jsonObject.put("work_office",et_perusahaan.getText().toString());
            jsonObject.put("work_status",sp_status_kerja.getSelectedItem().toString());

            showProgressDialog();
            userViewModel.addWorkData(Util.getData(getApplication(),"access_token"),jsonObject
            ).observe(this,userResponse -> {
                dismissProgressDialog();
                if(userResponse.getResponse().equals("200")){
                    Toast.makeText(getApplicationContext(),"Data Pekerjaan Berhasil Ditambah",Toast.LENGTH_SHORT).show();
                    ApplyDataActivity.navigate(ApplyWorkDataActivity.this);
                }else if (userResponse.getMessage().contains("Your NPWP Exist! Please try another number.")){
                    Toast.makeText(getApplicationContext(),"NPWP telah terdaftar, Mohon Coba Kembali",Toast.LENGTH_SHORT).show();
                }
                else if (userResponse.getResponse().equals("401")){
                    LoginPinActivity.navigate(ApplyWorkDataActivity.this,true);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa Kembali Data Anda",Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e){
            Log.v("error: ","Error Save Relative Data");
        }
    }
}