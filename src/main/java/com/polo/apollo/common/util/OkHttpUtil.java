package com.polo.apollo.common.util;


import lombok.extern.java.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author baoqianyong
 * @date 2019/06/18
 */
@Component
@Log
public class OkHttpUtil {

    private static OkHttpClient okHttpClient;

    @Autowired
    public OkHttpUtil(OkHttpClient client) {
        OkHttpUtil.okHttpClient = client;
    }

    /**
     * 调用okhttp的newCall方法
     *
     * @param request
     * @return
     */
    private static String execNewCall(Request request) {
        Response response = null;
        try {
            log.info("http ===> " + request.url().toString());
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String resp = response.body().string();
                log.info("===> " + resp);
                return resp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    /**
     * get 请求
     *
     * @param url 请求的url
     * @return
     */
    public static String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36")
                .build();
        return execNewCall(request);
    }
}
