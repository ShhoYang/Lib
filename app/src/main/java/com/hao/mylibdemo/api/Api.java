package com.hao.mylibdemo.api;


import com.hao.mylibdemo.App;
import com.hao.mylibdemo.bean.News;

import io.reactivex.Observable;

/**
 * @author Yang Shihao
 */
public class Api {

    String BASE_URL = "http://v.juhe.cn/toutiao/";

    static ApiService apiService= null;

    public static Observable<News> getNews(String type) {

        return apiService.getNews(App.KEY, App.TYEP);
    }
}

