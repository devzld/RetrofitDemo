package com.zld.httplib.util;

import android.content.Context;

import com.zld.httplib.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/07/13
 *     @desc   :
 * </pre>
 */
public class OkhttpProvide {
    static OkHttpClient okHttpClient;

    public static OkHttpClient okHttpClient(Context context, String BASE_URL) {
        if (okHttpClient == null) {
            synchronized (OkhttpProvide.class) {
                if (okHttpClient == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            //todo
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .build();
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                        client = client.newBuilder().addInterceptor(logging).build();
                    }
                    okHttpClient = client;

                }
            }
        }
        return okHttpClient;
    }
}
