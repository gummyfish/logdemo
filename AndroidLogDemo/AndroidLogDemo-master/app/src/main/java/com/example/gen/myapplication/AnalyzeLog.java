package com.example.gen.myapplication;

import android.content.Context;
// import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
// import android.preference.PreferenceManager;
import android.util.Log;
import org.jetbrains.annotations.NotNull;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * write log to /storage/emulated/0/Android/data/com.example.gen.myapplication/files/analyze.log
 * use background thread.
 *
 * API:
 * write log : AnalyzeLog.getInstance(context).d("xxx");
 * switch log on : AnalyzeLog.getInstance(context).logOn();
 * switch log off : AnalyzeLog.getInstance(context).logOff();
 */
public class AnalyzeLog {

    private static final String TAG = AnalyzeLog.class.getSimpleName();

    // private static final String KEY_ANALYZE_LOG_ON = "analyze_log_on";
    private static final String THREAD_NAME = "analyze_log_thread";
    private static final String ANALYZE_FILE_NAME = "analyze.log";
    private static final String NEW_LINE_SEPERATOR = "\r\n";
    // private SharedPreferences mSharedPreference;
    private Context mContext;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private boolean mLogon;

    private static AnalyzeLog mInstance;

    private AnalyzeLog(@NotNull Context context) {
        // mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        // boolean logOn = mSharedPreference.getBoolean(KEY_ANALYZE_LOG_ON, false);
        mContext = context.getApplicationContext();
        if (mLogon) {
            initThread();
        }
    }

    private void initThread() {
        mHandlerThread = new HandlerThread(THREAD_NAME);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public static AnalyzeLog getInstance(@NotNull Context context) {
        if (mInstance == null) {
            synchronized (AnalyzeLog.class) {
                if (mInstance == null) {
                    mInstance = new AnalyzeLog(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * open analyze log switch.
     * start the thread.
     */
    public void logOn() {
        // mSharedPreference.edit().putBoolean(KEY_ANALYZE_LOG_ON, true).apply();
        mLogon = true;
        if (mHandlerThread == null) {
            initThread();
        }
    }

    /**
     * close analyze log switch.
     * quit the thread.
     */
    public void logOff() {
        // mSharedPreference.edit().putBoolean(KEY_ANALYZE_LOG_ON, false).apply();
        mLogon = false;
        deleteFile();
        if (mHandlerThread != null) {
            mHandlerThread.quitSafely();
            mHandlerThread = null;
            mHandler = null;
        }
    }

    private void deleteFile() {
        // File logFile = new File(mContext.getDir(ANALYZE_FILE_NAME,Context.MODE_PRIVATE), ANALYZE_FILE_NAME);
        final File logFile = new File(mContext.getExternalFilesDir(null), ANALYZE_FILE_NAME);
        if (logFile != null && logFile.exists() && mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    logFile.delete();
                }
            });
        }
    }

    /**
     * write msg to ANALYZE_FILE_NAME file.
     * @param msg
     */
    public void d(final String msg) {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    writeFile(msg);
                }
            });
        } else {
            Log.d(TAG, "log off ignore msg : " + msg);
        }
    }

    private void writeFile(final String msg) {
        Log.d(TAG, "log on write msg : " + msg);
        FileOutputStream fos = null;
        try {
            // fos = mContext.openFileOutput(ANALYZE_FILE_NAME, Context.MODE_APPEND);
            fos = new FileOutputStream(
                    new File(mContext.getExternalFilesDir(null), ANALYZE_FILE_NAME) ,true);
            fos.write(msg.getBytes());
            fos.write(NEW_LINE_SEPERATOR.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fos);
        }
    }

    private void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
