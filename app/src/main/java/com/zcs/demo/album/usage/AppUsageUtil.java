package com.zcs.demo.album.usage;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.zcs.demo.album.utils.JListKit;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by ZengCS on 2019/5/30.
 * E-mail:zcs@sxw.cn
 * Add:成都市天府软件园E3-3F
 */
public class AppUsageUtil {
    private static final String TAG = "AppUsageUtil";
    private static final String PACKAGE_NAME_UNKNOWN = "PACKAGE_NAME_UNKNOWN";

    public static void checkUsageStateAccessPermission(Context context) {
        if (!AppUsageUtil.checkAppUsagePermission(context)) {
            AppUsageUtil.requestAppUsagePermission(context);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static String getTopActivityPackageName(@NonNull Context context) {
        final UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        if (usageStatsManager == null) {
            return PACKAGE_NAME_UNKNOWN;
        }

        String topActivityPackageName = PACKAGE_NAME_UNKNOWN;
        long time = System.currentTimeMillis();
        // 查询最后十秒钟使用应用统计数据
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
        // 以最后使用时间为标准进行排序
        if (usageStatsList != null) {
            SortedMap<Long, UsageStats> sortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : usageStatsList) {
                sortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
            if (sortedMap.size() != 0) {
                topActivityPackageName = sortedMap.get(sortedMap.lastKey()).getPackageName();
                Log.d(TAG, "Top activity package name = " + topActivityPackageName);
            }
        }

        return topActivityPackageName;
    }

    public static boolean checkAppUsagePermission(Context context) {
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
