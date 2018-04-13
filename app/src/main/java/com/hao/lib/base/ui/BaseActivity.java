package com.hao.lib.base.ui;

import com.hao.lib.base.mvp.APresenter;
import com.hao.lib.base.proxy.UIProxy;

/**
 * @author Yang Shihao
 */
public abstract class BaseActivity<P extends APresenter> extends MyActivity<P,UIProxy> {
}
