package com.usst.httpDemo.t1;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class Test1 {

    public static void main(String[] args) throws Exception{

        HttpClient httpClient = new DefaultHttpClient();

        HttpUriRequest request = new HttpGet("http://www.baidu.com");

/*        String method = request.getMethod();
        System.out.println("method = " + method);*/

    /*    Header header =
        request.setHeader(header);*/
        HttpResponse response = null;
        InputStream content = null;

        try {
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            content = entity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes = new byte[1024];
        String str = "";
        while ((content.read(bytes)) != -1){
            str = new String(bytes, "UTF-8");
        }

        System.out.println(str);

    }

}
