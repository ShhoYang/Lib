package com.hao.lib.demo.behavior;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hao.lib.R;
import com.hao.lib.base.ui.BaseActivity;

import butterknife.BindView;

public class EasyBehaviorActivity extends BaseActivity {


    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.tv)
    TextView mTv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_easy_behavior;
    }

    @Override
    public void initInject() {
    }

    @Override
    public void initView() {
        setTitle(getClass().getSimpleName());
        mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setX(event.getRawX() - v.getWidth() / 2);
                    v.setY(event.getRawY() - v.getHeight() / 2);
                }
                return false;
            }
        });
    }

    @Override
    public void initData() {

    }
}
