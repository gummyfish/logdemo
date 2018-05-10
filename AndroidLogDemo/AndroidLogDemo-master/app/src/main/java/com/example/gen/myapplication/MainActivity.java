package com.example.gen.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private static final String TAG = AnalyzeLog.class.getSimpleName();
    /**
     * 模拟发送短信按钮
     * @param savedInstanceState
     */
    private Button mButton;

    /**
     * 模拟log开关状态切换
     */
    private Button mBt_logon, mBt_logoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        mButton = findViewById(R.id.bt_send);
        mButton.setOnClickListener(mBtListener);
        mBt_logon = findViewById(R.id.bt_logon);
        mBt_logoff = findViewById(R.id.bt_logoff);
        mBt_logon.setOnClickListener(mBtListener);
        mBt_logoff.setOnClickListener(mBtListener);
    }

    private View.OnClickListener mBtListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_send:
                    sendMsg();
                    break;
                case R.id.bt_logon:
                    sendBroadCast(true);
                    break;
                case R.id.bt_logoff:
                    sendBroadCast(false);
                    break;
                default:
                    break;
            }
        }
    };

    private void sendBroadCast(boolean flag) {
        String action = flag ? LogReceiver.ACTION_BEGIN : LogReceiver.ACTION_END;
        Intent intent = new Intent(action);
        sendBroadcast(intent, LogReceiver.PERMISSION);
    }

    private void sendMsg() {
        Log.d(TAG, "sending msg...");
        AnalyzeLog.getInstance(this).d("sending msg!!!");
    }
}
