package com.example.android_mobile_banking.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_mobile_banking.repositories.AuthRepositories;
import com.example.android_mobile_banking.repositories.UserRepositories;
import com.example.android_mobile_banking.response.AuthResponse;
import com.example.android_mobile_banking.response.UserResponse;

import org.json.JSONObject;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserResponse> mutableLiveData;
    private UserRepositories userRepositories;

    public void init(){
        if (mutableLiveData!=null){
            return;
        }
        userRepositories = UserRepositories.getInstance();
    }

    public LiveData<UserResponse> getUserData(String auth){
        if (mutableLiveData==null){
            userRepositories=UserRepositories.getInstance();
        }
        mutableLiveData=userRepositories.getData(auth);
        return mutableLiveData;
    }

    public LiveData<UserResponse> addPersonalData(String auth,JSONObject object){
        if (mutableLiveData==null){
            userRepositories=UserRepositories.getInstance();
        }
        mutableLiveData=userRepositories.addPersonalData(auth,object);
        return mutableLiveData;
    }
}
