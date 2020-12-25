package com.example.android_mobile_banking.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.model.Mutasi;
import com.example.android_mobile_banking.ui.activity.DetailTransactionActivity;
import com.example.android_mobile_banking.util.Util;

import java.util.ArrayList;

public class MutasiAdapter extends RecyclerView.Adapter<MutasiAdapter.MutasiViewHolder> {

    private Context context;
    private ArrayList<Mutasi> listMutasi;
    private Activity activity;

    public MutasiAdapter(Activity activity,Context context, ArrayList<Mutasi> listMutasi) {
        this.activity = activity;
        this.context = context;
        this.listMutasi = listMutasi;
    }

    @NonNull
    @Override
    public MutasiAdapter.MutasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mutasi, parent, false);
        return new MutasiAdapter.MutasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MutasiAdapter.MutasiViewHolder holder, int position) {

//        Log.v("isiNAMATRANSAKSI: ",listMutasi.get(position).getNama_transaksi());
        String[] dateParts = listMutasi.get(position).getTgl_transaksi().split(" ");
        String dayMonthYear = dateParts[0];
        String[] dateMonth = dayMonthYear.split("-");

        String nominal = "Rp." + Util.round(String.valueOf(listMutasi.get(position).getNominal()));
        holder.tv_nama_transaksi.setText(listMutasi.get(position).getNama_transaksi());
        if (listMutasi.get(position).getStatus_transaksi().equals("kredit")) {
            holder.tv_nilai_transaksi.setText("+" + nominal);
            holder.tv_nilai_transaksi.setTextColor(context.getColor(R.color.green));
        } else {
            holder.tv_nilai_transaksi.setText("-" + nominal);
            holder.tv_nilai_transaksi.setTextColor(context.getColor(R.color.colorTextError));
        }
        holder.tv_tgl_transaksi.setText(dateMonth[2] + "/" + dateMonth[1]);

        if (listMutasi.get(position).getNama_transaksi().contains("Pembelian Token")){
            holder.layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    public static void navigate(Activity activity,String tgl_trx,String nama_trx,String no_token,
//                            String payment,String card_no){
                    DetailTransactionActivity.navigate(activity,listMutasi.get(position).getTgl_transaksi(),
                            listMutasi.get(position).getNama_transaksi(),listMutasi.get(position).getDesc_transaksi(),
                            nominal,listMutasi.get(position).getCard_no());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMutasi.size();
    }

    public class MutasiViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tv_nama_transaksi, tv_tgl_transaksi, tv_nilai_transaksi, tv_status_transaksi;
        LinearLayoutCompat layout_item;

        public MutasiViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama_transaksi = itemView.findViewById(R.id.tv_nama_transaksi);
            tv_tgl_transaksi = itemView.findViewById(R.id.tv_tgl_transaksi);
            tv_nilai_transaksi = itemView.findViewById(R.id.tv_nilai_transaksi);
            tv_status_transaksi = itemView.findViewById(R.id.tv_status_transaksi);
            layout_item = itemView.findViewById(R.id.layout_item);

        }

    }
}
