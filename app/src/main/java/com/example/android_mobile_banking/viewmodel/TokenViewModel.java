package com.example.android_mobile_banking.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_mobile_banking.model.ParamToken;
import com.example.android_mobile_banking.repositories.TokenRepositories;
import com.example.android_mobile_banking.repositories.UserRepositories;
import com.example.android_mobile_banking.response.TokenResponse;
import com.example.android_mobile_banking.response.UserResponse;

import org.json.JSONObject;

public class TokenViewModel extends ViewModel {

    private MutableLiveData<TokenResponse> mutableLiveData;
    private TokenRepositories tokenRepositories;

    public void init(){
        if (mutableLiveData!=null){
            return;
        }
        tokenRepositories = TokenRepositories.getInstance();
    }

    public LiveData<TokenResponse> getVoucerList(){
        if (mutableLiveData==null){
            tokenRepositories=TokenRepositories.getInstance();
        }
        mutableLiveData=tokenRepositories.getTokenList();
        return mutableLiveData;
    }

    public LiveData<TokenResponse> checkUser(String no_pelanggan){
        if (mutableLiveData==null){
            tokenRepositories=TokenRepositories.getInstance();
        }
        mutableLiveData=tokenRepositories.checkUser(no_pelanggan);
        return mutableLiveData;
    }


public LiveData<TokenResponse> buyToken(JSONObject paramToken){
        if (mutableLiveData==null){
            tokenRepositories=TokenRepositories.getInstance();
        }
        mutableLiveData=tokenRepositories.buyToken(paramToken);
        return mutableLiveData;
    }
}
