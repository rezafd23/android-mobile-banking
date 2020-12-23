package com.example.android_mobile_banking.ui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.model.Token;
import com.example.android_mobile_banking.viewmodel.TokenViewModel;

import java.util.ArrayList;

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.TokenViewHolder> {
    Context context;
    RelativeLayout layout_rincian;
    ArrayList<Token> list;
    View viewfocus;
    AppCompatImageView ic_close;

    ProgressDialog progressDialog;

    AppCompatTextView tv_payment,tv_pulsa;
    RelativeLayout layoutPulsa;
    LinearLayoutCompat btn_pay;
    TokenViewModel tokenViewModel;

    public TokenAdapter(Context context, ArrayList<Token> list, RelativeLayout layout_rincian,View viewfocus,
                        AppCompatImageView ic_close,AppCompatTextView tv_payment,RelativeLayout layoutPulsa,
                        LinearLayoutCompat btn_pay, TokenViewModel tokenViewModel) {
        this.layout_rincian=layout_rincian;
        this.layoutPulsa=layoutPulsa;
        this.tv_payment=tv_payment;
        this.ic_close=ic_close;
        this.viewfocus=viewfocus;
        this.btn_pay=btn_pay;
        this.context = context;
        this.tokenViewModel = tokenViewModel;
        this.list = list;
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
