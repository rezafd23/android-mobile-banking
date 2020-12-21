package com.example.android_mobile_banking.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.listener.DialogClickListener;

public class DoubleBtnDialog extends Dialog implements View.OnClickListener {

    private AppCompatImageView ivIcon,ivClose;
    private AppCompatTextView tvMessage,tvTitle;
    private AppCompatButton btnPositive,btnNegative;
    private boolean noClose = false;

    @DrawableRes
    private int iconResource;
    private String message;
    private String title;
    private String posTitle;
    private String negTitle;
    private DialogClickListener positiveListener;
    private DialogClickListener negativeListener;

    public DoubleBtnDialog(@NonNull Context context) {
        super(context);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_double_btn);
        ivIcon = findViewById(R.id.iv_icon);
        ivClose = findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoubleBtnDialog.this.dismiss();
            }
        });

        if (noClose){
            ivClose.setVisibility(View.GONE);
        }
        tvMessage = findViewById(R.id.tv_message);
        tvTitle = findViewById(R.id.tv_title);
        btnPositive = findViewById(R.id.btn_positive);
        btnNegative = findViewById(R.id.btn_negative);

        try {
            if (message != null) {
                tvMessage.setText(message);
            }

            if (title != null) {
                tvTitle.setText(title);
            }

            if (posTitle != null) {
                btnPositive.setText(posTitle);
                btnPositive.setVisibility(View.VISIBLE);
                btnPositive.setOnClickListener(this);
            }

            if (negTitle != null) {
                btnNegative.setText(negTitle);
                btnNegative.setVisibility(View.VISIBLE);
                btnNegative.setOnClickListener(this);
            }

            if (iconResource > 0) {
                ivIcon.setImageResource(iconResource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * @param message as a dialog message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void setIconResource(@DrawableRes int iconResource) {
        this.iconResource = iconResource;
    }

    public void setButonPositive(String title, DialogClickListener listener) {
        this.positiveListener = listener;
        this.posTitle = title;
    }

    public void setButonNegative(String title, DialogClickListener listener) {
        this.negativeListener = listener;
        this.negTitle = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNoClose(boolean NoClose) {
        this.noClose = NoClose;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_positive) {
            if (positiveListener != null) {
                positiveListener.onClick(DoubleBtnDialog.this, view);
            }
            dismiss();
        } else if (view.getId() == R.id.btn_negative) {
            if (negativeListener != null) {
                negativeListener.onClick(DoubleBtnDialog.this, view);
            }
        }
    }
}
