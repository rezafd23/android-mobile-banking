package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.view.View;
import android.widget.RelativeLayout;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.model.Token;
import com.example.android_mobile_banking.ui.adapter.TokenAdapter;
import com.example.android_mobile_banking.viewmodel.TokenViewModel;
import com.xw.repo.XEditText;

import java.util.ArrayList;
import java.util.List;

public class BuyPlnActivity extends AppCompatActivity {

    private XEditText et_nomor_pln;
    private AppCompatTextView tv_customer,tv_meter,tv_tarif,tv_payment;
    private RecyclerView voucerRecyclerView;
    private TokenViewModel tokenViewModel;
    private TokenAdapter tokenAdapter;
    private ArrayList<Token> arrTemp =new ArrayList<>();
    private RelativeLayout layout_rincian,layoutPulsa;
    private LinearLayoutCompat btn_pay;
    private View viewfocus;
    private AppCompatImageView ic_close;
    private List<Token> tokenList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_pln);
        initView();
        initData();
        checkUser();
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, BuyPlnActivity.class);
        activity.startActivity(intent);
    }

    private void initView(){
        et_nomor_pln=findViewById(R.id.et_nomor_pln);
        tv_customer=findViewById(R.id.tv_customer);
        tv_meter=findViewById(R.id.tv_meter);
        tv_tarif=findViewById(R.id.tv_tarif);
        voucerRecyclerView=findViewById(R.id.voucerRecyclerView);

        layout_rincian=findViewById(R.id.layout_rincian);
        viewfocus=findViewById(R.id.viewfocus);
        ic_close=findViewById(R.id.ic_close);
        tv_payment=findViewById(R.id.tv_payment);
        layoutPulsa=findViewById(R.id.layoutPulsa);
        btn_pay=findViewById(R.id.btn_pay);

        tokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
    }

    private void initData(){
        if (tokenAdapter==null){
            tokenAdapter=new TokenAdapter(BuyPlnActivity.this,arrTemp,layout_rincian,
                    viewfocus,ic_close,tv_payment,layoutPulsa,btn_pay,tokenViewModel);
            voucerRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
            voucerRecyclerView.setAdapter(tokenAdapter);
            voucerRecyclerView.setHasFixedSize(true);
            voucerRecyclerView.setItemAnimator(new DefaultItemAnimator());
            voucerRecyclerView.setNestedScrollingEnabled(true);
        } else {
            tokenAdapter.notifyDataSetChanged();
        }
        tokenViewModel.init();
        tokenViewModel.getVoucerList().observe(this,tokenResponse -> {
            tokenList=tokenResponse.getPayload();
            arrTemp.clear();
            arrTemp.addAll(tokenList);
            tokenAdapter.notifyDataSetChanged();
        });
    }

    private void checkUser(){

    }
}