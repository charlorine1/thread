package com.usst.httpDemo.t1;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;

public class HuToolHttpDemo {

    public static void main(String[] args) {
        JSONObject j = new JSONObject();
        j.putOpt("a","AAAA");
        j.putOpt("b","BBBB");
        j.putOpt("c","CCCC");

        System.out.println(j.toString());

/*        HttpRequest post = HttpRequest.post("www.baidu.com")
                .body("11111");
        HttpResponse execute = post.execute();
        int status = execute.getStatus();*/


    }


}
