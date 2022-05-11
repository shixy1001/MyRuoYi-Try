package com.sxy.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientUtil {
    /**
     * post请求
     * 发送json格式的请求
     * @param url 请求地址
     * @param jsonParm 请求参数
     */
    public static JSONObject httpPost(String url,JSONObject jsonParm){
        CloseableHttpClient httpClient = HttpClients.createDefault();//获取http客户端

        HttpPost post = new HttpPost(url);//创建HttpPost对象，将要请求的URL通过构造方法传入HttpPost对象。
        post.setHeader("content-type","application/json");

        StringEntity entity = new StringEntity(jsonParm.toString(),"UTF-8");//将参数放入实体
        post.setEntity(entity); //调用setEntity(HttpEntity entity)方法，将参数放入请求体。

        CloseableHttpResponse httpResponse = null;
        JSONObject jsonResult = null;
        String result = null;

        try {
            httpResponse = httpClient.execute(post);//客户端执行请求
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                //从响应模型中获取响应实体
               HttpEntity responseEntity = httpResponse.getEntity();
                result = EntityUtils.toString(responseEntity,"UTF-8");
                jsonResult = JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null != httpResponse){
                    httpResponse.close();
                }
                if(null != httpClient){
                    httpClient.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonResult;
    }

    /**
     * post请求，发送json格式的请求参数
     * @param url
     * @param jsonParm
     * @param token
     */
    public static JSONObject httpPost(String url,JSONObject jsonParm,String token){
        //创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httppost对象
        HttpPost post = new HttpPost(url);
        post.setHeader("content-type","application/json");
        post.setHeader("Authorization",token);
        //为httppost对象创建参数
        StringEntity entity = new StringEntity(jsonParm.toString(),"UTF-8");
        post.setEntity(entity);

        //定义相应对象和结果
        CloseableHttpResponse httpResponse = null;
        JSONObject jsonObject = null;
        String result = null;

        //发送请求，对请求结果进行处理
        try {
            httpResponse = httpClient.execute(post);//客户端发送请求
            if(httpResponse.getStatusLine().getStatusCode() == 200){//如果请求成功
                HttpEntity responseEntity = httpResponse.getEntity();//取得响应体
                result = EntityUtils.toString(responseEntity,"UTF-8");//将响应结果转字符串
                jsonObject = JSONObject.parseObject(result);//字符串转json格式
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
               if(null != httpResponse){
                    httpResponse.close();
                }
               if(null != httpClient){
                   httpClient.close();
               }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     *post请求,发生string格式参数,例如 name=jack&sex=1
     * Content-type:application/x-www-form-urlencoded
     * @param url
     * @param strParm
     */
     public static JSONObject httpPost(String url,String strParm,String token){
          CloseableHttpClient httpClient = HttpClients.createDefault();
          JSONObject jsonResult = null;

          //实例化
         HttpPost httpPost = new HttpPost(url);
         try {
         if(null !=strParm){
             StringEntity entity = new StringEntity(strParm,"UTF-8");//解决中文乱码
             entity.setContentEncoding("UTF-8");

             //传输过程类型
             entity.setContentType("application/x-www-form-urlencoded");
             httpPost.setEntity(entity);
         }
             CloseableHttpResponse result = httpClient.execute(httpPost);
           //请求成功并得到响应呢
             if(result.getStatusLine().getStatusCode()==200){
                 String str="";
                 try {
                     str = EntityUtils.toString(result.getEntity(),"utf-8");
                     jsonResult = JSONObject.parseObject(str);
                 }catch (Exception e){//打印捕获的异常
                     LoggerUtil.error("post请求失败："+e.getMessage());
                     //  e.printStackTrace();
                 }
             }
         } catch (IOException e) {
             LoggerUtil.error("post请求提交失败:"+e.getMessage());
             // e.printStackTrace();
         }finally {
             httpPost.releaseConnection();
         }
         return jsonResult;
     }

    /**
     * get请求
     * @param  url
     * @param token
     */
    public static JSONObject httpGet(String url,String token){
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet get = new HttpGet(url);
        get.setHeader("content-type","application/json");
        get.setHeader("Authorization", token);

        CloseableHttpResponse httpResponse = null;
        JSONObject jsonObject =null;
        String result = null;

        try {
            httpResponse = httpClient.execute(get);
            if(httpResponse.getStatusLine().getStatusCode() ==200){
                HttpEntity entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity,"UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                    if(null != httpResponse){
                         httpResponse.close();
                    }
                    if(null != httpClient){
                        httpClient.close();
                    }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static JSONObject httpGet(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet get = new HttpGet(url);
    //    get.setHeader("content-type","application/json");

        CloseableHttpResponse httpResponse = null;
        JSONObject jsonObject =null;
        String result = null;

        try {
            httpResponse = httpClient.execute(get);
            if(httpResponse.getStatusLine().getStatusCode() ==200){
                HttpEntity entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity,"UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null != httpResponse){
                    httpResponse.close();
                }
                if(null != httpClient){
                    httpClient.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static JSONObject httpDelete(String url,String token){
        CloseableHttpClient httpClient = HttpClients.createDefault();//创建客户端实例
        HttpDelete delete = new HttpDelete(url);

        delete.setHeader("content-type","application/json");
        delete.setHeader("Authorization",token);

        CloseableHttpResponse httpResponse = null;
        String result = null;
        JSONObject jsonResult = null;
        try {
            httpResponse = httpClient.execute(delete);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
            jsonResult = JSONObject.parseObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null != httpResponse){
                    httpResponse.close();
                }
                if(null != httpClient){
                    httpClient.close();
                }
            } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return jsonResult;
    }

    public static void main(String[] args) {
        JSONObject response = httpGet("http://192.168.16.121/dev-api/captchaImage");
        System.out.println(response.toJSONString());
    }
}
