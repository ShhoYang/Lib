package com.yl.library.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yl.library.bean.IMenuItem;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author Yang Shihao
 * @date 2017/12/16
 */

public class MenuItemAdapter extends CommonAdapter {

    private int mTextColor = -1;

    public MenuItemAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    @Override
    protected void convert(ViewHolder holder, Object o, final int position) {

        TextView textView = (TextView) holder.getConvertView();
        String menu = null;
        if (o instanceof String) {
            menu = (String) o;
        } else if (o instanceof IMenuItem) {
            menu = ((IMenuItem) o).getMenuText();
        }
        textView.setText(menu);
        if (mTextColor != -1) {
            textView.setTextColor(mTextColor);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(null, null, position);
                }
            }
        });
    }
}
