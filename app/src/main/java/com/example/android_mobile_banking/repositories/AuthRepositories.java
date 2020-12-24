package com.example.android_mobile_banking.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.android_mobile_banking.constant.ApiService;
import com.example.android_mobile_banking.response.AuthResponse;

import org.json.JSONObject;

public class AuthRepositories {

    private static AuthRepositories authRepositories;

    public static AuthRepositories getInstance() {
        if (authRepositories == null) {
            authRepositories = new AuthRepositories();
        }
        return authRepositories;
    }

    public MutableLiveData<AuthResponse> registerPhone(JSONObject object) {
        MutableLiveData<AuthResponse> registerData = new MutableLiveData<>();

        try {
            final AuthResponse res = new AuthResponse();
            AndroidNetworking.post(ApiService.register)
                    .addJSONObjectBody(object)
                    .setTag("registerPhone")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    res.setOtp(response.getJSONObject("payload").getString("otp"));
                                    res.setOtpId(response.getJSONObject("payload").getString("id"));
                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setPayload(response.getString("payload"));
                                }
                                registerData.setValue(res);
                            } catch (Exception e) {
                                registerData.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            registerData.setValue(null);
                            Log.v("Cek Error", "onError: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            registerData.setValue(null);
            e.printStackTrace();
        }
        return registerData;
    }

    public MutableLiveData<AuthResponse> loginPhone(JSONObject object) {
        MutableLiveData<AuthResponse> registerData = new MutableLiveData<>();

        try {
            final AuthResponse res = new AuthResponse();
            AndroidNetworking.post(ApiService.loginPhone)
                    .addJSONObjectBody(object)
                    .setTag("loginPhone")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    res.setOtp(response.getJSONObject("payload").getString("otp"));
                                    res.setOtpId(response.getJSONObject("payload").getString("id"));
                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setPayload(response.getString("payload"));
                                }
                                registerData.setValue(res);
                            } catch (Exception e) {
                                registerData.setValue(null);
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onError(ANError anError) {
                            registerData.setValue(null);
                            Log.v("Cek Error", "onError: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            registerData.setValue(null);
            e.printStackTrace();
        }
        return registerData;
    }

    public MutableLiveData<AuthResponse> submitOtpRegister(JSONObject object) {
        MutableLiveData<AuthResponse> registerData = new MutableLiveData<>();

        try {
            final AuthResponse res = new AuthResponse();
            AndroidNetworking.post(ApiService.submitOTP)
                    .addJSONObjectBody(object)
                    .setTag("submitOTP")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                res.setResponse(response.getString("response"));
                                res.setPayload(response.getString("payload"));

                                registerData.setValue(res);
                            } catch (Exception e) {
                                registerData.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            registerData.setValue(null);
                            Log.v("Cek Error", "onError: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            registerData.setValue(null);
            e.printStackTrace();
        }
        return registerData;
    }

    public MutableLiveData<AuthResponse> createPin(JSONObject object) {
        MutableLiveData<AuthResponse> registerData = new MutableLiveData<>();

        try {
            final AuthResponse res = new AuthResponse();
            AndroidNetworking.post(ApiService.createPIN)
                    .addJSONObjectBody(object)
                    .setTag("createPin")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                res.setResponse(response.getString("response"));
                                res.setPayload(response.getString("payload"));
                                registerData.setValue(res);
                            } catch (Exception e) {
                                registerData.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            registerData.setValue(null);
                            Log.v("Cek Error", "onError: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            registerData.setValue(null);
            e.printStackTrace();
        }
        return registerData;
    }

    public MutableLiveData<AuthResponse> loginPin(JSONObject object) {
        MutableLiveData<AuthResponse> registerData = new MutableLiveData<>();

        try {
            final AuthResponse res = new AuthResponse();
            AndroidNetworking.post(ApiService.loginPIN)
                    .addJSONObjectBody(object)
                    .setTag("loginPIN")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    res.setStatus(response.getString("status"));
                                    res.setAccess_token(response.getJSONObject("payload").getString("access_token"));
                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setPayload(response.getString("payload"));
                                }
                                registerData.setValue(res);
                            } catch (Exception e) {
                                registerData.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            registerData.setValue(null);
                            Log.v("Cek Error", "onError: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            registerData.setValue(null);
            e.printStackTrace();
        }
        return registerData;
    }
}