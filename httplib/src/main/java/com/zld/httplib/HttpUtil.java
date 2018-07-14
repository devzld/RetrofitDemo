package com.zld.httplib;

import android.app.Activity;
import android.content.Context;

import com.zld.httplib.converter.StringConverterFactory;
import com.zld.httplib.service.CommonService;
import com.zld.httplib.util.OkhttpProvide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/07/13
 *     @desc   :
 * </pre>
 */
public class HttpUtil {

    private static volatile HttpUtil mInstance;
    private static volatile CommonService mService;
    private Context mApplicationContext;

    private HttpUtil(CommonService service, Context mApplicationContext) {
        mService = service;
        this.mApplicationContext = mApplicationContext;
    }

    public static CommonService getService() {
        if (mInstance == null) {
            throw new NullPointerException("HttpUtil has not be initialized");
        }
        return mService;
    }

    public static HttpUtil getInstance() {
        if (mInstance == null) {
            throw new NullPointerException("HttpUtil has not be initialized");
        }
        return mInstance;
    }

    public static class SingletonBuilder {
        private Context applicationContext;
        private String baseUrl;
        private List<String> servers = new ArrayList<>();

        private List<Converter.Factory> converterFactories = new ArrayList<>();
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
        OkHttpClient client;


        public SingletonBuilder(Context context) {
            try {
                Activity activity = (Activity) context;
                applicationContext = context.getApplicationContext();
            } catch (Exception e) {
                e.printStackTrace();
                applicationContext = context;
            }
        }

        public SingletonBuilder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public SingletonBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public SingletonBuilder addServerUrl(String ipUrl) {
            this.servers.add(ipUrl);
            return this;
        }

        public SingletonBuilder serverUrls(List<String> servers) {
            this.servers = servers;
            return this;
        }

        public SingletonBuilder addConverterFactory(Converter.Factory factory) {
            this.converterFactories.add(factory);
            return this;
        }

        public SingletonBuilder addCallFactory(CallAdapter.Factory factory) {
            this.adapterFactories.add(factory);
            return this;
        }

        public HttpUtil build() {
            if (checkNULL(this.baseUrl)) {
                throw new NullPointerException("BASE_URL can not be null");
            }
            if (converterFactories.size() == 0) {
                converterFactories.add(StringConverterFactory.create());
            }
            if (adapterFactories.size() == 0) {
                adapterFactories.add(RxJava2CallAdapterFactory.create());
            }
            if (client == null) {
                client = OkhttpProvide.okHttpClient(applicationContext, baseUrl);
            }
            Retrofit.Builder builder = new Retrofit.Builder();

            for (Converter.Factory factory : converterFactories) {
                builder.addConverterFactory(factory);
            }
            for (CallAdapter.Factory factory : adapterFactories) {
                builder.addCallAdapterFactory(factory);
            }
            Retrofit retrofit = builder.baseUrl(baseUrl)
                    .client(client)
                    .build();

            CommonService commonService = retrofit.create(CommonService.class);
            mInstance = new HttpUtil(commonService, applicationContext);
            return mInstance;
        }
    }

    public static boolean checkNULL(String str) {
        return str == null || "null".equals(str) || "".equals(str);
    }

    public static Map<String, String> checkParams(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        //todo
        //retrofit的params的值不能为null，此处做下校验，防止出错
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() == null) {
                params.put(entry.getKey(), "");
            }
        }
        return params;
    }

    public static Map<String, String> checkHeaders(Map<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        //todo
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if (entry.getValue() == null) {
                headers.put(entry.getKey(), "");
            }
        }
        return headers;
    }

    private static CompositeDisposable mDisposable = new CompositeDisposable();
    private static Map<String,Disposable> CALL_MAP = new HashMap<>();


    public static synchronized void putCall(Object tag, String url, Disposable disposable) {
        if (tag == null){
            return;
        }
        synchronized (mDisposable) {
            mDisposable.add(disposable);
        }
        synchronized (CALL_MAP){
            CALL_MAP.put(tag.toString()+url,disposable);
        }

    }

    public static synchronized void cancel(Object tag) {
        if (tag == null){
            return;
        }
        List<String> list = new ArrayList<>();

    }

}
