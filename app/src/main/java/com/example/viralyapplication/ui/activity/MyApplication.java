package com.example.viralyapplication.ui.activity;


import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.example.viralyapplication.utility.TaskExecuter;
import com.example.viralyapplication.utility.Utils;
import com.google.android.gms.common.util.HttpUtils;

public class MyApplication extends MultiDexApplication {
    private static final String TAG = MyApplication.class.getSimpleName();
    public static MyApplication instance;
    public static Context mContext;
    public boolean loadFriendSuccess = false;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        initObjects();
    }
    private void initObjects() {
        TaskExecuter.getInstance(this);
    }

}