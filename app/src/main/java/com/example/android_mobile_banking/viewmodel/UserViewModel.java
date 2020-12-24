package com.example.android_mobile_banking.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_mobile_banking.repositories.AuthRepositories;
import com.example.android_mobile_banking.repositories.UserRepositories;
import com.example.android_mobile_banking.response.AuthResponse;
import com.example.android_mobile_banking.response.UserResponse;

import org.json.JSONObject;

import java.io.File;

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

    public LiveData<UserResponse> addRelativeData(String auth,JSONObject object){
        if (mutableLiveData==null){
            userRepositories=UserRepositories.getInstance();
        }
        mutableLiveData=userRepositories.addRelativeData(auth,object);
        return mutableLiveData;
    }

    public LiveData<UserResponse> addWorkData(String auth,JSONObject object){
        if (mutableLiveData==null){
            userRepositories=UserRepositories.getInstance();
        }
        mutableLiveData=userRepositories.addWorkData(auth,object);
        return mutableLiveData;
    }

    public LiveData<UserResponse> uploadEKTP(String auth, File file){
        if (mutableLiveData==null){
            userRepositories=UserRepositories.getInstance();
        }
        mutableLiveData=userRepositories.uploadEKTP(auth,file);
        return mutableLiveData;
    }

    public LiveData<UserResponse> finishRegister(String auth, String noktp){
        if (mutableLiveData==null){
            userRepositories=UserRepositories.getInstance();
        }
        mutableLiveData=userRepositories.finishRegister(auth,noktp);
        return mutableLiveData;
    }

    public LiveData<UserResponse> getMutasi(String no_rekening, String start_date,String end_date){
        if (mutableLiveData==null){
            userRepositories=UserRepositories.getInstance();
        }
        mutableLiveData=userRepositories.getMutasi(no_rekening,start_date,end_date);
        return mutableLiveData;
    }
}
