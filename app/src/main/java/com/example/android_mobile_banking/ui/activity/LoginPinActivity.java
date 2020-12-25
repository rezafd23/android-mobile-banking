package com.example.android_mobile_banking.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.ui.helper.SingleActivity;
import com.example.android_mobile_banking.util.Util;
import com.example.android_mobile_banking.viewmodel.AuthViewModel;

import org.json.JSONObject;

public class LoginPinActivity extends SingleActivity {

    private PinEntryEditText pinEntry;
    private final int MAX_PIN = 6;
    private final int MAX_RETRY = 3;
    private String loginPin = null;
    private AppCompatTextView tv_title, tv_donthave,tv_alert;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);
        initView();

        doLogin();

        tv_donthave.setOnClickListener(view -> {
            LoginActivity.navigate(LoginPinActivity.this,true);
        });

        pinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    pinEntry.setError(false);
                    pinEntry.setPinLineColors(getColorStateList(R.color.colorBackgroundInfoTint));
                    tv_alert.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, LoginPinActivity.class);
        activity.startActivity(intent);
    }

    public static void navigate(Activity activity, boolean clearPrevStack) {
        Intent intent = new Intent(activity, LoginPinActivity.class);
        if (clearPrevStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
        activity.finish();
    }

    private void initView() {
        pinEntry = findViewById(R.id.txt_pin_entry);
        tv_title = findViewById(R.id.tv_title);
        tv_donthave = findViewById(R.id.tv_donthave);
        tv_alert = findViewById(R.id.tv_alert);

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
    }

    private void doLogin() {
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.length()==MAX_PIN){
                        processLogin(Util.getData(getApplication(),"username"),str.toString());
                    }
                }
            });
        }
    }

    private void processLogin(String username, String pin) {
        showProgressDialog();
        try {
            JSONObject object = new JSONObject();
            object.put("username", username);
            object.put("pin", pin);
            authViewModel.loginPIN(object)
                    .observe(this, registerResponse -> {
                        dismissProgressDialog();
                        Log.v("isiREsponse: ",registerResponse.getResponse());
                        if (registerResponse.getResponse().equals("200")) {
                            Util.setData(getApplication(), "access_token", registerResponse.getAccess_token());
                            MainActivity.navigate(LoginPinActivity.this, true);
                        } else {
                            pinEntry.setText("");
                            pinEntry.setPinLineColors(getColorStateList(R.color.colorTextError));
                            tv_alert.setVisibility(View.VISIBLE);
                            pinEntry.setError(true);
//                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan, Mohon Ulangi", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Log.v("error1: ", "Try Set Username");
            e.printStackTrace();
        }
    }

    public void onKeyboardClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                keyPress('1');
                break;
            case R.id.tv_2:
                keyPress('2');
                break;
            case R.id.tv_3:
                keyPress('3');
                break;
            case R.id.tv_4:
                keyPress('4');
                break;
            case R.id.tv_5:
                keyPress('5');
                break;
            case R.id.tv_6:
                keyPress('6');
                break;
            case R.id.tv_7:
                keyPress('7');
                break;
            case R.id.tv_8:
                keyPress('8');
                break;
            case R.id.tv_9:
                keyPress('9');
                break;
            case R.id.tv_0:
                keyPress('0');
                break;
            case R.id.tv_backspace:
                deleteChar();
                break;
        }
    }

    private void keyPress(char c) {
        if (pinEntry.getText().length() < MAX_PIN) {
            pinEntry.setText(pinEntry.getText().append(c).toString());
            tv_title.setText(getString(R.string.input_pin));
        }

    }

    private void deleteChar() {
        if (pinEntry.getText().length() > 0) {
            String s = pinEntry.getText().delete(pinEntry.getText().length() - 1, pinEntry.getText().length()).toString();
            pinEntry.setText(s);
        }
    }
}