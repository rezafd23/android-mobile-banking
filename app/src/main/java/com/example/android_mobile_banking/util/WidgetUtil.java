package com.example.android_mobile_banking.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.example.android_mobile_banking.R;
import com.example.android_mobile_banking.listener.DialogClickListener;
import com.example.android_mobile_banking.ui.adapter.SpinnerAdapter;
import com.example.android_mobile_banking.ui.dialog.DoubleBtnDialog;
import com.example.android_mobile_banking.widget.CustomSpinner;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WidgetUtil {

    public static void showDoubleButtonDialog(Activity activity, String title, String message,
                                              String btnPosTitle, DialogClickListener posListener,
                                              String btnNegTitle, DialogClickListener negListener) {
        DoubleBtnDialog baseDialog = new DoubleBtnDialog(activity);
        baseDialog.setMessage(message);
        baseDialog.setTitle(title);
        baseDialog.setButonPositive(btnPosTitle, posListener);
        baseDialog.setButonNegative(btnNegTitle, negListener);
        baseDialog.show();
    }
    public static void setupSpinner(Spinner spinner, String[] list) {
        //SpinnerAdapter adapter = new SpinnerAdapter(spinner.getContext(), android.R.layout.simple_spinner_item, Arrays.asList(list));
        SpinnerAdapter adapter = new SpinnerAdapter(spinner.getContext(), R.layout.spinner_item, Arrays.asList(list));
        adapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    public static boolean validateEditText(TextView view) {
        if (!TextUtils.isEmpty(view.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateEditTextPhone(TextView view) {
        if(view.getText().length() <= 13){
            return true;
        }else{
            return false;
        }
    }

    public static boolean notValidate(TextInputLayout inputLayout, EditText editText) {
        if (!validateEditText(editText)) {
            if(!validateEditTextPhone(editText)){
                editText.requestFocus();
                inputLayout.setError("No Telepon Lebih dari 13 Digit");
                return true;
            }else{
                editText.requestFocus();
                inputLayout.setError("Data Belum Terisi");
                return true;
            }
        } else {
            return false;
        }
    }
    public static void scrollToView(final NestedScrollView scrollView, final View view) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, view.getBottom());
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean notValidate(CustomSpinner spinner) {
        if (!validateField(spinner)) {
            spinner.requestFocus();
            spinner.setError("Data Belum Terisi");
            return true;
        } else {
            spinner.setError(null);
            return false;
        }
    }
    public static boolean validateField(Spinner spinner) {
        if (spinner.getSelectedItemPosition() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
