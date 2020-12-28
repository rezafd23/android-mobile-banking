package com.example.android_mobile_banking.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.listener.DialogClickListener;
import com.example.android_mobile_banking.widget.CustomScrollView;

public class LoginTermDialog extends Dialog implements CustomScrollView.ScrollViewListener {

    private Activity activity;
    private AppCompatButton btnAgree;
    private AppCompatTextView tvTitle;
    private String btnTitle;
    private CustomScrollView scrollView;
    private DialogClickListener listener;
    private WebView mWebView;

    public LoginTermDialog(@NonNull Context context) {
        super(context);
        activity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_term_dialog);
        initView();

        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultFontSize(12);
        getTncData();

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnAgree.setEnabled(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ViewTreeObserver observer = scrollView.getViewTreeObserver();
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int viewHeight = scrollView.getMeasuredHeight();
                        int contentHeight = scrollView.getChildAt(0).getMeasuredHeight();
                        if (viewHeight > contentHeight) {
                            btnAgree.setEnabled(true);
                        } else {
                            btnAgree.setEnabled(false);
                        }
                    }
                });
            }
        }, 0);
    }

    private void getTncData() {
        String data3 ="  <![CDATA[\n" +
                "       <h2>Apakah Lorem Ipsum itu?</h2> <br>\n" +
                "        Lorem Ipsum adalah contoh teks atau dummy dalam industri percetakan dan penataan huruf atau typesetting.\n" +
                "        <br>Lorem Ipsum telah menjadi standar contoh teks sejak tahun 1500an, saat seorang tukang cetak yang tidak dikenal mengambil sebuah kumpulan teks dan mengacaknya untuk menjadi sebuah buku contoh huruf. Ia tidak hanya bertahan selama 5 abad, tapi juga telah beralih ke penataan huruf elektronik, tanpa ada perubahan apapun. Ia mulai dipopulerkan pada tahun 1960 dengan diluncurkannya lembaran-lembaran Letraset yang menggunakan kalimat-kalimat dari Lorem Ipsum, dan seiring munculnya perangkat lunak Desktop Publishing seperti Aldus PageMaker juga memiliki versi Lorem Ipsum.</p>\n" +
                "    ]]>";
        mWebView.loadData(String.valueOf(Html.fromHtml(data3)), "text/html", "utf-8");
    }
    private void initView(){
        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        tvTitle = findViewById(R.id.tv_title);
        btnAgree = findViewById(R.id.btn_agree);

        mWebView = findViewById(R.id.myWebView);

        scrollView = findViewById(R.id.scrollview);
        scrollView.setScrollViewListener(this);
        try {
            if (listener != null) {
                btnAgree.setText(btnTitle);
                btnAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        listener.onClick(LoginTermDialog.this, v);
                    }
                });
            }
        } catch (Exception e) {
            ;
        }
    }


    @Override
    public void onBackPressed() {
        LoginActivity.navigate(activity, true);
    }

//    @Override
//    public void show() {
//        super.show();
//        Window window = this.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//    }

    public void setButtonAgree(String title, DialogClickListener listener) {
        this.btnTitle = title;
        this.listener = listener;
    }

    @Override
    public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
        View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        if (diff == 0) {
            btnAgree.setEnabled(true);
        }
    }
}