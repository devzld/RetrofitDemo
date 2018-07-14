package com.zld.httplib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.zld.httplib.util.NetUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/07/13
 *     @desc   :
 * </pre>
 */
public class HttpBuilder {
    Map<String, String> params = new HashMap<>();
    Map<String, String> headers = new HashMap<>();
    String url;
    Error mErrorCallBack;
    Success mSuccessCallBack;
    Object tag;
    Context mContext;
    boolean checkNetConnected = false;
//    CompositeDisposable mDisposable;

    public HttpBuilder(@NonNull String url) {
        this.url = url;
    }

    public HttpBuilder tag(@NonNull Object tag) {
        this.tag = tag;
        return this;
    }

    public HttpBuilder params(@NonNull Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    public HttpBuilder params(@NonNull String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public HttpBuilder headers(@NonNull Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public HttpBuilder header(@NonNull String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public HttpBuilder success(@NonNull Success success) {
        this.mSuccessCallBack = success;
        return this;
    }

    public HttpBuilder error(@NonNull Error error) {
        this.mErrorCallBack = error;
        return this;
    }

    public HttpBuilder isConnected(@NonNull Context context) {
        checkNetConnected = true;
        mContext = context;
        return this;
    }

    private String checkUrl(String url) {
        if (HttpUtil.checkNULL(url)) {
            throw new NullPointerException("absolute url can not be empty");
        }
        return url;
    }

    /**
     * 请求前检查
     *
     * @return
     */
    boolean allready() {
        if (!checkNetConnected || mContext == null) {
            return true;
        }
        if (!NetUtils.isConnected(mContext)) {
            Toast.makeText(mContext, "检测到网络已关闭，请先打开网络", Toast.LENGTH_SHORT).show();
            NetUtils.openSetting(mContext);//跳转到网络设置界面
            return false;
        }
        return true;
    }

    public Observable<String> getOb() {
        return HttpUtil.getService().get(checkUrl(this.url), HttpUtil.checkParams(params), HttpUtil.checkHeaders(headers));
    }

    public Observable<String> postOb() {
        return HttpUtil.getService().post(checkUrl(this.url), HttpUtil.checkParams(params), HttpUtil.checkHeaders(headers));
    }

    public Observable<String> putOb() {
        return HttpUtil.getService().put(checkUrl(this.url), HttpUtil.checkParams(params), HttpUtil.checkHeaders(headers));
    }

    @SuppressLint("CheckResult")
    public void get() {
        HttpUtil.getService().get(checkUrl(this.url), HttpUtil.checkParams(params), HttpUtil.checkHeaders(headers))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (mSuccessCallBack != null) {
                            mSuccessCallBack.Success(s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mErrorCallBack != null) {
                            mErrorCallBack.Error(throwable);
                        }
                    }
                });

    }


    @SuppressLint("CheckResult")
    public void post() {
        HttpUtil.getService().post(checkUrl(this.url), HttpUtil.checkParams(params), HttpUtil.checkHeaders(headers))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (mSuccessCallBack != null) {
                            mSuccessCallBack.Success(s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mErrorCallBack != null) {
                            mErrorCallBack.Error(throwable);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void put() {
        HttpUtil.getService().put(checkUrl(this.url), HttpUtil.checkParams(params), HttpUtil.checkHeaders(headers))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (mSuccessCallBack != null) {
                            mSuccessCallBack.Success(s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mErrorCallBack != null) {
                            mErrorCallBack.Error(throwable);
                        }
                    }
                });
    }

    public interface Success {
        void Success(String result);
    }

    public interface Error {
        void Error(Object... msg);
    }

}
