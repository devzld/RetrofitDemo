package com.zld.httpclientdemo;

import android.app.Application;

import com.zld.netlib.HttpUtil;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/07/13
 *     @desc   :
 * </pre>
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new HttpUtil.SingletonBuilder(this)
                .baseUrl("http://gank.io")
                .build();
    }
}
