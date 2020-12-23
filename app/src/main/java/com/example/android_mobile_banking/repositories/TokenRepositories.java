package com.example.android_mobile_banking.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.android_mobile_banking.constant.ApiService;
import com.example.android_mobile_banking.model.Token;
import com.example.android_mobile_banking.response.AuthResponse;
import com.example.android_mobile_banking.response.TokenResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TokenRepositories {

    private static TokenRepositories tokenRepositories;

    public static TokenRepositories getInstance() {
        if (tokenRepositories == null) {
            tokenRepositories = new TokenRepositories();
        }
        return tokenRepositories;
    }

    public MutableLiveData<TokenResponse> getTokenList() {
        MutableLiveData<TokenResponse> listTokenData = new MutableLiveData<>();

        try {
            final TokenResponse res = new TokenResponse();
            AndroidNetworking.get(ApiService.getTokenList)
                    .setTag("getTokenList")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.v("isiResponse: ",response.toString());
                                if (response.getString("response").equals("200")) {
                                    res.setResponse(response.getString("response"));
                                    List<Token>listToken = new ArrayList<>();
                                    JSONArray jsonArrayPayload = response.getJSONArray("payload");
                                    for (int i=0;i<jsonArrayPayload.length();i++){
                                        Token token = new Token();
                                        token.setId_voucer(jsonArrayPayload.getJSONObject(i).getInt("id_voucer"));
                                        token.setPrice(jsonArrayPayload.getJSONObject(i).getInt("price"));
                                        token.setVoucer(jsonArrayPayload.getJSONObject(i).getString("voucer"));
                                        listToken.add(token);
                                    }
                                    res.setPayload(listToken);
                                } else {
                                    res.setResponse(response.getString("response"));
//                                    res.setPayload(response.getString("payload"));
                                }
                                listTokenData.setValue(res);
                            } catch (Exception e) {
                                listTokenData.setValue(null);
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            listTokenData.setValue(null);
                            Log.v("Cek Error", "onError: " + anError);
                            anError.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            listTokenData.setValue(null);
            e.printStackTrace();
        }
        return listTokenData;
    }
}
