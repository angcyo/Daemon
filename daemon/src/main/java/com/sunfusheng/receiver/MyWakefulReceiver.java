package com.sunfusheng.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.sunfusheng.daemon.DaemonHolder;

/**
 * Created by Fussen on 2017/2/22.
 * <p>
 * 唤醒锁 用来唤醒CPU
 */

public class MyWakefulReceiver extends WakefulBroadcastReceiver {

    static final String TAG = "[MyWakefulReceiver]";

    @Override
    public void onReceive(Context context, Intent intent) {
        DaemonHolder.startService();
    }
}
