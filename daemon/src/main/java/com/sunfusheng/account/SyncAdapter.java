package com.sunfusheng.account;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.sunfusheng.daemon.DaemonHolder;


/**
 * Created by Fussen on 2017/2/23.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {


    private Context mContext;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.mContext = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        DaemonHolder.startService();
    }
}
