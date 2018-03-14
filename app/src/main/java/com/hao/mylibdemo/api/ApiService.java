package com.hao.mylibdemo.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Yang Shihao
 */

public interface ApiService {

    /**
     * 发送消息
     */
    @POST("https://api.netease.im/nimserver/msg/sendMsg.action")
    Observable<ResponseBody> sendMessage(@HeaderMap() Map<String, String> header, @Body() RequestBody body);
}


