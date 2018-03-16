package com.hao.mylibdemo.activity.contract;

import com.hao.mylibdemo.bean.News;
import com.yl.library.base.mvp.AListPresenter;
import com.yl.library.base.mvp.IListView;

/**
 * @author Yang Shihao
 * @date 2018/3/16
 */

public interface MainContract{

    interface View extends IListView{

    }

    abstract class Presenter extends AListPresenter<View,News.DataBean>{

    }
}
