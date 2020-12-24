package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.model.Mutasi;
import com.example.android_mobile_banking.model.Token;
import com.example.android_mobile_banking.ui.adapter.MutasiAdapter;
import com.example.android_mobile_banking.ui.adapter.TokenAdapter;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.viewmodel.UserViewModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xw.repo.XEditText;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MutasiRekeningActivity extends SingleActivity {

    private AppCompatEditText et_start_date, et_end_date;
    private AppCompatButton btn_cek_mutasi, btn_end_date, btn_start_date;
    private RecyclerView recyler_mutasi;
    private UserViewModel userViewModel;
    private MutasiAdapter mutasiAdapter;
    private ArrayList<Mutasi> arrayList = new ArrayList<>();
    private List<Mutasi> mutasiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutasi_rekening);

        initView();

        btn_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_start_date(et_start_date);
            }
        });

        btn_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_start_date(et_end_date);
            }
        });

        btn_cek_mutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_start_date.getText().toString().equals("") || et_end_date.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Mohon Pilih Tanggal Mutasi", Toast.LENGTH_SHORT).show();
                } else {
                    getMutasi(et_start_date.getText().toString(),et_end_date.getText().toString());
                }

            }
        });


    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, MutasiRekeningActivity.class);
        activity.startActivity(intent);
    }

    private void initView() {
        et_start_date = findViewById(R.id.et_start_date);
        et_end_date = findViewById(R.id.et_end_date);
        btn_cek_mutasi = findViewById(R.id.btn_cek_mutasi);
        recyler_mutasi = findViewById(R.id.recyler_mutasi);
        btn_start_date = findViewById(R.id.btn_start_date);
        btn_end_date = findViewById(R.id.btn_end_date);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void set_start_date(AppCompatEditText txt_date) {
        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(MutasiRekeningActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        txt_date.setText(dateFormatter.format(newDate.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void getMutasi(String start_date, String end_date) {
        showProgressDialog();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(Util.getData(getApplication(), "data_nasabah"));

            if (mutasiAdapter == null) {
                mutasiAdapter = new MutasiAdapter(MutasiRekeningActivity.this, arrayList);
                recyler_mutasi.setLayoutManager(new LinearLayoutManager(this));
                recyler_mutasi.setAdapter(mutasiAdapter);
                recyler_mutasi.setHasFixedSize(true);
                recyler_mutasi.setItemAnimator(new DefaultItemAnimator());
                recyler_mutasi.setNestedScrollingEnabled(true);
            } else {
                mutasiAdapter.notifyDataSetChanged();
            }
            userViewModel.init();
            userViewModel.getMutasi(jsonObject.getAsJsonObject("banking").get("no_rekening").getAsString()
                    , start_date, end_date).observe(this, userResponse -> {
                dismissProgressDialog();
                if (userResponse.getResponse().equals("200")) {
                    if (userResponse.getMessage().equals("Tidak Ada Transaksi")) {
                        Toast.makeText(getApplicationContext(), "Tidak Terdapat Transaksi Pada Tanggal Tersebut", Toast.LENGTH_SHORT);
                    } else {
                        mutasiList = userResponse.getMutasiList();
                        arrayList.clear();
                        arrayList.addAll(mutasiList);
                        mutasiAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), userResponse.getMessage(), Toast.LENGTH_SHORT);
                }
            });

        } catch (Exception e) {
            dismissProgressDialog();
            Log.v("Error: ", "Error GET MUTASI");
            e.printStackTrace();
        }
    }
}