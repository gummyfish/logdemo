package com.example.gen.myapplication;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.util.Log;

public class ExampleApp extends Application {

    private String TAG = AnalyzeLog.class.getSimpleName();
    private BroadcastReceiver mLogSwitchReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App start");
        registerLogSwitchReceiver();
    }

    private void registerLogSwitchReceiver() {
        Log.d(TAG, "registerLogSwitchReceiver");
        mLogSwitchReceiver = new LogReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(LogReceiver.ACTION_BEGIN);
        filter.addAction(LogReceiver.ACTION_END);
        registerReceiver(mLogSwitchReceiver, filter, LogReceiver.PERMISSION, null);
    }

    @Override
    public void onTerminate() {
        Log.d(TAG,"onTerminate");
        unregisterLogSwitchReceiver();
        super.onTerminate();
    }

    private void unregisterLogSwitchReceiver() {
        Log.d(TAG, "unregisterLogSwitchReceiver");
        unregisterReceiver(mLogSwitchReceiver);
    }
}
