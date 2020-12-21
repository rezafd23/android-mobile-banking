package com.example.android_mobile_banking.repositories;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.android_mobile_banking.callback.BaseCallback;
import com.example.android_mobile_banking.constant.ApiService;
import com.example.android_mobile_banking.response.AuthResponse;

import org.json.JSONObject;


public class AuthController {

    public void doRegister(String username, BaseCallback<AuthResponse> baseCallback){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",username);

            final AuthResponse res = new AuthResponse();
            AndroidNetworking.post(ApiService.register)
                    .addJSONObjectBody(jsonObject)
                    .setTag("registerPhone")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("response").equals("200")){
                                    res.setResponse(response.getString("response"));
                                    res.setOtp(response.getJSONObject("payload").getString("otp"));
                                    res.setOtpId(response.getJSONObject("payload").getString("id"));
                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setPayload(response.getString("payload"));
                                }
                                baseCallback.onSuccess(res);
                            }catch (Exception e){
                                baseCallback.onError(e);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            baseCallback.onError(anError);
                            Log.v("Cek Error", "onError: "+anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void submitOTP(JSONObject jsonObject, BaseCallback<AuthResponse> baseCallback){
        final AuthResponse res = new AuthResponse();
        AndroidNetworking.post(ApiService.submitOTP)
                .addJSONObjectBody(jsonObject)
                .setTag("submitOTP")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            if (response.getString("response").equals("200")){
//                                res.setResponse(response.getString("response"));
//                                res.setOtp(response.getJSONObject("payload").getString("otp"));
//                                res.setOtpId(response.getJSONObject("payload").getString("id"));
//                            } else {
                                res.setResponse(response.getString("response"));
                                res.setPayload(response.getString("payload"));
//                            }
                            baseCallback.onSuccess(res);
                        }catch (Exception e){
                            baseCallback.onError(e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        baseCallback.onError(anError);
                        Log.v("Cek Error", "onError: "+anError);
                        anError.printStackTrace();
                    }
                });
    }
}
