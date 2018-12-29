package com.sunfusheng;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.sunfusheng.daemon.AbsHeartBeatService;
import com.sunfusheng.daemon.DaemonHolder;
import com.sunfusheng.daemon.DaemonService;
import com.sunfusheng.daemon.JobSchedulerService;
import com.sunfusheng.daemon.R;
import com.sunfusheng.service.AuthenticatorService;
import com.sunfusheng.service.SyncService;

import static android.content.Context.ACCOUNT_SERVICE;

/**
 * 保活相关控制
 * <p>
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2018/12/26
 */
public class RDaemon {

    static String ACCOUNT_TYPE = "com.wayto.saas";
    static String CONTENT_AUTHORITY = "com.wayto.saas.provider";


    public static void init(@NonNull Context context, Class<? extends AbsHeartBeatService> service) {
        RDaemon.enableComponent(context, service.getName());
        RDaemon.enableComponent(context, DaemonService.class.getName());
        RDaemon.enableComponent(context, JobSchedulerService.class.getName());
        RDaemon.enableComponent(context, SyncService.class.getName());
        RDaemon.enableComponent(context, AuthenticatorService.class.getName());

        DaemonHolder.init(context, service);
        addAccount(context);
    }

    /**
     * 启用账户自动同步
     */
    private static void addAccount(Context context) {
        //添加账号

        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        Account account = null;

        Account[] accountsByType = accountManager.getAccountsByType(ACCOUNT_TYPE);

        if (accountsByType.length > 0) {
            account = accountsByType[0];
        } else {
            account = new Account(context.getResources().getString(R.string.app_name), ACCOUNT_TYPE);
        }

        if (accountManager.addAccountExplicitly(account, null, null)) {

            //开启同步，设置同步周期  30秒
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY, Bundle.EMPTY, 30);
        }
    }

    /**
     * 激活组件
     */
    public static void enableComponent(@NonNull Context context, String cls) {
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context.getPackageName(), cls),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
}
