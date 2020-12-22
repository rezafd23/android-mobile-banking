package com.example.android_mobile_banking.util;

import java.util.Hashtable;

public class IntentHelper {

    private static IntentHelper mInstance;
    private Hashtable<String, Object> mHash;

    private IntentHelper() {
        mHash = new Hashtable<String, Object>();
    }

    private static IntentHelper getInstance() {
        if(mInstance==null) {
            mInstance = new IntentHelper();
        }
        return mInstance;
    }

    public static void addObjectForKey(Object object, String key) {
        getInstance().mHash.put(key, object);
    }

    public static Object getObjectForKey(String key) {
       IntentHelper helper = getInstance();
        Object data = helper.mHash.get(key);
        //helper.mHash.remove(key);
        //helper = null;
        return data;
    }

    public static void remove(String key) {
        IntentHelper helper = getInstance();
        helper.mHash.remove(key);
        helper = null;

    }
}
