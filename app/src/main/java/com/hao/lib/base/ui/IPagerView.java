package com.hao.lib.base.ui;

import android.support.v4.app.Fragment;

/**
 * @author Yang Shihao
 * @date 2018/4/13
 */
public interface IPagerView {

    String[] getTitles();

    Fragment[] getFragments();
}
