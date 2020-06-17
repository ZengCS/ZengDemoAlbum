package com.zcs.demo.album;

import android.app.Application;
import android.content.Context;

/**
 * Created by ZengCS on 2019/5/30.
 * E-mail:zengcs@vip.qq.com
 * Add:成都市天府软件园E3
 */
public class BaseApplication extends Application {
    private static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        mApplication = null;
    }

    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getApplication() {
        return mApplication;
    }
}