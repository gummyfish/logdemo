package com.example.gen.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LogReceiver extends BroadcastReceiver {

    private static final String TAG = AnalyzeLog.class.getSimpleName();

    public static final String PERMISSION = "com.huawei.application.permission.logCollect";

    public static final String ACTION_BEGIN = "com.example.action.log.begin";
    public static final String ACTION_END = "com.example.action.log.end";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BEGIN.equals(intent.getAction())) {
            Log.d(TAG, "receive ACTION_BEGIN");
            logOn(context);
        } else if (ACTION_END.equals(intent.getAction())) {
            Log.d(TAG, "receive ACTION_END");
            logOff(context);
        }
    }

    private void logOff(Context context) {
        AnalyzeLog.getInstance(context).logOff();
    }

    private void logOn(Context context) {
        AnalyzeLog.getInstance(context).logOn();
    }
}
