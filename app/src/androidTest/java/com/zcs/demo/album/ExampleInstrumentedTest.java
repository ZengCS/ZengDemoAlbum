package com.zcs.demo.album;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.zcs.demo.album", appContext.getPackageName());
    }

    @Test
    public void getDate() {
        // 获取当天凌晨0点0分0秒Date
        Calendar c1 = Calendar.getInstance();
        c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        Date beginOfDate = c1.getTime();
        System.out.println(beginOfDate);
        long beginTime = c1.getTimeInMillis();
        System.out.println("beginTime = " + beginTime);

        // 获取当天23点59分59秒Date
        Calendar c2 = Calendar.getInstance();
        c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        // Date endOfDate = c2.getTime();
        long endTime = c2.getTimeInMillis();
        // System.out.println(endOfDate);
        System.out.println("endTime = " + endTime);
    }
}
