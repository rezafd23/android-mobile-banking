package com.example.android_mobile_banking.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
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

    private BottomNavigationView navigationView,navigationView2;
    private NestedScrollView scrollView;
    private RelativeLayout layout_loading_data, layout_apply_onprogress,
            layout_credit_finish;
    private LinearLayoutCompat layout_apply_new,layoutCard1;
    private AppCompatButton btn_ajukan,btn_continue_apply;
    private SwipeRefreshLayout swiperefresh;
    private HorizontalScrollView scroll_ewallet;
    private AppCompatTextView cardName1,tv_cash_used,welcome_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        navigationView.setItemIconTintList(null);
        navigationView2.setItemIconTintList(null);

        getDataUser(Util.getData(getApplication(), "access_token"));
        doAjukan();
        doContinue();
        doRefresh();
        doMenu();
        doMenu2();
    }

    private void doMenu(){
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mutasi:
                        MutasiRekeningActivity.navigate(MainActivity.this);
//                        mfragment = new Home();
                        break;
                    case R.id.profile:
                        ProfileActivity.navigate(MainActivity.this,"APPLICATION_SUCCESS");
//                        mfragment = new Search();
                        break;
                    case R.id.pln:
                        BuyPlnActivity.navigate(MainActivity.this);
//                        mfragment = new Acount();
                        break;
                    case R.id.logout:
                        LoginPinActivity.navigate(MainActivity.this,true);
                        break;
                }
                return true;
            }
        });
    }

    private void doMenu2(){
        navigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.profile:
                        ProfileActivity.navigate(MainActivity.this,"NOT_YET");
//                        mfragment = new Search();
                        break;
                    case R.id.logout:
                        LoginPinActivity.navigate(MainActivity.this,true);
                        break;
                }
                return true;
            }
        });
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
        navigationView2 = findViewById(R.id.bottomnav2);
        scrollView = findViewById(R.id.scroll_view);
        layout_loading_data = findViewById(R.id.layout_loading_data);
        layout_apply_new = findViewById(R.id.layout_apply_new);
        layout_apply_onprogress = findViewById(R.id.layout_apply_onprogress);
        layout_credit_finish = findViewById(R.id.layout_credit_finish);
        btn_ajukan = findViewById(R.id.btn_ajukan_credit);
        btn_continue_apply = findViewById(R.id.btn_continue_apply);
        swiperefresh = findViewById(R.id.swiperefresh);
        scroll_ewallet = findViewById(R.id.scroll_ewallet);
        cardName1 = findViewById(R.id.cardName1);
        layoutCard1 = findViewById(R.id.layoutCard1);
        tv_cash_used = findViewById(R.id.tv_cash_used);
        welcome_text = findViewById(R.id.welcome_text);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void doRefresh(){
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataUser(Util.getData(getApplication(),"access_token"));


            }
        });
    }

    private void getDataUser(String auth) {
        showProgressDialog();
        userViewModel.getUserData(auth)
                .observe(this, userResponse -> {
                    dismissProgressDialog();
//                    Log.v("isiResponse: ", userResponse.getResponse());
                    if (userResponse.getResponse().equals("200")) {
                        dismissProgressDialog();
                        if (swiperefresh.isRefreshing())swiperefresh.setRefreshing(false);
                        Log.v("isiResponse: ", userResponse.getPayload().toString());
                        Util.setData(getApplication(),"data_nasabah",userResponse.getPayload().toString());
                        setUpHome(userResponse.getPayload());
                    } else if (userResponse.getResponse().equals("401")){
                        LoginPinActivity.navigate(MainActivity.this,true);
                    }
                    else {
                        dismissProgressDialog();
                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan, Mohon Ulangi", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void doContinue(){
        btn_continue_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyDataActivity.navigate(MainActivity.this);
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
                    navigationView2.setVisibility(View.VISIBLE);
                    layout_loading_data.setVisibility(View.GONE);
                    layout_credit_finish.setVisibility(View.GONE);
                    layout_apply_onprogress.setVisibility(View.GONE);
                    layout_apply_new.setVisibility(View.VISIBLE);
                    navigationView.setVisibility(View.GONE);
                    scroll_ewallet.setVisibility(View.GONE);
                    layoutCard1.setVisibility(View.GONE);
                    break;
                case "IN_PROGGRESS":
                    layout_apply_onprogress.setVisibility(View.VISIBLE);
                    navigationView2.setVisibility(View.VISIBLE);
                    layout_loading_data.setVisibility(View.GONE);
                    layout_credit_finish.setVisibility(View.GONE);
                    layout_apply_new.setVisibility(View.GONE);
                    navigationView.setVisibility(View.GONE);
                    scroll_ewallet.setVisibility(View.GONE);
                    layoutCard1.setVisibility(View.GONE);
                    break;
                case "APPLICATION_SUCCESS":
                    layout_apply_new.setVisibility(View.GONE);
                    layout_loading_data.setVisibility(View.GONE);
                    layout_apply_onprogress.setVisibility(View.GONE);
                    layout_credit_finish.setVisibility(View.VISIBLE);
                    navigationView.setVisibility(View.VISIBLE);
                    navigationView2.setVisibility(View.GONE);
                    layoutCard1.setVisibility(View.VISIBLE);
                    scroll_ewallet.setVisibility(View.VISIBLE);
                    Log.v("saldo: ",jsonObject.getJSONObject("banking").getString("saldo"));
                    if (!jsonObject.getJSONObject("banking").getString("saldo").equals("0")){
                        tv_cash_used.setText(Util.round(String.valueOf(jsonObject.getJSONObject("banking").getInt("saldo"))));
                    }else {
                        tv_cash_used.setText(String.valueOf(jsonObject.getJSONObject("banking").getInt("saldo")));
                    }
                    cardName1.setText(jsonObject.getJSONObject("personal").getString("nama"));

                    String[]arrName=jsonObject.getJSONObject("personal").getString("nama").split(" ");

                    welcome_text.setText("Hi,\n"+arrName[0]+"!");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}