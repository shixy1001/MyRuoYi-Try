package com.sxy.task;

import com.alibaba.fastjson.JSONObject;
import com.sxy.utils.HttpClientUtil;

public class HttpClientTask {
    JSONObject jsonObject = new JSONObject();

    public JSONObject sendRequest(String url,String function,JSONObject jsonParm){
        if(function.equals("post")){//判断请求方式
            jsonObject = HttpClientUtil.httpPost(url,jsonParm);//执行没有token带参数的post请求
        }
        return jsonObject;
    }

    public JSONObject sendRequest(String url,String function,JSONObject pramar,String token){
        if(function.equals("post")){
            jsonObject = HttpClientUtil.httpPost(url,pramar,token);//执行有token带参数的post请求
        }
        else if(function.equals("get")){
            jsonObject = HttpClientUtil.httpGet(url, token);
        }
        else if(function.equals("delete")){
            jsonObject = HttpClientUtil.httpDelete(url,token);
        }
        return jsonObject;
    }


    public JSONObject sendRequest(String url,String function){
        if(function.equals("get") ){//判断请求方式
            jsonObject = HttpClientUtil.httpGet(url);//执行没有token带参数的post请求
        }
        return jsonObject;
    }
}
