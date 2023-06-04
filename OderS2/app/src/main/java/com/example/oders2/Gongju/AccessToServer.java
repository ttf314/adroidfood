package com.example.oders2.Gongju;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ZHG on 2017/01/25.
 */

public class AccessToServer {
    /**
     * 向服务器发送Get请求，获取响应结果
     * @param url     表示需要访问的资源网址;
     * @param names   表示需传递的多个参数名称集合;
     * @param values  表示传递的每个参数所对应的值;
     * @return        返回字符串（通常是JSON格式）
     */
    public String doGet(String url, String[] names, String[] values) {
        String result = "";// 保存返回结果
        if (names != null) {// 当有参数时，将参数拼接在地址后面，并用?隔开，参数间用&隔开
            url += "?";// 在网址后面添加？号
            for (int i = 0; i < names.length; i++) {// 循环遍历参数名和参数值，将其拼接
                url += names[i] + "=" + values[i];
                if (i != (names.length - 1)) {// 如果不是最后一个参数则添加&符号
                    url += "&";
                }
            }
        }
        System.out.println("url="+url);
        HttpClient httpClient = new DefaultHttpClient();// 创建HttpClient对象
        HttpGet httpGet = new HttpGet(url);// 创建HttpGet对象
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);// 发送请求获取响应
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 判断返回结果状态
                HttpEntity httpEntity = httpResponse.getEntity();// 获取返回的实体
                InputStream is = httpEntity.getContent();// 获取实体的内容
                result=readFromInputStream(is);//读取输入流中的内容
            } else {
                result = Constants.ERROR;//返回出错
            }
        } catch (Exception e) {//访问过程中抛出异常
            result =Constants.EXCEPTION;//返回异常
        }
        return result;//返回结果
    }
    /**
     * 向服务器发送Post请求，获取响应结果
     * @param url    表示需要访问的资源网址;
     * @param names  表示需传递的多个参数名称集合;
     * @param values 表示传递的每个参数所对应的值;
     * @return       返回字符串（通常是JSON格式）
     */
    public String doPost(String url, String[] names, String[] values) {
        String result = "";//保存返回结果
        HttpClient httpClient = new DefaultHttpClient();// 创建HttpClient对象
        HttpPost httpPost = new HttpPost(url);// 创建HttpPost对象
        if (names != null) {// 将传递的参数写入请求体中
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();// 创建键值对集合
            for (int i = 0; i < names.length; i++) {// 循环遍历参数名和参数值，将其关联起来，并添加到集合中去
                NameValuePair pair = new BasicNameValuePair(names[i], values[i]);//将关键字与对应的值关联起来
                params.add(pair);//将参数添加到集合中去
            }
            try {
                HttpEntity requestEntity = new UrlEncodedFormEntity(params,
                        "UTF-8");//创建请求实体，传递参数
                httpPost.setEntity(requestEntity);//将请求实体放入Post请求中去
            } catch (Exception ex) {//捕获异常
                result = Constants.EXCEPTION;//返回异常
            }
        }
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);// 发送post请求
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 判断返回结果状态
                HttpEntity httpEntity = httpResponse.getEntity();// 获取返回的实体
                InputStream is = httpEntity.getContent();// 获取实体的内容
                result=readFromInputStream(is);//读取输入流中的内容
            } else {
                result =Constants.ERROR;
            }
        } catch (Exception e) {//捕获异常
            result = Constants.EXCEPTION;//返回异常
        }
        return result;
    }
    private String readFromInputStream(InputStream is){//读取输入流中的内容
        String result="";//保存读取的内容
        byte[] buffer = new byte[1024];//定义缓存数组
        int hasRead = 0;//记录读取的字节数
        try {
            while ((hasRead = is.read(buffer)) != -1) {// 循环读取输入流中的内容，直到结束
                result += new String(buffer, 0, hasRead);//将读取的内容拼接成字符串
            }
        } catch (IOException e) {//读取过程中抛出异常
            result = Constants.EXCEPTION;//返回异常
        }
        return result;
    }
}
