package com.zld.httplib.service;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/07/13
 *     @desc   :
 * </pre>
 */
public interface CommonService {

    @GET()
    Observable<String> get(@Url String url, @QueryMap Map<String, String> params, @HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @FieldMap Map<String, String> params, @HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @PUT()
    Observable<String> put(@Url String url, @FieldMap Map<String, String> params, @HeaderMap Map<String, String> headers);
}
