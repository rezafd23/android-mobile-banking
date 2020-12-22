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

public class ApplyRelevanDataActivity extends SingleActivity {

    private AppCompatImageView ic_back;
    private XEditText et_ibu_kandung,et_kerabat,et_telp_kerabat,et_alamat_kerabat;
    private CustomTextInputLayout input_ibu,input_nama_kerabat,input_telp,input_alamat;
    private CustomSpinner sp_hubungan_kerabat;
    private AppCompatButton btn_simpan;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_relevan_data);

        initView();

        setupView();

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public static void navigate(Activity activity, String personal_data) {
        Intent intent = new Intent(activity, ApplyRelevanDataActivity.class);
        intent.putExtra("relative_data",personal_data);
        activity.startActivity(intent);
    }

    private void initView(){
        ic_back=findViewById(R.id.ic_back);
        sp_hubungan_kerabat=findViewById(R.id.sp_hubungan_kerabat);

        input_ibu=findViewById(R.id.input_ibu);
        input_nama_kerabat=findViewById(R.id.input_nama_kerabat);
        input_telp=findViewById(R.id.input_telp);
        input_alamat=findViewById(R.id.input_alamat);

        et_ibu_kandung=findViewById(R.id.et_ibu_kandung);
        et_kerabat=findViewById(R.id.et_kerabat);
        et_telp_kerabat=findViewById(R.id.et_telp_kerabat);
        et_alamat_kerabat=findViewById(R.id.et_alamat_kerabat);

        btn_simpan=findViewById(R.id.btn_simpan);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void setupView(){
        try {
            setTextChange(et_ibu_kandung,input_ibu);
            setTextChange(et_kerabat,input_nama_kerabat);
            setTextChange(et_telp_kerabat,input_telp);
            setTextChange(et_alamat_kerabat,input_alamat);

            sp_hubungan_kerabat.setArrowColor(getColor(R.color.dark_blue));

            sp_hubungan_kerabat.setHintColor(getColor(R.color.black));

            sp_hubungan_kerabat.setBaseColor(getColor(R.color.black));

            Typeface rubik_regular = ResourcesCompat.getFont(this, R.font.rubik_regular);
            sp_hubungan_kerabat.setTypeface(rubik_regular);

            WidgetUtil.setupSpinner(sp_hubungan_kerabat, AppConstant.listKerabat);

            if (!getIntent().getStringExtra("relative_data").equals("-")) {
                JSONObject relativeData = new JSONObject(getIntent().getStringExtra("relative_data"));
                et_ibu_kandung.setText(relativeData.getString("mother_name"));
                et_kerabat.setText(relativeData.getString("relevan_name"));
                et_telp_kerabat.setText(relativeData.getString("no_hp_relevan"));
                et_alamat_kerabat.setText(relativeData.getString("relevan_address"));

                WidgetUtil.setSpinnerSelection(sp_hubungan_kerabat,relativeData.getString("relationship"),true);
            }
        }catch (Exception e){
            Log.v("error"," Setup View: "+e.getMessage());
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
        String ibu_kandung = et_ibu_kandung.getText().toString();
        String kerabat = et_kerabat.getText().toString();
        String alamat = et_alamat_kerabat.getText().toString();
        String telpon = et_telp_kerabat.getText().toString();
        boolean flag = true;

        if (ibu_kandung.isEmpty() || kerabat.isEmpty()||alamat.isEmpty() || telpon.isEmpty()) flag = false;

        if (sp_hubungan_kerabat.getSelectedItemPosition() < 0 ) {
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
            jsonObject.put("mother_name",et_ibu_kandung.getText().toString());
            jsonObject.put("relevan_name",et_kerabat.getText().toString());
            jsonObject.put("relationship",sp_hubungan_kerabat.getSelectedItem().toString());
            jsonObject.put("no_hp_relevan",et_telp_kerabat.getText().toString());
            jsonObject.put("relevan_address",et_alamat_kerabat.getText().toString());

            showProgressDialog();
            userViewModel.addRelativeData(Util.getData(getApplication(),"access_token"),jsonObject
            ).observe(this,userResponse -> {
                dismissProgressDialog();
                if(userResponse.getResponse().equals("200")){
                    Toast.makeText(getApplicationContext(),"Data Kerabat Berhasil Ditambah",Toast.LENGTH_SHORT);
                    ApplyDataActivity.navigate(ApplyRelevanDataActivity.this);
                } else if (userResponse.getResponse().equals("401")){
                    LoginPinActivity.navigate(ApplyRelevanDataActivity.this,true);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Mohon Periksa Kembali Data Anda",Toast.LENGTH_SHORT);
                }
            });

        } catch (Exception e){
            Log.v("error: ","Error Save Relative Data");
        }
    }
}

