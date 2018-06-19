package com.hao.lib.rx;

import com.hao.lib.bean.News;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author Yang Shihao
 */

public interface ApiService {

    @GET("index")
    Observable<List<News>> getNews(@Query("key") String apiKey, @Query("type") String type);

    @GET("ix")
    Observable<ResponseBody> getNews(@Url String url , @QueryMap Map<String, String> map);
}


