package com.hao.lib.demo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Yang Shihao
 * @date 2018/6/14
 */
public class EasyBehavior extends CoordinatorLayout.Behavior<TextView> {

    private static final String TAG = "EasyBehavior";

    public EasyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        child.setX(dependency.getX() + 200);
        child.setY(dependency.getY() + 200);
        child.setText(dependency.getX() + "--" + dependency.getY());
        return true;
    }
}
