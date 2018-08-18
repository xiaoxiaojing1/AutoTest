package chapter9.class1.httpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/** Created by xiaoxiaojing on 2018/8/18.  */

public class class5PostWithCookies {

    private String url;
    private ResourceBundle boundle;
    private CookieStore cookieStore;  //用来存储cookies信息的变量

    @BeforeClass
    public void beforeClass(){
        boundle = ResourceBundle.getBundle("hxj/application",Locale.CHINA);
        url = boundle.getString("test.url1");
    }

    @Test
    public void getCookies() throws IOException {

        String uri = url + boundle.getString("test.getCookies.uri");//从配置文件中拼接测试的url

        HttpGet get = new HttpGet(uri);
        DefaultHttpClient client = new DefaultHttpClient();

        HttpResponse response = client.execute(get);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        cookieStore = client.getCookieStore();  //获取cookies信息
        List<Cookie> cookies = cookieStore.getCookies();

        for(Cookie cookie:cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookies:"+name+"="+value);
        }

    }

    @Test(dependsOnMethods = {"getCookies"})
    public void postWithCookies() throws IOException {

        String uri = this.url + boundle.getString("test.post.uri");//从配置文件中拼接测试的url
        HttpPost post = new HttpPost(uri);  //声明一个post方法

        post.setHeader("Content-type","application/json");  //设置header

        //添加参数
        JSONObject param =new JSONObject();
        param.put("name","xiaojing");
        param.put("age","27");

        //将参数信息添加到post方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        DefaultHttpClient client = new DefaultHttpClient();  //声明一个Client对象，用来执行方法
        client.setCookieStore(cookieStore);  //设置cookies信息
        HttpResponse response = client.execute(post);  //执行post方法

        //响应结果的存储
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //处理结果：判断返回结果是否符合预期
        JSONObject jsonresult = new JSONObject(result);  //将响应结果由字符串转化成为json对象
        String name = (String) jsonresult.get("xiaojing");  //获取到结果值
        String status = (String) jsonresult.get("status");  //获取到结果值
        Assert.assertEquals("true",name);  //判断返回结果是否符合预期
        Assert.assertEquals("1",status);   //判断返回结果是否符合预期

    }
}
