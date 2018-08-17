package chapter9.class1.httpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**  Created by xiaoxiaojing on 2018/8/17.  */

public class class3Properties {

    private String url;  //定义url，然后从配置文件中读取url
    private ResourceBundle bundle;  //定义ResourceBundle对象，用来读取配置文件数据
    private CookieStore store;  //定义CookieStore对象，用来存储cookies信息

    @BeforeClass
    public void beforeClass(){
        //getBundle默认识别properties后缀文件，所以后缀可省略
        bundle=ResourceBundle.getBundle("hxj/application", Locale.CHINA);
        url=bundle.getString("test.url1");
    }

    @Test
    public void test1() throws IOException {

        //从配置文件中，提取并拼接url
        String getcookiesURi=bundle.getString("test.getCookies.uri");
        String getcookiesURL=this.url+getcookiesURi;

        //获取body
        String result;
        HttpGet get=new HttpGet(getcookiesURL);
        //HttpClient对象无法获取cookies，所以此处改为DefaultHttpClient
        DefaultHttpClient client=new DefaultHttpClient();
        HttpResponse response=client.execute(get);
        result=EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //获取cookies信息
        store=client.getCookieStore();
        List<Cookie> cookieList = store.getCookies();

        for(Cookie cookie:cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println(name+"="+value);
        }
    }

    @Test(dependsOnMethods = {"test1"})
    public void test2() throws IOException {
        String uri = bundle.getString("test.get.with.cookies");
        String getWithCookiesURL = this.url+uri;
        HttpGet get = new HttpGet(getWithCookiesURL);
        DefaultHttpClient client = new DefaultHttpClient();

        //设置cookies信息
        client.setCookieStore(store);
        HttpResponse response = client.execute(get);

        //获取响应的状态码
        int status = response.getStatusLine().getStatusCode();
        System.out.println("status="+status);

        if(status == 200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }
        else{
            System.out.println("访问"+getWithCookiesURL+"接口失败");
        }

    }
}
























