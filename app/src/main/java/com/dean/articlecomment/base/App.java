package com.dean.articlecomment.base;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DeanGuo on 9/1/16.
 */
public class App extends Application{

    private static App instance;
    private Set<Activity> allActivities;

    public static synchronized App getInstance() {
        return instance;
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化日志
        Logger.init(getPackageName()).hideThreadInfo();

        //初始化tbs x5 webview
        QbSdk.allowThirdPartyAppDownload(true);
        QbSdk.initX5Environment(getApplicationContext(), QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, null);
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
