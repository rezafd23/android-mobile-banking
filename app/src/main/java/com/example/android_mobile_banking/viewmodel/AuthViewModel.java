package com.example.android_mobile_banking.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_mobile_banking.repositories.AuthRepositories;
import com.example.android_mobile_banking.response.AuthResponse;

import org.json.JSONObject;

public class AuthViewModel extends ViewModel {
    private MutableLiveData<AuthResponse> mutableLiveData;
    private AuthRepositories authRepositories;

    public void init(){
        if (mutableLiveData!=null){
            return;
        }
        authRepositories = AuthRepositories.getInstance();
//        authRepositories=AuthRepositories
    }

    public LiveData<AuthResponse> registerPhone(JSONObject object){
        if (mutableLiveData==null){
            authRepositories=AuthRepositories.getInstance();
        }
        mutableLiveData=authRepositories.registerPhone(object);
        return mutableLiveData;
    }

    public LiveData<AuthResponse> submitOtpRegister(JSONObject object){
        if (mutableLiveData==null){
            authRepositories=AuthRepositories.getInstance();
        }
        mutableLiveData=authRepositories.submitOtpRegister(object);
        return mutableLiveData;
    }

    public LiveData<AuthResponse> createPIN(JSONObject object){
        if (mutableLiveData==null){
            authRepositories=AuthRepositories.getInstance();
        }
        mutableLiveData=authRepositories.createPin(object);
        return mutableLiveData;
    }

    public LiveData<AuthResponse> loginPIN(JSONObject object){
        if (mutableLiveData==null){
            authRepositories=AuthRepositories.getInstance();
        }
        mutableLiveData=authRepositories.loginPin(object);
        return mutableLiveData;
    }
}
