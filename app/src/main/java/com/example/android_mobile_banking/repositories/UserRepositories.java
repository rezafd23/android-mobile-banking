package com.example.android_mobile_banking.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.android_mobile_banking.constant.ApiService;
import com.example.android_mobile_banking.response.AuthResponse;
import com.example.android_mobile_banking.response.UserResponse;
import com.example.android_mobile_banking.util.Util;

import org.json.JSONObject;

import java.io.File;

public class UserRepositories {

    private static UserRepositories userRepositories;

    public static UserRepositories getInstance() {
        if (userRepositories == null) {
            userRepositories = new UserRepositories();
        }
        return userRepositories;
    }

    public MutableLiveData<UserResponse> getData(String authorization) {
        MutableLiveData<UserResponse> dataUser = new MutableLiveData<>();

        try {
            final UserResponse res = new UserResponse();
            AndroidNetworking.get(ApiService.getData)
                    .addHeaders("authorization", authorization)
                    .setTag("getData")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiRESPONSEUSER: ",response.toString());
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    res.setStatus(response.getString("status"));
                                    res.setPayload(response.getJSONObject("payload"));
                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setMessage(response.getString("payload"));
                                }
                                dataUser.setValue(res);
                            } catch (Exception e) {
                                dataUser.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            dataUser.setValue(null);
                            Log.v("Cek Error", "onError: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            dataUser.setValue(null);
            e.printStackTrace();
        }
        return dataUser;
    }

    public MutableLiveData<UserResponse> addPersonalData(String authorization,JSONObject object) {
        MutableLiveData<UserResponse> dataUser = new MutableLiveData<>();
        Log.v("isiJSON: ",object.toString());
        try {
            final UserResponse res = new UserResponse();
            AndroidNetworking.post(ApiService.addPersonalData)
                    .addHeaders("authorization", authorization)
                    .addJSONObjectBody(object)
                    .setTag("addPersonalData")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiRESPONSEUSER: ",response.toString());
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    res.setStatus(response.getString("status"));
                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setMessage(response.getString("payload"));
                                }
                                dataUser.setValue(res);
                            } catch (Exception e) {
                                dataUser.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            dataUser.setValue(null);
                            Log.v("Cek Error", "onError addPersonalData: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            dataUser.setValue(null);
            e.printStackTrace();
        }
        return dataUser;
    }

    public MutableLiveData<UserResponse> addRelativeData(String authorization,JSONObject object) {
        MutableLiveData<UserResponse> dataUser = new MutableLiveData<>();
        Log.v("isiJSON: ",object.toString());
        try {
            final UserResponse res = new UserResponse();
            AndroidNetworking.post(ApiService.addRelativeData)
                    .addHeaders("authorization", authorization)
                    .addJSONObjectBody(object)
                    .setTag("addRelativeData")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiRESPONSEUSER: ",response.toString());
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    res.setStatus(response.getString("status"));
                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setMessage(response.getString("payload"));
                                }
                                dataUser.setValue(res);
                            } catch (Exception e) {
                                dataUser.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            dataUser.setValue(null);
                            Log.v("Cek Error", "onError addRelativeData: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            dataUser.setValue(null);
            e.printStackTrace();
        }
        return dataUser;
    }

    public MutableLiveData<UserResponse> addWorkData(String authorization,JSONObject object) {
        MutableLiveData<UserResponse> dataUser = new MutableLiveData<>();
        Log.v("isiJSON: ",object.toString());
        try {
            final UserResponse res = new UserResponse();
            AndroidNetworking.post(ApiService.addWorkData)
                    .addHeaders("authorization", authorization)
                    .addJSONObjectBody(object)
                    .setTag("addWorkData")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiRESPONSEUSER: ",response.toString());
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    res.setStatus(response.getString("status"));
                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setMessage(response.getString("payload"));
                                }
                                dataUser.setValue(res);
                            } catch (Exception e) {
                                dataUser.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            dataUser.setValue(null);
                            Log.v("Cek Error", "onError addWorkData: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            dataUser.setValue(null);
            e.printStackTrace();
        }
        return dataUser;
    }

    public MutableLiveData<UserResponse> uploadEKTP(String authorization, File file) {
        MutableLiveData<UserResponse> dataUser = new MutableLiveData<>();
        Log.v("isiJSON: ",file.toString());
        try {
            final UserResponse res = new UserResponse();
            AndroidNetworking.upload(ApiService.uploadEKTP)
                    .addHeaders("authorization", authorization)
                    .addHeaders("Content-Type","multipart/form-data")
                    .addMultipartFile("ektp",file)
                    .setTag("addWorkData")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            Log.v("onPRoggress: ",String.valueOf(bytesUploaded));
                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiRESPONSEUSER: ",response.toString());
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    res.setStatus(response.getString("status"));
                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setMessage(response.getString("payload"));
                                }
                                dataUser.setValue(res);
                            } catch (Exception e) {
                                dataUser.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            dataUser.setValue(null);
                            Log.v("Cek Error", "onError addWorkData: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            dataUser.setValue(null);
            e.printStackTrace();
        }
        return dataUser;
    }

    public MutableLiveData<UserResponse> finishRegister(String authorization, String no_kttp) {
        MutableLiveData<UserResponse> dataUser = new MutableLiveData<>();
        try {
            final UserResponse res = new UserResponse();
            AndroidNetworking.post(ApiService.finishRegister)
                    .addBodyParameter("ektp",no_kttp)
                    .addHeaders("authorization", authorization)
                    .addHeaders("Content-Type","application/x-www-form-urlencoded")
                    .setTag("finishRegister")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            Log.v("onPRoggress: ",String.valueOf(bytesUploaded));
                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiRESPONSEUSER: ",response.toString());
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
//                                    res.setStatus(response.getString("status"));
                                } else {
                                    res.setResponse(response.getString("response"));
//                                    res.setMessage(response.getString("payload"));
                                }
                                dataUser.setValue(res);
                            } catch (Exception e) {
                                dataUser.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            dataUser.setValue(null);
                            Log.v("Cek Error", "onError addWorkData: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            dataUser.setValue(null);
            e.printStackTrace();
        }
        return dataUser;
    }
}
