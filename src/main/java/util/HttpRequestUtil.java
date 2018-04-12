package util;

import javafx.util.Pair;
import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by YY on 2017-12-27.
 */
public class HttpRequestUtil {

    private static Pair<Integer, String> httpRequest(String method, String strUrl, JSONObject jsonObject) {
        Integer responseCode = 0;
        String response = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(true);
            conn.setInstanceFollowRedirects(true);
            // 设置请求头
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // 维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.connect();
            // 设置请求体
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(jsonObject.toString());
            out.flush();
            out.close();
            // 获取返回结果
            responseCode = conn.getResponseCode();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                response = response + readLine;
            }
            is.close();
            br.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair(responseCode, response);
    }

    private static Pair<Integer, String> httpRequest(String method, String strUrl) {
        Integer responseCode = 0;
        String response = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setUseCaches(true);
            conn.setInstanceFollowRedirects(true);
            //设置请求头
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.connect();
            //获取返回结果
            responseCode = conn.getResponseCode();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                response = response + readLine;
            }
            is.close();
            br.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair(responseCode, response);
    }

    private static Pair<Integer, String> httpRequestReHeader(String method, String strUrl, String headKey) {
        Integer responseCode = 0;
        String responseHead = "";
        String response = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setUseCaches(true);
            conn.setInstanceFollowRedirects(true);
            //设置请求头
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.connect();
            //获取返回结果
            responseCode = conn.getResponseCode();
            //获取返回头
            responseCode = conn.getResponseCode();
            responseHead = conn.getHeaderField(headKey);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair(responseCode, responseHead);
    }

    private static Pair<Integer, String> httpRequestReHeader(String method, String strUrl, JSONObject jsonObject, String headKey) {
        Integer responseCode = 0;
        String responseHead = "";
        String response = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(true);
            conn.setInstanceFollowRedirects(true);
            //设置请求头
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.connect();
            //设置请求体
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(jsonObject.toString());
            out.flush();
            out.close();
            //获取返回头
            responseCode = conn.getResponseCode();
            responseHead = conn.getHeaderField(headKey);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair(responseCode, responseHead);
    }

    private static Pair<Integer, String> httpRequest(String method, String strUrl, String xmlStr) {
        Integer responseCode = 0;
        String response = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(true);
            conn.setInstanceFollowRedirects(true);
            //设置请求头
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.connect();
            //设置请求体
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(xmlStr);
            out.flush();
            out.close();
            //获取返回结果
            responseCode = conn.getResponseCode();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                response = response + readLine;
            }
            is.close();
            br.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair(responseCode, response);
    }

    private static Pair<Integer, String> httpRequestReHeader(String method, String strUrl, String xmlStr, String headKey) {
        Integer responseCode = 0;
        String responseHeader = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(true);
            conn.setInstanceFollowRedirects(true);
            //设置请求头
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.connect();
            //设置请求体
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(xmlStr);
            out.flush();
            out.close();
            //获取返回结果
            responseCode = conn.getResponseCode();
            responseHeader = conn.getHeaderField(headKey);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair(responseCode, responseHeader);
    }

    public static Pair<Integer, String> httpGet(String strUrl) {
        return httpRequest("GET", strUrl);
    }

    public static Pair<Integer, String> httpGetByJson(String strUrl, JSONObject jsonObject) {
        return httpRequest("GET", strUrl, jsonObject);
    }

    public static Pair<Integer, String> httpGetByXml(String strUrl, String xml) {
        return httpRequest("GET", strUrl, xml);
    }

    public static Pair<Integer, String> httpPost(String strUrl) {
        return httpRequest("POST", strUrl);
    }

    public static Pair<Integer, String> httpPostByJson(String strUrl, JSONObject jsonObject) {
        return httpRequest("POST", strUrl, jsonObject);
    }

    public static Pair<Integer, String> httpPostByXml(String strUrl, String xml) {
        return httpRequest("POST", strUrl, xml);
    }

    public static Pair<Integer, String> httpDelete(String strUrl) {
        return httpRequest("DELETE", strUrl);
    }

    public static Pair<Integer, String> httpDeleteByJson(String strUrl, JSONObject jsonObject) {
        return httpRequest("DELETE", strUrl, jsonObject);
    }

    public static Pair<Integer, String> httpDeleteByXml(String strUrl, String xml) {
        return httpRequest("DELETE", strUrl, xml);
    }

    public static Pair<Integer, String> httpPut(String strUrl) {
        return httpRequest("PUT", strUrl);
    }

    public static Pair<Integer, String> httpPutByJson(String strUrl, JSONObject jsonObject) {
        return httpRequest("PUT", strUrl, jsonObject);
    }

    public static Pair<Integer, String> httpPutByXml(String strUrl, String xml) {
        return httpRequest("PUT", strUrl, xml);
    }


    //http请求返回
    public static Pair<Integer, String> httpGetReHeader(String strUrl, String headKey) {
        return httpRequestReHeader("GET", strUrl, headKey);
    }

    public static Pair<Integer, String> httpGetReHeaderByJson(String strUrl, JSONObject jsonObject, String headKey) {
        return httpRequestReHeader("GET", strUrl, jsonObject, headKey);
    }

    public static Pair<Integer, String> httpPostReHeader(String strUrl, String headKey) {
        return httpRequestReHeader("POST", strUrl, headKey);
    }

    public static Pair<Integer, String> httpPostReHeaderByJson(String strUrl, JSONObject jsonObject, String headKey) {
        return httpRequestReHeader("POST", strUrl, jsonObject, headKey);
    }

    public static Pair<Integer, String> httpDeleteReHeader(String strUrl, String headKey) {
        return httpRequestReHeader("DELETE", strUrl, headKey);
    }

    public static Pair<Integer, String> httpDeleteReHeaderByJson(String strUrl, JSONObject jsonObject, String headKey) {
        return httpRequestReHeader("DELETE", strUrl, jsonObject, headKey);
    }

    public static Pair<Integer, String> httpPutReHeader(String strUrl, String headKey) {
        return httpRequestReHeader("PUT", strUrl, headKey);
    }

    public static Pair<Integer, String> httpPutReHeaderByJson(String strUrl, JSONObject jsonObject, String headKey) {
        return httpRequestReHeader("PUT", strUrl, jsonObject, headKey);
    }

    public static Pair<Integer, String> httpPutReHeaderByXml(String strUrl, String xml, String headKey) {
        return httpRequestReHeader("PUT", strUrl, xml, headKey);
    }

    public static Pair<Integer, String> httpGetReHeaderByXml(String strUrl, String xml, String headKey) {
        return httpRequestReHeader("GET", strUrl, xml, headKey);
    }

    public static Pair<Integer, String> httpPostReHeaderByXml(String strUrl, String xml, String headKey) {
        return httpRequestReHeader("POST", strUrl, xml, headKey);
    }

    public static Pair<Integer, String> httpDeleteReHeaderByXml(String strUrl, String xml, String headKey) {
        return httpRequestReHeader("DELETE", strUrl, xml, headKey);
    }


}

