package com.zld.netlib.interfaces;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/23.
 */
@FunctionalInterface
public interface HeadersInterceptor {
    Map checkHeaders(Map headers);
}
