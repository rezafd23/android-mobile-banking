package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
//import com.github.rtoshiro.util.format.text.SimpleMaskTextWatcher;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.constant.AppConstant;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.util.WidgetUtil;
import com.example.android_mobile_banking.viewmodel.UserViewModel;
import com.example.android_mobile_banking.widget.CustomSpinner;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.SimpleMaskTextWatcher;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xw.repo.XEditText;

import org.json.JSONObject;

public class ApplyPersonalDataActivity extends SingleActivity {

    private TextInputLayout inputNoKTP, inputEmail,input_alamat,input_rt_rw,input_nama;
    private NestedScrollView nested_scroll_view;
    private XEditText et_ektp, et_email,et_alamat,et_rt_rw,et_nama;
    private AppCompatButton btn_simpan;
    private AppCompatImageView ic_back;
    private CustomSpinner sp_pendidikan, sp_status_perkawinan,
            sp_kecamatan, sp_kelurahan, sp_kabupaten, sp_provinsi,sp_status_rumah;

    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_personal_data);


        initView();
        setupView();

        btn_simpan.setOnClickListener(view -> {
            savePersonalData();
        });
    }

    private void initView() {
        input_nama = findViewById(R.id.input_nama);
        inputNoKTP = findViewById(R.id.input_ektp);
        inputEmail = findViewById(R.id.input_email);
        input_alamat = findViewById(R.id.input_alamat);
        input_rt_rw = findViewById(R.id.input_rt_rw);
        et_ektp = findViewById(R.id.et_ektp);
        et_email = findViewById(R.id.et_email);
        et_rt_rw = findViewById(R.id.et_rt_rw);
        et_alamat = findViewById(R.id.et_alamat);
        et_nama = findViewById(R.id.et_nama);
        btn_simpan = findViewById(R.id.btn_simpan);
        sp_pendidikan = findViewById(R.id.sp_pendidikan);
        sp_status_perkawinan = findViewById(R.id.sp_status_perkawinan);
        sp_kecamatan = findViewById(R.id.sp_kecamatan);
        sp_kelurahan = findViewById(R.id.sp_kelurahan);
        sp_kabupaten = findViewById(R.id.sp_kabupaten);
        sp_provinsi = findViewById(R.id.sp_provinsi);
        sp_status_rumah = findViewById(R.id.sp_status_rumah);
        nested_scroll_view = findViewById(R.id.nested_scroll_view);
        ic_back = findViewById(R.id.ic_back);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupView() {

        setTextChange(et_ektp, inputNoKTP);
        setTextChange(et_email, inputEmail);
        setTextChange(et_alamat, input_alamat);
        setTextChange(et_rt_rw, input_rt_rw);
        setTextChange(et_nama, input_nama);

        SimpleMaskTextWatcher textWatcher = new SimpleMaskTextWatcher(et_rt_rw, "NNN/NNN");
        et_rt_rw.addTextChangedListener(textWatcher);

        sp_pendidikan.setArrowColor(getColor(R.color.dark_blue));
        sp_status_perkawinan.setArrowColor(getColor(R.color.dark_blue));
        sp_provinsi.setArrowColor(getColor(R.color.dark_blue));
        sp_kabupaten.setArrowColor(getColor(R.color.dark_blue));
        sp_kecamatan.setArrowColor(getColor(R.color.dark_blue));
        sp_kelurahan.setArrowColor(getColor(R.color.dark_blue));
        sp_status_rumah.setArrowColor(getColor(R.color.dark_blue));

        sp_pendidikan.setHintColor(getColor(R.color.black));
        sp_status_perkawinan.setHintColor(getColor(R.color.black));
        sp_provinsi.setHintColor(getColor(R.color.black));
        sp_kabupaten.setHintColor(getColor(R.color.black));
        sp_kecamatan.setHintColor(getColor(R.color.black));
        sp_kelurahan.setHintColor(getColor(R.color.black));
        sp_status_rumah.setHintColor(getColor(R.color.black));

        sp_pendidikan.setBaseColor(getColor(R.color.black));
        sp_status_perkawinan.setBaseColor(getColor(R.color.black));
        sp_provinsi.setBaseColor(getColor(R.color.black));
        sp_kabupaten.setBaseColor(getColor(R.color.black));
        sp_kecamatan.setBaseColor(getColor(R.color.black));
        sp_kelurahan.setBaseColor(getColor(R.color.black));
        sp_status_rumah.setBaseColor(getColor(R.color.black));

        Typeface rubik_regular = ResourcesCompat.getFont(this, R.font.rubik_regular);
        sp_pendidikan.setTypeface(rubik_regular);
        sp_status_perkawinan.setTypeface(rubik_regular);
        sp_provinsi.setTypeface(rubik_regular);
        sp_kabupaten.setTypeface(rubik_regular);
        sp_kecamatan.setTypeface(rubik_regular);
        sp_kelurahan.setTypeface(rubik_regular);
        sp_status_rumah.setTypeface(rubik_regular);


        WidgetUtil.setupSpinner(sp_pendidikan, AppConstant.listPendidikan);
        WidgetUtil.setupSpinner(sp_status_perkawinan, AppConstant.lisKawin);

        WidgetUtil.setupSpinner(sp_provinsi, AppConstant.listProvinsi);
        WidgetUtil.setupSpinner(sp_kabupaten, AppConstant.listDKI);
        WidgetUtil.setupSpinner(sp_status_rumah, AppConstant.listStatusRumah);



//        sp_kabupaten.setOnItemSelectedListener(new );

        sp_kabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkEnabledButton();
//                Log.v("isiNilai: ", String.valueOf(i));
//                Log.v("isiNilai: ", String.valueOf(adapterView.getSelectedItem()));
                switch (i) {
                    case 0:
                        WidgetUtil.setupSpinner(sp_kecamatan, AppConstant.listJakBarKec);
                        break;
                    case 1:
                        WidgetUtil.setupSpinner(sp_kecamatan, AppConstant.listJakSelKec);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sp_kecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkEnabledButton();
//                Log.v("isiNilai: ", String.valueOf(i));
//                Log.v("isiNilai: ", String.valueOf(adapterView.getSelectedItem()));
                if (i>=0){
                    switch (adapterView.getSelectedItem().toString()){
                        case "Cilandak":
                            WidgetUtil.setupSpinner(sp_kelurahan, AppConstant.listKelCilandak);
                            break;
                        case "Mampang Prapatan":
                            WidgetUtil.setupSpinner(sp_kelurahan, AppConstant.listKelMampang);
                            break;
                        case "Taman Sari":
                            WidgetUtil.setupSpinner(sp_kelurahan, AppConstant.listKelTamsar);
                            break;
                        case "Grogol Petamburan":
                            WidgetUtil.setupSpinner(sp_kelurahan, AppConstant.listKelGrogol);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sp_pendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkEnabledButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_status_rumah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkEnabledButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_status_perkawinan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkEnabledButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setupData();
    }
    private void setupData(){
        try {
            if (!getIntent().getStringExtra("personal_data").equals("-")){
                Log.v("isiPersonalData: ",getIntent().getStringExtra("personal_data"));
                JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("personal_data"));

                et_nama.setText(jsonObject.getString("nama"));
                et_ektp.setText(jsonObject.getString("no_ktp"));
                et_email.setText(jsonObject.getString("email"));
                et_alamat.setText(jsonObject.getString("address"));
                WidgetUtil.setSpinnerSelection(sp_pendidikan,jsonObject.getString("education"),true);
                WidgetUtil.setSpinnerSelection(sp_status_perkawinan,jsonObject.getString("marital"),true);
                WidgetUtil.setSpinnerSelection(sp_provinsi,jsonObject.getString("province"),true);
                WidgetUtil.setSpinnerSelection(sp_kabupaten,jsonObject.getString("city"),true);
                WidgetUtil.setSpinnerSelection(sp_status_rumah,jsonObject.getString("living_status"),true);

                et_rt_rw.setText(jsonObject.getString("rt")+"/"+jsonObject.getString("rw"));

                String Kecamatan =  jsonObject.getString("district");
                String Kelurahan =  jsonObject.getString("sub_district");

                sp_kabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i) {
                            case 0:
                                WidgetUtil.setupSpinner(sp_kecamatan, AppConstant.listJakBarKec);
                                WidgetUtil.setSpinnerSelection(sp_kecamatan,Kecamatan,true);
                                break;
                            case 1:
                                WidgetUtil.setupSpinner(sp_kecamatan, AppConstant.listJakSelKec);
                                WidgetUtil.setSpinnerSelection(sp_kecamatan,Kecamatan,true);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                sp_kecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i>=0){
                            switch (adapterView.getSelectedItem().toString()){
                                case "Cilandak":
                                    WidgetUtil.setupSpinner(sp_kelurahan, AppConstant.listKelCilandak);
                                    WidgetUtil.setSpinnerSelection(sp_kelurahan,Kelurahan,true);
                                    break;
                                case "Mampang Prapatan":
                                    WidgetUtil.setupSpinner(sp_kelurahan, AppConstant.listKelMampang);
                                    WidgetUtil.setSpinnerSelection(sp_kelurahan,Kelurahan,true);
                                    break;
                                case "Taman Sari":
                                    WidgetUtil.setupSpinner(sp_kelurahan, AppConstant.listKelTamsar);
                                    WidgetUtil.setSpinnerSelection(sp_kelurahan,Kelurahan,true);
                                    break;
                                case "Grogol Petamburan":
                                    WidgetUtil.setupSpinner(sp_kelurahan, AppConstant.listKelGrogol);
                                    WidgetUtil.setSpinnerSelection(sp_kelurahan,Kelurahan,true);
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });


            }
        } catch (Exception e){
            Log.v("error Setup Data",e.getMessage());
        }

    }


    public static void navigate(Activity activity,String personal_data) {
        Intent intent = new Intent(activity, ApplyPersonalDataActivity.class);
        intent.putExtra("personal_data",personal_data);
        activity.startActivity(intent);
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
//                if (s.length() > 0) {
//                    inputLayout.setHint(null);
//                } else {
//                    inputLayout.setHint(hint);
//                }
                checkEnabledButton();
            }
        });
    }

    public void savePersonalData() {

           try {
               String[] parts = et_rt_rw.getText().toString().split("/");
               String rt = parts[0]; // 004
               String rw = parts[1];
               JSONObject jsonObject = new JSONObject();
               jsonObject.put("no_ktp",et_ektp.getText().toString());
               jsonObject.put("nama",et_nama.getText().toString());
               jsonObject.put("email",et_email.getText().toString());
               jsonObject.put("education",sp_pendidikan.getSelectedItem().toString());
               jsonObject.put("marital",sp_status_perkawinan.getSelectedItem().toString());
               jsonObject.put("address",et_alamat.getText().toString());
               jsonObject.put("province",sp_provinsi.getSelectedItem().toString());
               jsonObject.put("city",sp_kabupaten.getSelectedItem().toString());
               jsonObject.put("district",sp_kecamatan.getSelectedItem().toString());
               jsonObject.put("sub_district",sp_kelurahan.getSelectedItem().toString());
               jsonObject.put("rt",rt);
               jsonObject.put("rw",rw);
               jsonObject.put("living_status",sp_status_rumah.getSelectedItem().toString());

               showProgressDialog();
               userViewModel.addPersonalData(Util.getData(getApplication(),"access_token"),jsonObject
               ).observe(this,userResponse -> {
                   dismissProgressDialog();
                   if(userResponse.getResponse().equals("200")){
                       Toast.makeText(getApplicationContext(),"Data Pribadi Berhasil Ditambah",Toast.LENGTH_SHORT).show();
                       ApplyDataActivity.navigate(ApplyPersonalDataActivity.this);
                   } else if (userResponse.getResponse().equals("401")){
                       LoginPinActivity.navigate(ApplyPersonalDataActivity.this,true);
                   } else if (userResponse.getMessage().contains("Your Email and KTP Number Exist!")){
                       Log.v("cek","Masuk 1");
                       Toast.makeText(ApplyPersonalDataActivity.this,"Nomor KTP atau Email Anda telah Terdaftar",Toast.LENGTH_SHORT).show();
                   }
                   else {
                       Toast.makeText(getApplicationContext(),"Mohon Periksa Kembali Data Anda",Toast.LENGTH_SHORT).show();
                   }
               });

           } catch (Exception e){
               Log.v("error: ","Error Save Data");
           }
        }
//    }

    private void checkEnabledButton() {
        String email = et_email.getText().toString();
        String ektp = et_ektp.getText().toString();
        String nama = et_nama.getText().toString();
        String alamat = et_alamat.getText().toString();
        String rtrw = et_rt_rw.getText().toString();

        boolean flag = true;

        if (email.isEmpty() || ektp.isEmpty()||nama.isEmpty() || alamat.isEmpty()||rtrw.isEmpty()) flag = false;

        if (sp_pendidikan.getSelectedItemPosition() < 0 ||
                sp_status_perkawinan.getSelectedItemPosition() < 0 ||
                sp_provinsi.getSelectedItemPosition() < 0 ||
                sp_kabupaten.getSelectedItemPosition() < 0 ||
                sp_kecamatan.getSelectedItemPosition() < 0 ||
                sp_kelurahan.getSelectedItemPosition() < 0 ||
                sp_status_rumah.getSelectedItemPosition() < 0) {
            flag = false;
        }

        if (flag) {
            btn_simpan.setEnabled(true);
        } else {
            btn_simpan.setEnabled(false);
        }
    }

}