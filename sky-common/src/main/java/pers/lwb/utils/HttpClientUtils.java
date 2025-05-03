package pers.lwb.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import pers.lwb.exception.HttpClientException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class HttpClientUtils {

    /**
     * 发送 GET 请求
     * @param url 请求域名
     * @param entry 请求实体
     * @return 响应结果
     */
    public static String httpGet(String url, Map<String, String> entry) {
        CloseableHttpClient client = HttpClients.createDefault();

        String res = null;
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            entry.forEach(builder::addParameter);
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200)
                res = EntityUtils.toString(response.getEntity());
        } catch (URISyntaxException | IOException e) {
            throw new HttpClientException(e.getMessage());
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                throw new HttpClientException(e.getMessage());
            }
        }
        return res;
    }

    /**
     * 发送 POST 请求
     * @param url 域名
     * @param entity 实体
     * @return 响应结果
     * @param <T> 实体类型
     */
    public static <T> String httpPost4Json(String url, T entity) {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        String jsonString = JSONObject.toJSONString(entity);
        StringEntity requestEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);

        String res = null;
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            res = EntityUtils.toString(responseEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                throw new HttpClientException(e.getMessage());
            }
        }
        return res;
    }

    /**
     * 发送 POST 请求
     * @param url 域名
     * @param entry 实体
     * @return 响应结果
     */
    public static String httpPost(String url, Map<String, String> entry) {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> nvps = new ArrayList<>();
        entry.forEach((key, value) -> nvps.add(new BasicNameValuePair(key, value)));

        String res = null;
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
            response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            res = EntityUtils.toString(responseEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                throw new HttpClientException(e.getMessage());
            }
        }
        return res;
    }
}










