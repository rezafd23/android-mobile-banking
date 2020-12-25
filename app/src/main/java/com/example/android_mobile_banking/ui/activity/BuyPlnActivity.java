package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android_mobile_banking.Application;
import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.model.Token;
import com.example.android_mobile_banking.ui.adapter.TokenAdapter;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.viewmodel.AuthViewModel;
import com.example.android_mobile_banking.viewmodel.TokenViewModel;
import com.xw.repo.XEditText;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BuyPlnActivity extends SingleActivity {

    private XEditText et_nomor_pln;
    private AppCompatTextView tv_customer, tv_meter, tv_tarif, tv_payment, tv_admin_fee, tv_harga;
    private RecyclerView voucerRecyclerView;
    private TokenViewModel tokenViewModel;
    private AuthViewModel authViewModel;
    private TokenAdapter tokenAdapter;
    private ArrayList<Token> arrTemp = new ArrayList<>();
    private RelativeLayout layout_rincian, layoutPulsa;
    private LinearLayoutCompat btn_pay,btn_payment;
    private View viewfocus;
    private AppCompatImageView ic_close,ic_back;
    private List<Token> tokenList;
    private AppCompatButton btn_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_pln);
        initView();
        initData();
//        checkUser();

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser(et_nomor_pln.getText().toString());
            }
        });
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, BuyPlnActivity.class);
        activity.startActivity(intent);
    }

    private void initView() {
        et_nomor_pln = findViewById(R.id.et_nomor_pln);
        tv_customer = findViewById(R.id.tv_customer);
        tv_meter = findViewById(R.id.tv_meter);
        tv_tarif = findViewById(R.id.tv_tarif);
        tv_admin_fee = findViewById(R.id.tv_admin_fee);
        tv_harga = findViewById(R.id.tv_harga);
        voucerRecyclerView = findViewById(R.id.voucerRecyclerView);

        layout_rincian = findViewById(R.id.layout_rincian);
        viewfocus = findViewById(R.id.viewfocus);
        ic_close = findViewById(R.id.ic_close);
        tv_payment = findViewById(R.id.tv_payment);
        layoutPulsa = findViewById(R.id.layoutPulsa);
        btn_pay = findViewById(R.id.btn_pay);
        btn_payment = findViewById(R.id.btn_payment);
        btn_check = findViewById(R.id.btn_check);
        ic_back = findViewById(R.id.ic_back);

        tokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        ic_back.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initData() {
        if (tokenAdapter == null) {
            tokenAdapter = new TokenAdapter(BuyPlnActivity.this,BuyPlnActivity.this, arrTemp, layout_rincian,
                    viewfocus, ic_close, tv_payment, layoutPulsa, btn_payment, tokenViewModel,
                    tv_harga, tv_admin_fee, getApplication(),et_nomor_pln,authViewModel);
            voucerRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            voucerRecyclerView.setAdapter(tokenAdapter);
            voucerRecyclerView.setHasFixedSize(true);
            voucerRecyclerView.setItemAnimator(new DefaultItemAnimator());
            voucerRecyclerView.setNestedScrollingEnabled(true);
        } else {
            tokenAdapter.notifyDataSetChanged();
        }
        tokenViewModel.init();
        tokenViewModel.getVoucerList().observe(this, tokenResponse -> {
            tokenList = tokenResponse.getPayload();
            arrTemp.clear();
            arrTemp.addAll(tokenList);
            tokenAdapter.notifyDataSetChanged();
        });
    }

    private void checkUser(String no_pelanggan) {
        showProgressDialog();
        if (no_pelanggan.equals("")) {
            dismissProgressDialog();
            Util.collapse(layout_rincian, 2);
            viewfocus.setVisibility(View.GONE);
            layoutPulsa.animate().alpha(1f);
            Toast.makeText(getApplicationContext(), "Mohon Isi Nomor Pelanggan", Toast.LENGTH_SHORT).show();
        } else {
            tokenViewModel.checkUser(no_pelanggan).observe(this, tokenResponse -> {
                dismissProgressDialog();
                if (tokenResponse.getResponse().equals("200")) {
                    try {
                        Util.collapse(layout_rincian, 2);
                        viewfocus.setVisibility(View.GONE);
                        layoutPulsa.animate().alpha(1f);
                        layoutPulsa.setVisibility(View.VISIBLE);
                        tv_customer.setText(tokenResponse.getPayloadUser().getString("nama"));
                        tv_meter.setText(tokenResponse.getPayloadUser().getString("no_pelanggan"));
                        tv_tarif.setText("Rp." + tokenResponse.getPayloadUser().getDouble("tarif") + " / " +
                                tokenResponse.getPayloadUser().getString("daya"));
                    } catch (Exception e) {
                        Log.v("Error: ", "Check user");
                        e.printStackTrace();
                    }
                } else {
                    layoutPulsa.setVisibility(View.INVISIBLE);
                    Util.collapse(layout_rincian, 2);
                    viewfocus.setVisibility(View.GONE);
                    layoutPulsa.animate().alpha(1f);
                    Toast.makeText(getApplicationContext(), "Nomor Tidak ditemukan. Mohon Coba Kembali", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}