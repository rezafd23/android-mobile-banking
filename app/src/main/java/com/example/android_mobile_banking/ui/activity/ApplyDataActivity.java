package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.constant.ApiService;
import com.example.android_mobile_banking.listener.DialogClickListener;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.util.WidgetUtil;
import com.example.android_mobile_banking.viewmodel.UserViewModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;

public class ApplyDataActivity extends SingleActivity {

    private static final int INTENT_REQ_CAMERA = 101;
    private static final int INTENT_REQ_PERSONAL = 102;
    private static final int INTENT_REQ_RELEVAN = 103;
    private static final int INTENT_REQ_WORK = 104;
    private static final int INTENT_REQ_FINISH = 105;

    private UserViewModel userViewModel;

    private AppCompatTextView tv_progress, tv_personal, tv_relevan, tv_work,tv_cam_desc,tv_cam_title;
    private ContentLoadingProgressBar progressBar;
    private AppCompatImageView iv_arrow_personal, iv_arrow_relevan, iv_arrow_work,iv_arrow_cam,iv_camera,iv_foto;
    private RelativeLayout btn_relevan_info, btn_work_info;
    private LinearLayoutCompat btn_capture_ktp, btn_capture_face,
            personal_detail, relative_detail, work_detail;
    private AppCompatButton btn_send,btn_save_local,btn_edit_cam;

    private AppCompatCheckBox checkbox;

    private String personal_data = "-";
    private String relative_data = "-";
    private String work_data = "-";

    //    layout Personal
    private AppCompatTextView tv_ktp, tv_email, tv_pendidikan_terakhir, tv_status_perkawinan,
            tv_alamat_rumah, tv_provinsi, tv_kabupaten, tv_kecamatan, tv_kelurahan, tv_rtrw, tv_status_rumah,
            tv_nama_ibu, tv_nama_kerabat, tv_hubungan_kerabat, tv_no_telp_kerabat, tv_alamat_kerabat, tv_nomor_npwp, tv_sumber_penghasilan,
            tv_pendapatan_perbulan, tv_jenis_pekerjaan, tv_nama_perusahaan, tv_status_pekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_data);

        initView();
        getDataUser(Util.getData(getApplication(), "access_token"));
        doSend();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onSaveAndStopClicked(btn_save_local);
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, ApplyDataActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        tv_progress = findViewById(R.id.tv_progress);
        iv_arrow_personal = findViewById(R.id.iv_arrow_personal);
        iv_arrow_relevan = findViewById(R.id.iv_arrow_relevan);
        iv_arrow_work = findViewById(R.id.iv_arrow_work);
        iv_arrow_cam = findViewById(R.id.iv_arrow_cam);
        iv_camera = findViewById(R.id.iv_camera);
        tv_cam_desc = findViewById(R.id.tv_cam_desc);
        tv_cam_title = findViewById(R.id.tv_cam_title);
        iv_foto = findViewById(R.id.iv_foto);
        checkbox = findViewById(R.id.checkbox);

//        layout button
        tv_personal = findViewById(R.id.tv_personal);
        tv_relevan = findViewById(R.id.tv_relevan);
        tv_work = findViewById(R.id.tv_work);
        btn_relevan_info = findViewById(R.id.btn_relevan_info);
        btn_work_info = findViewById(R.id.btn_work_info);
        btn_capture_ktp = findViewById(R.id.btn_capture_ktp);
        btn_capture_face = findViewById(R.id.btn_capture_face);
        btn_save_local = findViewById(R.id.btn_save_local);
        btn_edit_cam = findViewById(R.id.btn_edit_cam);

//        layout Personal data
        personal_detail = findViewById(R.id.personal_detail);
        tv_ktp = findViewById(R.id.tv_ktp);
        tv_email = findViewById(R.id.tv_email);
        tv_pendidikan_terakhir = findViewById(R.id.tv_pendidikan_terakhir);
        tv_status_perkawinan = findViewById(R.id.tv_status_perkawinan);
        tv_alamat_rumah = findViewById(R.id.tv_alamat_rumah);
        tv_provinsi = findViewById(R.id.tv_provinsi);
        tv_kabupaten = findViewById(R.id.tv_kabupaten);
        tv_kecamatan = findViewById(R.id.tv_kecamatan);
        tv_kelurahan = findViewById(R.id.tv_kelurahan);
        tv_rtrw = findViewById(R.id.tv_rtrw);
        tv_status_rumah = findViewById(R.id.tv_status_rumah);
        btn_send = findViewById(R.id.btn_send);

//        layout relative data
        relative_detail = findViewById(R.id.relative_detail);
        tv_nama_ibu = findViewById(R.id.tv_nama_ibu);
        tv_nama_kerabat = findViewById(R.id.tv_nama_kerabat);
        tv_hubungan_kerabat = findViewById(R.id.tv_hubungan_kerabat);
        tv_no_telp_kerabat = findViewById(R.id.tv_no_telp_kerabat);
        tv_alamat_kerabat = findViewById(R.id.tv_alamat_kerabat);

//        layout work data
        work_detail = findViewById(R.id.work_detail);
        tv_nomor_npwp = findViewById(R.id.tv_nomor_npwp);
        tv_sumber_penghasilan = findViewById(R.id.tv_sumber_penghasilan);
        tv_pendapatan_perbulan = findViewById(R.id.tv_pendapatan_perbulan);
        tv_jenis_pekerjaan = findViewById(R.id.tv_jenis_pekerjaan);
        tv_nama_perusahaan = findViewById(R.id.tv_nama_perusahaan);
        tv_status_pekerjaan = findViewById(R.id.tv_status_pekerjaan);


        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        btn_relevan_info.setEnabled(false);
        btn_work_info.setEnabled(false);
        btn_capture_ktp.setEnabled(false);
//        btn_capture_face.setEnabled(false);

        addPersonalData();
        addRelativeData();
        addWorkData();
        gotoCamera(btn_capture_ktp);
        gotoCamera(btn_edit_cam);

        sendProcess();

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
                    } catch (Exception e) {
                        Log.v("isiError: ", e.getMessage());
                    }

                });
    }

    private void addPersonalData() {
        iv_arrow_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyPersonalDataActivity.navigate(ApplyDataActivity.this, personal_data);
            }
        });
    }

    private void addRelativeData() {
        iv_arrow_relevan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyRelevanDataActivity.navigate(ApplyDataActivity.this, relative_data);
            }
        });
    }

    private void addWorkData() {
        iv_arrow_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyWorkDataActivity.navigate(ApplyDataActivity.this, work_data);
            }
        });
    }

    private void setupPage(JSONObject jsonObject) {
        try {
            String personal_status = jsonObject.getJSONObject("regis_data_status").getString("personal");
            String relative_status = jsonObject.getJSONObject("regis_data_status").getString("relative");
            String work_info = jsonObject.getJSONObject("regis_data_status").getString("work");
            String capture_ktp = jsonObject.getJSONObject("regis_data_status").getString("ektp");

            if (personal_status.equals("Y")) {
                JSONObject responData = jsonObject.getJSONObject("personal");

                personal_data = responData.toString();
                progressBar.setProgress(20);
                tv_progress.setText("20% Compleated");
                personal_detail.setVisibility(View.VISIBLE);

                tv_ktp.setText(responData.getString("no_ktp"));
                Util.setData(getApplication(),"no_ktp",responData.getString("no_ktp"));
                tv_email.setText(responData.getString("email"));
                tv_pendidikan_terakhir.setText(responData.getString("education"));
                tv_status_perkawinan.setText(responData.getString("marital"));
                tv_alamat_rumah.setText(responData.getString("address"));
                tv_provinsi.setText(responData.getString("province"));
                tv_kabupaten.setText(responData.getString("city"));
                tv_kecamatan.setText(responData.getString("district"));
                tv_kelurahan.setText(responData.getString("sub_district"));
                tv_rtrw.setText(responData.getString("rt") + "/" + responData.getString("rw"));
                tv_status_rumah.setText(responData.getString("living_status"));

                tv_personal.setTextColor(getColor(R.color.dark_blue));
                tv_relevan.setTextColor(getColor(R.color.black));
                btn_relevan_info.setEnabled(true);
            }
            if (relative_status.equals("Y")) {
                tv_relevan.setTextColor(getColor(R.color.dark_blue));
                tv_work.setTextColor(getColor(R.color.black));
                btn_work_info.setEnabled(true);
                relative_detail.setVisibility(View.VISIBLE);

                JSONObject responData = jsonObject.getJSONObject("relative");
                Log.v("isiRelative: ", responData.getString("mother_name"));
                tv_nama_ibu.setText(responData.getString("mother_name"));
                tv_nama_kerabat.setText(responData.getString("relevan_name"));
                tv_hubungan_kerabat.setText(responData.getString("relationship"));
                tv_no_telp_kerabat.setText(responData.getString("no_hp_relevan"));
                tv_alamat_kerabat.setText(responData.getString("relevan_address"));

                relative_data = responData.toString();
                progressBar.setProgress(40);
                tv_progress.setText("40% Compleated");
                personal_detail.setVisibility(View.VISIBLE);
            }
            if (work_info.equals("Y")) {
                tv_work.setTextColor(getColor(R.color.dark_blue));
                btn_capture_ktp.setEnabled(true);

                JSONObject responData = jsonObject.getJSONObject("work");
                work_data = responData.toString();
                progressBar.setProgress(80);
                tv_progress.setText("80% Compleated");
                work_detail.setVisibility(View.VISIBLE);

                tv_nomor_npwp.setText(responData.getString("npwp"));
                tv_sumber_penghasilan.setText(responData.getString("income_src"));
                tv_pendapatan_perbulan.setText(responData.getString("income"));
                tv_jenis_pekerjaan.setText(responData.getString("work_type"));
                if (responData.getString("work_office").length() > 0) {
                    tv_nama_perusahaan.setText(responData.getString("work_office"));
                } else {
                    tv_nama_perusahaan.setText("-");
                }
                tv_status_pekerjaan.setText(responData.getString("work_status"));

            }
            if (capture_ktp.equals("Y")) {
                btn_capture_face.setEnabled(true);

                progressBar.setProgress(100);
                tv_progress.setText("100% Compleated");
                iv_camera.setImageResource(R.drawable.ic_complete);
                iv_camera.setVisibility(View.VISIBLE);
                tv_cam_desc.setVisibility(View.GONE);
                iv_arrow_cam.setVisibility(View.GONE);
                iv_foto.setVisibility(View.VISIBLE);
                    btn_edit_cam.setVisibility(View.VISIBLE);
                tv_cam_title.setText(getString(R.string.foto_upload_finish));

                GlideUrl glideUrl = new GlideUrl(ApiService.BASE_URL_2+jsonObject.getJSONObject("ektp_file")
                        .getString("path"), new LazyHeaders.Builder()
                        .build());

                Glide.with(this)
                        .load(glideUrl)
                        .into(iv_foto);

                checkbox.setEnabled(true);
            }
//            if (capture_face.equals("Y")) {
//                btn_send.setEnabled(true);
//                progressBar.setProgress(100);
//                tv_progress.setText("100% Compleated");
//            }
        } catch (Exception e) {
            Log.v("Error: ", "Error Set View Status");
            e.printStackTrace();
        }
    }

    private void doSend(){
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    btn_send.setEnabled(true);
                }else {
                    btn_send.setEnabled(false);
                }
            }
        });
    }

    private void sendProcess(){
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finishRegister();
            }
        });
    }

    private void finishRegister(){
        showProgressDialog();
        userViewModel.finishRegister(Util.getData(getApplication(),"access_token"),
                Util.getData(getApplication(),"no_ktp"))
                .observe(this, userResponse -> {
                    dismissProgressDialog();
                    Log.v("isiResponse: ", userResponse.getResponse());
                    try {
                        if (userResponse.getResponse().equals("200")) {
                            MainActivity.navigate(ApplyDataActivity.this,true);
                        } else {
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan, Mohon Ulangi", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.v("isiError: ", e.getMessage());
                    }

                });
    }

    public void onSaveAndStopClicked(View view) {
        WidgetUtil.showDoubleButtonDialog(this, "Tunggu Dulu!", "Kamu akan meninggalkan pendaftaranmu, Apakah Yakin?.",
                "Tidak, Kembali", new DialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {
                        dialog.dismiss();
                    }
                },
                "Ya, Batalkan", new DialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view) {
                        MainActivity.navigate(ApplyDataActivity.this,true);
                    }
                }, true);

    }

    private void gotoCamera(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraKtpActivity.navigate(ApplyDataActivity.this,INTENT_REQ_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case INTENT_REQ_CAMERA:
                    String filePath = data.getStringExtra("foto_file");
                    onUploadingFoto(filePath);
                    break;
            }
        }
    }

    private void onUploadingFoto(final String filePath) {
        try {
            File ektpFile = new File(filePath);
            showProgressDialog();
            userViewModel.uploadEKTP(Util.getData(getApplication(),"access_token"),ektpFile)
                    .observe(this, userResponse -> {
                        dismissProgressDialog();
                        Log.v("isiResponse: ", userResponse.getResponse());
                        try {
                            if (userResponse.getResponse().equals("200")) {
                                getDataUser(Util.getData(getApplication(),"access_token"));
                            } else {
                                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan, Mohon Ulangi", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.v("isiError: ", e.getMessage());
                        }

                    });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERRORUPLOADFOTO", e.getMessage());
        }
    }

}