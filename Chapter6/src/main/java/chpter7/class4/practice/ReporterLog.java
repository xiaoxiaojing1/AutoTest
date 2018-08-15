package chpter7.class4.practice;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**  Created by xiaoxiaojing on 2018/8/15. */

public class ReporterLog {

    @Test
    public void test3(){
        Assert.assertEquals(1,2);
    }

    @Test
    public void test1(){
        Assert.assertEquals(1,2);
    }

    @Test
    public void test2(){
        Assert.assertEquals("aaa","aaa");
    }

    @Test
    public void ReportLog(){
        Assert.assertEquals(1,1);
        Reporter.log("这是我自己写的log啦啦啦啦");
        throw new RuntimeException("这是我自己抛出的异常啊啦啦啦啦");
    }

}
