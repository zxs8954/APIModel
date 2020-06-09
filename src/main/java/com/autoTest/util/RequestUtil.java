package com.autoTest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestUtil {
    public static String getMethod = "get";
    public static String postMethod = "post";
    private static CloseableHttpClient client = null;
    private static HttpPost post = null;
    private static HttpGet get = null;
    private static String result = null;
    private static CloseableHttpResponse response = null;
    private static Logger log = Logger.getLogger(RequestUtil.class);

    public static String doRequest(String url, Map<String, String> map, String method) {
        client = HttpClients.createDefault();
        //get请求
        if (method.equals(getMethod)) {
            try {
                URIBuilder uriBuilder = new URIBuilder(url);
                for (String key : map.keySet()) {
                    uriBuilder.setParameter(key, map.get(key));
                }
                get = new HttpGet(uriBuilder.build());
                log.info("请求地址" + get + "\r" + "请求方法:" + method + "\r" + "请求参数:" + JSON.toJSONString(map));
                try {
                    response = client.execute(get);
                    //解析响应
                    if (response.getStatusLine().getStatusCode() == 200) {
                        result = EntityUtils.toString(response.getEntity(), "utf-8");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (method.equals(postMethod)) {
            List<NameValuePair> param = null;
            UrlEncodedFormEntity encodedFormEntity = null;
            post = new HttpPost(url);
            log.info("\r" + "请求地址" + post + "\r" + "请求方法:" + method + "\r" + "请求参数:" + JSON.toJSONString(map));
            for (String key : map.keySet()) {
                param = new ArrayList<NameValuePair>();
                param.add(new BasicNameValuePair(key,String.valueOf( map.get(key))));
            }
            try {
                encodedFormEntity = new UrlEncodedFormEntity(param, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            post.setEntity(encodedFormEntity);
            try {
                response = client.execute(post);
                //解析响应
                if (response.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(response.getEntity(), "utf-8");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            log.info("请检查参数");
        }
        String s = jsonFormat(result);
        log.info(s);
        return s;
    }


    //JSON格式化
    private static String jsonFormat(String str) {
        JSONObject object = JSONObject.parseObject(str);
        return JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);

    }
}
