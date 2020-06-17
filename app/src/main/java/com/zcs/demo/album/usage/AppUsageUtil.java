package com.zcs.demo.album.usage;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zcs.demo.album.utils.JListKit;

import java.util.List;

/**
 * Created by ZengCS on 2019/5/30.
 * E-mail:zengcs@vip.qq.com
 * Add:成都市天府软件园E3
 */
public class AppUsageUtil {
    private static final String TAG = "AppUsageUtil";

    public static boolean hasAppUsagePermission(Context context) {
        UsageStatsManager usageStatsManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        }
        if (usageStatsManager == null) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        // try to get app usage state in last 2 min
        List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 2 * 60 * 1000, currentTime);
        return JListKit.isNotEmpty(stats);
    }

    public static void requestAppUsagePermission(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.i(TAG, "Start usage access settings activity fail!");
        }
    }
}
