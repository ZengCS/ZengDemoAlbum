package com.zcs.demo.album.usage;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.zcs.demo.album.R;
import com.zcs.demo.album.base.BaseActivity;
import com.zcs.demo.album.base.CommonRecyclerAdapter;
import com.zcs.demo.album.databinding.ActivityAppUsageBinding;
import com.zcs.demo.album.utils.JDateKit;
import com.zcs.demo.album.utils.JListKit;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZengCS on 2019/5/30.
 * E-mail:zengcs@vip.qq.com
 * Add:成都市天府软件园E3
 */
public class AppUsageActivity extends BaseActivity {
    private static final String TAG = "AppUsageActivity";
    private ActivityAppUsageBinding mHolder;
    private List<AppUsageBean> mItems;
    private CommonRecyclerAdapter<AppUsageBean> mAdapter;
    private static final String[] TAB_NAMES = {"今日数据", "昨日数据", "本周数据", "本月数据", "年度数据"};
    private boolean isGoToGrand = false;// 是否去过授权页面

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定UI
        mHolder = ActivityAppUsageBinding.inflate(getLayoutInflater());
        setContentView(mHolder.getRoot());

        // 初始化Tab
        int c = 0;
        TabLayout tabLayout = mHolder.tabCondition;
        for (String name : TAB_NAMES) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setTag(c);
            tab.view.setOnClickListener(v -> onTabClick((int) tab.getTag()));
            tabLayout.addTab(tab.setText(name));
            c++;
        }

        // 授权|加载数据
        initData();
    }

    private void initData() {
        if (AppUsageUtil.hasAppUsagePermission(this)) {
            // 默认加载今天的数据
            isGoToGrand = false;
            onTabClick(0);
        } else {
            isGoToGrand = true;
            // TODO 这里有点强制开启的意思，实际应用中最好弹出一个对话框让用户知道，并可以选择【授权】或【退出】
            AppUsageUtil.requestAppUsagePermission(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGoToGrand) {// 如果从应用跳转到了授权，那么返回应用的时候 需要重新执行一次
            initData();
        }
    }

    public void onTabClick(int position) {
        Log.d(TAG, "onTabClick() called with: position = [" + position + "]");

        setTitle(TAB_NAMES[position]);
        long currTime = System.currentTimeMillis();

        switch (position) {
            case 0:// 今天的数据  00:00 到 现在
                getAppUsage(getTodayTime0(), currTime);
                break;
            case 1:// 昨天的数据  昨天00:00 - 今天00:00
                long todayTime0 = getTodayTime0();
                getAppUsage(todayTime0 - DateUtils.DAY_IN_MILLIS, todayTime0);
                break;
            case 2:// 最近7天数据
                getAppUsage(currTime - DateUtils.WEEK_IN_MILLIS, currTime);
                break;
            case 3:// 最近30天数据
                getAppUsage(currTime - DateUtils.DAY_IN_MILLIS * 30, currTime);
                break;
            case 4:// 最近一年的数据
                getAppUsage(currTime - DateUtils.DAY_IN_MILLIS * 365, currTime);
                break;
        }
    }

    /**
     * @return 今日零点的时间
     */
    private long getTodayTime0() {
        // 获取今天凌晨0点0分0秒的time
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        return calendar.getTimeInMillis();
    }

    private LoadAppUsageTask mLoadAppUsageTask;

    private void getAppUsage(long beginTime, long endTime) {
        String fmt = "yyyy-MM-dd HH:mm:ss";
        mHolder.tvTimeRange.setText(String.format("(%s - %s)",
                JDateKit.timeToDate(fmt, beginTime),
                JDateKit.timeToDate(fmt, endTime)));
        // setTitle("数据分析中...");
        showLoading("数据分析中...");

        // a task can be executed only once,init is required every time
        mLoadAppUsageTask = new LoadAppUsageTask(beginTime, endTime, list -> {
            mItems = list;
            initAdapter();
        });
        mLoadAppUsageTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadAppUsageTask != null) {
            mLoadAppUsageTask.cancel(true);
            mLoadAppUsageTask = null;
        }
    }

    private long maxTime;// 当前列表中 使用最久的APP时间 用于计算进度条百分比

    private void initAdapter() {
        if (JListKit.isNotEmpty(mItems)) {
            Collections.sort(mItems);// 按使用时长排序
            maxTime = mItems.get(0).getTotalTimeInForeground();
        } else {
            maxTime = 1;
        }
        setTitle(String.format("%s (共%s条)", getTitle(), mItems.size()));
        if (mAdapter == null) {
            String fmt = "yyyy-MM-dd HH:mm:ss";
            mAdapter = new CommonRecyclerAdapter<AppUsageBean>(R.layout.item_app_usage, mItems) {
                @Override
                protected void convert(@NonNull BaseViewHolder helper, AppUsageBean item) {
                    helper.setText(R.id.id_tv_app_name, String.format("%s(%s)", item.getAppName(), item.getPackageName()));
                    Drawable appIcon = item.getAppIcon();
                    if (appIcon != null) {
                        helper.setImageDrawable(R.id.id_iv_app_icon, appIcon);
                    } else {
                        helper.setImageResource(R.id.id_iv_app_icon, R.mipmap.ic_launcher);
                    }
                    long totalTimeInForeground = item.getTotalTimeInForeground();
                    helper.setText(R.id.id_tv_time_in_foreground, String.format("使用时长:%s (%sms)", JDateKit.timeToStringChineChinese(totalTimeInForeground), totalTimeInForeground));
                    helper.setText(R.id.id_tv_last_usage, String.format("上次使用:%s", JDateKit.timeToDate(fmt, item.getLastTimeUsed())));
                    // 计算进度条百分比
                    float percent = (float) item.getTotalTimeInForeground() / maxTime;
                    Guideline guideline = helper.getView(R.id.guideline);
                    guideline.setGuidelinePercent(percent);
                }
            };
            mHolder.rvAppUsage.setAdapter(mAdapter);
            mHolder.rvAppUsage.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mAdapter.setNewInstance(mItems);
        }
        hideLoading();
    }
}
