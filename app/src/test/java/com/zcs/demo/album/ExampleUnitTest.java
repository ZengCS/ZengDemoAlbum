package com.zcs.demo.album;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.println("hello world");
        assertEquals(4, 2 + 2);
    }


    @Test
    public void getDate() {
        // 获取当天凌晨0点0分0秒Date
        Calendar c1 = Calendar.getInstance();
        c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        Date beginOfDate = c1.getTime();
        System.out.println("beginOfDate = " + beginOfDate);

        long todayTime0 = c1.getTimeInMillis();
        System.out.println("todayTime0 = " + todayTime0);
        System.out.println("endTime = " + (todayTime0 + 24 * 3600 * 1000));

        // 获取当天23点59分59秒Date
        Calendar c2 = Calendar.getInstance();
        c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        Date endOfDate = c2.getTime();
        System.out.println("endOfDate = " + endOfDate);

        long endTime = c2.getTimeInMillis();
        System.out.println("endTime = " + endTime);

        System.out.println("Time gap = " + (endTime - todayTime0));
    }
}