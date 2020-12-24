package com.example.android_mobile_banking.ui.adapter;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.model.ParamToken;
import com.example.android_mobile_banking.model.Token;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.viewmodel.TokenViewModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xw.repo.XEditText;

import org.json.JSONObject;

import java.util.ArrayList;

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.TokenViewHolder> {
    Context context;
    RelativeLayout layout_rincian;
    ArrayList<Token> list;
    View viewfocus;
    AppCompatImageView ic_close;
    private String total_payment="";

    ProgressDialog progressDialog;

    AppCompatTextView tv_payment,tv_harga,tv_admin_fee;
    RelativeLayout layoutPulsa;
    LinearLayoutCompat btn_pay;
    TokenViewModel tokenViewModel;
    Application application;
    XEditText et_no_pelanggan;


    public TokenAdapter(Context context, ArrayList<Token> list, RelativeLayout layout_rincian, View viewfocus,
                        AppCompatImageView ic_close, AppCompatTextView tv_payment, RelativeLayout layoutPulsa,
                        LinearLayoutCompat btn_pay, TokenViewModel tokenViewModel, AppCompatTextView tv_harga,
                        AppCompatTextView tv_admin_fee, Application application, XEditText et_no_pelanggan) {
        this.layout_rincian=layout_rincian;
        this.layoutPulsa=layoutPulsa;
        this.tv_payment=tv_payment;
        this.ic_close=ic_close;
        this.viewfocus=viewfocus;
        this.btn_pay=btn_pay;
        this.context = context;
        this.tokenViewModel = tokenViewModel;
        this.list = list;
        this.tv_harga = tv_harga;
        this.tv_admin_fee = tv_admin_fee;
        this.application = application;
        this.et_no_pelanggan = et_no_pelanggan;
    }

    @NonNull
    @Override
    public TokenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return new TokenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TokenViewHolder holder, int position) {
        holder.tv_nominal.setText(list.get(position).getVoucer());
//        holder.tv_nominal.setText(list.get(position).getId_voucer());

        holder.linear_nominal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.expand(layout_rincian,2);
                viewfocus.setVisibility(View.VISIBLE);
                layoutPulsa.animate().alpha(0.5f);
                tv_harga.setText(list.get(position).getVoucer());
                tv_admin_fee.setText("Rp.1500");
                total_payment = String.valueOf(list.get(position).getPrice()+1500);
                tv_payment.setText("Rp."+Util.round(total_payment));
//                tv_payment.setText("Rp."+Util.round(total+".0"));
            }
        });

        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.collapse(layout_rincian,2);
                viewfocus.setVisibility(View.GONE);
                layoutPulsa.animate().alpha(1f);
            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showProgressDialog(progressDialog,context);
                int total_pay = Integer.parseInt(total_payment);
                try {
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) parser.parse(Util.getData(application,"data_nasabah"));
                    Log.v("isiSaldo: ", String.valueOf(jsonObject.getAsJsonObject("banking").get("saldo")));
                    int saldo = Integer.parseInt(String.valueOf(jsonObject.getAsJsonObject("banking").get("saldo")));
                    if (saldo<total_pay){
                        Util.dismissProgressDialog(progressDialog);
                        Toast.makeText(context,"Saldo Anda tidak Cukup Untuk menyelesaikan Transaksi ini",Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("nominal",total_pay);
                        jsonObject1.put("id_nasabah_card",Integer.parseInt(String.valueOf(jsonObject.getAsJsonObject("banking").get("id_nasabah_card"))));
                        jsonObject1.put("nama_transaksi","Pembelian Token "+tv_harga.getText().toString());
                        jsonObject1.put("no_pelanggan",et_no_pelanggan.getText().toString());
                        jsonObject1.put("id_voucer",list.get(position).getId_voucer());
                        tokenViewModel.buyToken(jsonObject1).
                                observe((LifecycleOwner) context, tokenResponse -> {
                                    Util.dismissProgressDialog(progressDialog);
                            if (tokenResponse.getResponse().equals("200")) {
                                try {
//                                    Util.dismissProgressDialog(progressDialog);
                                    Log.v("isiResponseBuyToken: ",tokenResponse.getPayloadUser().toString());
                                } catch (Exception e) {
//                                    Util.dismissProgressDialog(progressDialog);
                                    Log.v("Error: ", "Check user");
                                    e.printStackTrace();
                                }
                            } else {
//                                Util.dismissProgressDialog(progressDialog);
                            }
                        });

//                        Toast.makeText(context,"Saldo Anda Cukup Untuk menyelesaikan Transaksi ini",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TokenViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView tv_nominal,tv_nominal2;
        LinearLayoutCompat linear_nominal;

        public TokenViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nominal=itemView.findViewById(R.id.tv_nominal);
            tv_nominal2=itemView.findViewById(R.id.tv_nominal2);
            linear_nominal=itemView.findViewById(R.id.linear_nominal);

        }

    }
}
