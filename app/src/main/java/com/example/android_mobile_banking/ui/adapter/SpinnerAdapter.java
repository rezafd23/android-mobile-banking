package com.example.android_mobile_banking.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {
    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

}