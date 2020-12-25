package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.ui.helper.SingleActivity;

public class DetailTransactionActivity extends SingleActivity {

    private AppCompatTextView tv_tgl_trx, tv_nama_transaksi, tv_token, tv_total_payment, tv_card_no;
    private AppCompatButton btn_home;
    private AppCompatImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);
        initView();
        setupView();

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigate(DetailTransactionActivity.this, true);
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void navigate(Activity activity, String tgl_trx, String nama_trx, String no_token,
                                String payment, String card_no) {
        Intent intent = new Intent(activity, DetailTransactionActivity.class);
        intent.putExtra("tgl_trx", tgl_trx);
        intent.putExtra("nama_trx", nama_trx);
        intent.putExtra("no_token", no_token);
        intent.putExtra("payment", payment);
        intent.putExtra("card_no", card_no);
        activity.startActivity(intent);
    }

    private void initView() {
        tv_tgl_trx = findViewById(R.id.tv_tgl_trx);
        tv_nama_transaksi = findViewById(R.id.tv_nama_transaksi);
        tv_token = findViewById(R.id.tv_token);
        tv_total_payment = findViewById(R.id.tv_total_payment);
        ic_back = findViewById(R.id.ic_back);
        btn_home = findViewById(R.id.btn_home);
        tv_card_no = findViewById(R.id.tv_card_no);
    }

    private void setupView() {
        tv_tgl_trx.setText(getIntent().getStringExtra("tgl_trx"));
        tv_nama_transaksi.setText(getIntent().getStringExtra("nama_trx"));
//        tv_token.setText(getIntent().getStringExtra("no_token"));
        tv_total_payment.setText(getIntent().getStringExtra("payment"));
        tv_card_no.setText(getIntent().getStringExtra("card_no"));

        String no_token = getIntent().getStringExtra("no_token");
        String token = "";
        for (int i = 0; i < no_token.length(); i++) {
            if (i % 4 == 0) {
                token=token+" "+no_token.charAt(i);
            } else {
                token=token+no_token.charAt(i);
            }
        }
        tv_token.setText(token);
    }
}