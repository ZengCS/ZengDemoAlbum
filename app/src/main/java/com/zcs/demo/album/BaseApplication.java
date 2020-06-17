package com.zcs.demo.album;

import android.app.Application;
import android.content.Context;

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