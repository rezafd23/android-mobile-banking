package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.viewmodel.AuthViewModel;
import com.example.android_mobile_banking.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

public class MainActivity extends SingleActivity {

    private UserViewModel userViewModel;

    private BottomNavigationView navigationView;
    private NestedScrollView scrollView;
    private RelativeLayout layout_loading_data, layout_apply_onprogress,
            layout_credit_finish;
    private LinearLayoutCompat layout_apply_new;
    private AppCompatButton btn_ajukan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        navigationView.setItemIconTintList(null);

        getDataUser(Util.getData(getApplication(), "access_token"));
        doAjukan();
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void navigate(Activity activity, boolean clearPrevStack) {
        Intent intent = new Intent(activity, MainActivity.class);
        if (clearPrevStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
        activity.finish();
    }

    private void initView() {
        navigationView = findViewById(R.id.bottomnav);
        scrollView = findViewById(R.id.scroll_view);
        layout_loading_data = findViewById(R.id.layout_loading_data);
        layout_apply_new = findViewById(R.id.layout_apply_new);
        layout_apply_onprogress = findViewById(R.id.layout_apply_onprogress);
        layout_credit_finish = findViewById(R.id.layout_credit_finish);
        btn_ajukan = findViewById(R.id.btn_ajukan_credit);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void getDataUser(String auth) {
        showProgressDialog();
        userViewModel.getUserData(auth)
                .observe(this, userResponse -> {
                    dismissProgressDialog();
                    Log.v("isiREsponse: ", userResponse.getResponse());
                    if (userResponse.getResponse().equals("200")) {
                        dismissProgressDialog();
                        Log.v("isiResponse: ", userResponse.getPayload().toString());
                        setUpHome(userResponse.getPayload());
                    } else {
                        dismissProgressDialog();
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan, Mohon Ulangi", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void doAjukan(){
        btn_ajukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyDataActivity.navigate(MainActivity.this);
            }
        });
    }

    private void setUpHome(JSONObject jsonObject) {
        try {
            String application_status = jsonObject.getString("application_status");

            switch (application_status){
                case "NOT_YET":
                    layout_loading_data.setVisibility(View.GONE);
                    layout_apply_new.setVisibility(View.VISIBLE);
                    break;
                case "IN_PROGGRESS":
                    layout_loading_data.setVisibility(View.GONE);
                    layout_apply_onprogress.setVisibility(View.VISIBLE);
                    break;
                case "Application_Success":
                    layout_loading_data.setVisibility(View.GONE);
                    layout_credit_finish.setVisibility(View.VISIBLE);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}