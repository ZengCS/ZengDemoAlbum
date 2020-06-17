package com.zcs.demo.album.usage;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.zcs.demo.album.BaseApplication;
import com.zcs.demo.album.BuildConfig;
import com.zcs.demo.album.utils.JDateKit;
import com.zcs.demo.album.utils.JListKit;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by ZengCS on 2019/5/30.
 * E-mail:zengcs@vip.qq.com
 * Add:成都市天府软件园E3
 * 加载应用使用情况 Task
 */
public class LoadAppUsageTask extends AsyncTask<Void, Void, ArrayList<AppUsageBean>> {
    private static final String TAG = "LoadAppUsageTask";
    private Callback mCallback;
    private long beginTime, endTime;

    public LoadAppUsageTask(long beginTime, long endTime, Callback mCallback) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.mCallback = mCallback;
        mPackageManager = BaseApplication.getApplication().getPackageManager();
    }

    // 方法1：onPreExecute（）
    // 作用：执行 线程任务前的操作
    @Override
    protected void onPreExecute() {
        // 执行前显示提示
        Log.d(TAG, "********** 开始获取应用使用情况 **********");
    }

    // 方法2：doInBackground（）
    // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
    // 此处通过计算从而模拟“加载进度”的情况

    @Override
    protected ArrayList<AppUsageBean> doInBackground(Void... voids) {
        return readAppUsageList();
    }

    private ArrayList<AppUsageBean> readAppUsageList() {
        ArrayList<AppUsageBean> mItems = JListKit.newArrayList();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            UsageStatsManager usage = (UsageStatsManager) BaseApplication.getApplication().getSystemService(Context.USAGE_STATS_SERVICE);
            if (usage == null) return mItems;

            // 查询并按包名进行聚合操作
            Map<String, UsageStats> statsMap = usage.queryAndAggregateUsageStats(beginTime, endTime);
            Set<String> keySet = statsMap.keySet();
            for (String packageName : keySet) {
                UsageStats usageStats = statsMap.get(packageName);
                if (usageStats == null) continue;
                long totalTimeInForeground = usageStats.getTotalTimeInForeground();
                if (totalTimeInForeground <= 0) continue;// 小于1秒的都按照没有打开过处理

                AppUsageBean appUsageBean = new AppUsageBean(packageName, usageStats);
                ApplicationInfo info = getAppInfo(packageName);
                appUsageBean.setAppInfo(info);
                if (info != null) {
                    // 获取应用名称
                    String label = (String) info.loadLabel(mPackageManager);
                    Drawable icon = info.loadIcon(mPackageManager);
                    appUsageBean.setAppName(label);
                    appUsageBean.setAppIcon(icon);
                } else {
                    appUsageBean.setAppName("应用已卸载");
                    // Log.e(TAG, "已经找不到包名为[" + packageName + "]的应用");
                }

                mItems.add(appUsageBean);
                // 打印日志
                if (BuildConfig.DEBUG) {
                    Log.d("UsageStats", "**********************************************");
                    Log.d("UsageStats", packageName);
                    // Log.d("UsageStats", "运行时长:" + JDateKit.timeToStringChineChinese(totalTimeInForeground));
                    Log.d("UsageStats", String.format("运行时长:%s (%sms)", JDateKit.timeToStringChineChinese(totalTimeInForeground), totalTimeInForeground));
                    String fmt = "yyyy-MM-dd HH:mm:ss.SSS";
                    Log.d("UsageStats", "开始启动:" + JDateKit.timeToDate(fmt, usageStats.getFirstTimeStamp()));
                    Log.d("UsageStats", "最后启动:" + JDateKit.timeToDate(fmt, usageStats.getLastTimeStamp()));
                    Log.d("UsageStats", "最近使用:" + JDateKit.timeToDate(fmt, usageStats.getLastTimeUsed()));
                }
            }
        }
        return mItems;
    }

    private PackageManager mPackageManager;

    private ApplicationInfo getAppInfo(String pkgName) {
        try {
            // ApplicationInfo info = mPackageManager.getApplicationInfo(pkgName, PackageManager.GET_ACTIVITIES);
            return mPackageManager.getApplicationInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            // e.printStackTrace();
            Log.e(TAG, "已经找不到包名为[" + pkgName + "]的应用");
        }
        return null;
    }

    // 方法3：onPostExecute（）
    // 作用：接收线程任务执行结果、将执行结果显示到UI组件
    @Override
    protected void onPostExecute(ArrayList<AppUsageBean> list) {
        Log.d(TAG, "共获取到[" + list.size() + "]个系统应用。");
        if (mCallback != null)
            mCallback.onPostExecute(list);
    }

    public interface Callback {
        void onPostExecute(ArrayList<AppUsageBean> list);
    }
}
