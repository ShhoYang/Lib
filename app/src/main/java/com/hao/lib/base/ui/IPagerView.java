package com.hao.lib.base.ui;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * @author Yang Shihao
 */
public interface IPagerView extends IView {

    void setViewPagerData(String[] titles, Fragment[] fragments);

    void setViewPagerData(List<String> titles, List<Fragment> fragments);
}
