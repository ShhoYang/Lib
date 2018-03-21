package com.hao.lib.rx;

import com.hao.lib.bean.News;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Yang Shihao
 */

public interface ApiService{

    @GET("index")
    Observable<News> getNews(@Query("key") String apiKey, @Query("type") String type);
}


