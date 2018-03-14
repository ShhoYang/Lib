package com.yl.library.base.mvp;

/**
 * @author Yang Shihao
 */
public abstract class APresenter<V extends IView> extends BasePresenter {

    protected V mView;

    public void onCreate(V view) {
        mView = view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mView = null;
    }
}
