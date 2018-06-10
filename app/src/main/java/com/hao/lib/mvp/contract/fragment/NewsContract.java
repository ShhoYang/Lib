package com.hao.lib.mvp.contract.fragment;

import android.view.View;

import com.hao.lib.base.mvp.AListPresenter;
import com.hao.lib.base.ui.IListView;
import com.hao.lib.bean.News;
import com.hao.lib.rx.Api;

/**
 * @author Yang Shihao
 * @date 2018/3/16
 */

public interface NewsContract {

    interface View extends IListView{

    }

    abstract class Presenter extends AListPresenter<View, News> {

        public Presenter(View view, Api api) {
            super(view,api);
        }
    }
}
