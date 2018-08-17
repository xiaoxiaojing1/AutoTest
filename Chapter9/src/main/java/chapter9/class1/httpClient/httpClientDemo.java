package chapter9.class1.httpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

/**  Created by xiaoxiaojing on 2018/8/16.  */

public class httpClientDemo {
    @Test
    public void test1() throws IOException {

        //用来存放响应结果
        String result;

        HttpGet get=new HttpGet("http://www.baidu.com");
        //client用来执行get方法
        HttpClient client=new DefaultHttpClient();

        HttpResponse response=client.execute(get);
        result=EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
    }
}




