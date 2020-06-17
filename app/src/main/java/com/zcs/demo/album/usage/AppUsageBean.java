package com.zcs.demo.album.usage;

import android.app.usage.UsageStats;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by ZengCS on 2019/5/30.
 * E-mail:zengcs@vip.qq.com
 * Add:成都市天府软件园E3
 */
public class AppUsageBean implements Serializable, Comparable<AppUsageBean> {
    private String packageName;
    private ApplicationInfo appInfo;
    private UsageStats usageStats;

    private Drawable appIcon;
    private String appName;

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public long getTotalTimeInForeground() {
        if (usageStats != null)
            return usageStats.getTotalTimeInForeground();
        else
            return 0L;
    }

    public long getLastTimeUsed() {
        if (usageStats != null)
            return usageStats.getLastTimeUsed();
        return 0L;
    }

    public AppUsageBean() {
    }

    public AppUsageBean(String packageName) {
        this.packageName = packageName;
    }

    public AppUsageBean(String packageName, UsageStats usageStats) {
        this.packageName = packageName;
        this.usageStats = usageStats;
    }

    public AppUsageBean(String packageName, ApplicationInfo appInfo) {
        this.packageName = packageName;
        this.appInfo = appInfo;
    }

    public AppUsageBean(String packageName, ApplicationInfo appInfo, UsageStats usageStats) {
        this.packageName = packageName;
        this.appInfo = appInfo;
        this.usageStats = usageStats;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public ApplicationInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(ApplicationInfo appInfo) {
        this.appInfo = appInfo;
    }

    @NonNull
    public UsageStats getUsageStats() {
        return usageStats;
    }

    public void setUsageStats(@NonNull UsageStats usageStats) {
        this.usageStats = usageStats;
    }

    @Override
    public int compareTo(@NonNull AppUsageBean o) {
        try {
            long l = o.getTotalTimeInForeground() - getTotalTimeInForeground();
            l /= 1000;
            if (l >= Integer.MAX_VALUE)
                l = Integer.MAX_VALUE - 1;
            return (int) (l);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
