package com.example.lc.materialuitest;

import com.example.lc.materialuitest.util.DateUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class DateUtilTest {

    private String time = "2017-10-15 16:00:02";

    private long timeStamp = 1508054402000L;

    private Date date;

    @Before
    public void setUp() throws Exception{
        System.out.println("测试开始");
        date = new Date();
        date.setTime(timeStamp);
    }

    @After
    public void tearDown() throws Exception{
        System.out.println("测试结束");
    }

    @Test(expected = ParseException.class)
    public void stampToDateTest() throws Exception{
        Assert.assertEquals(time, DateUtil.stampToDate(timeStamp));
    }

}
