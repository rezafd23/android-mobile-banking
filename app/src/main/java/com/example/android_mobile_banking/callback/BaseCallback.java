package com.example.android_mobile_banking.callback;

public interface BaseCallback<T> {
    void onSuccess(T result);

    void onError(Throwable throwable);
}
