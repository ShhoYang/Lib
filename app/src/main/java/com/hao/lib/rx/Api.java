package com.hao.lib.rx;


import com.hao.lib.App;
import com.hao.lib.bean.News;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Yang Shihao
 */
public class Api {

    String BASE_URL = "http://v.juhe.cn/toutiao/";

    private ApiService apiService= null;

    @Inject
    public Api(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<News> getNews(String type) {

        return apiService.getNews(App.KEY, App.TYEP);
    }
}

