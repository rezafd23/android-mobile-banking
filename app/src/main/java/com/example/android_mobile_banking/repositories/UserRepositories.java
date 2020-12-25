package com.example.android_mobile_banking.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.android_mobile_banking.constant.ApiService;
import com.example.android_mobile_banking.model.Mutasi;
import com.example.android_mobile_banking.model.Token;
import com.example.android_mobile_banking.response.AuthResponse;
import com.example.android_mobile_banking.response.TokenResponse;
import com.example.android_mobile_banking.response.UserResponse;
import com.example.android_mobile_banking.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
                                Log.v("isiRESPONSEUSER: ", response.toString());
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

    public MutableLiveData<UserResponse> addPersonalData(String authorization, JSONObject object) {
        MutableLiveData<UserResponse> dataUser = new MutableLiveData<>();
        Log.v("isiJSON: ", object.toString());
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
                                Log.v("isiRESPONSEUSER: ", response.toString());
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

    public MutableLiveData<UserResponse> addRelativeData(String authorization, JSONObject object) {
        MutableLiveData<UserResponse> dataUser = new MutableLiveData<>();
        Log.v("isiJSON: ", object.toString());
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
                                Log.v("isiRESPONSEUSER: ", response.toString());
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

    public MutableLiveData<UserResponse> addWorkData(String authorization, JSONObject object) {
        MutableLiveData<UserResponse> dataUser = new MutableLiveData<>();
        Log.v("isiJSON: ", object.toString());
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
                                Log.v("isiRESPONSEUSER: ", response.toString());
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
        Log.v("isiJSON: ", file.toString());
        try {
            final UserResponse res = new UserResponse();
            AndroidNetworking.upload(ApiService.uploadEKTP)
                    .addHeaders("authorization", authorization)
                    .addHeaders("Content-Type", "multipart/form-data")
                    .addMultipartFile("ektp", file)
                    .setTag("addWorkData")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            Log.v("onPRoggress: ", String.valueOf(bytesUploaded));
                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiRESPONSEUSER: ", response.toString());
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
                    .addBodyParameter("ektp", no_kttp)
                    .addHeaders("authorization", authorization)
                    .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                    .setTag("finishRegister")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            Log.v("onPRoggress: ", String.valueOf(bytesUploaded));
                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiRESPONSEUSER: ", response.toString());
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

    public MutableLiveData<UserResponse> getMutasi(String no_rekening, String start_date, String end_date) {
        MutableLiveData<UserResponse> listUserData = new MutableLiveData<>();
        try {
            final UserResponse res = new UserResponse();
            AndroidNetworking.get(ApiService.getMutasi)
                    .addPathParameter("no_rekening", no_rekening)
                    .addPathParameter("start_date", start_date)
                    .addPathParameter("end_date", end_date)
                    .setTag("getTokenList")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiResponse: ", response.toString());
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    if (response.getString("message").equals("Tidak Ada Transaksi")) {
                                        res.setMessage(response.getString("message"));
                                    } else {
                                        res.setMessage(response.getString("message"));
                                        List<Mutasi> listMutasi = new ArrayList<>();
                                        for (int i = 0; i < response.getJSONArray("payload").length(); i++) {
                                            Mutasi mutasi = new Mutasi();
                                            mutasi.setNo_rekening(response.getJSONArray("payload").getJSONObject(i).getString("no_rekening"));
                                            mutasi.setNama_transaksi(response.getJSONArray("payload").getJSONObject(i).getString("nama_transaksi"));
                                            mutasi.setDesc_transaksi(response.getJSONArray("payload").getJSONObject(i).getString("desc_transaksi"));
                                            mutasi.setNominal(response.getJSONArray("payload").getJSONObject(i).getInt("nominal"));
                                            mutasi.setStatus_transaksi(response.getJSONArray("payload").getJSONObject(i).getString("status_transaksi"));
                                            mutasi.setTgl_transaksi(response.getJSONArray("payload").getJSONObject(i).getString("tgl_transaksi"));
                                            mutasi.setCard_no(response.getJSONArray("payload").getJSONObject(i).getString("card_no"));
                                            listMutasi.add(mutasi);
                                        }
                                        res.setMutasiList(listMutasi);
                                    }

                                } else {
                                    res.setResponse(response.getString("response"));
                                    res.setMessage(response.getString("message"));
                                }
                                listUserData.setValue(res);
                            } catch (Exception e) {
                                listUserData.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            listUserData.setValue(null);
                            Log.v("Cek Error", "onError: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            listUserData.setValue(null);
            e.printStackTrace();
        }
        return listUserData;
    }
}
