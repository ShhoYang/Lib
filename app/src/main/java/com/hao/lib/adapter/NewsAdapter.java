package com.hao.lib.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hao.lib.R;
import com.hao.lib.bean.News;
import com.hao.lib.utils.ImageManager;

import java.util.List;

/**
 * @author Yang Shihao
 * @date 2018/3/16
 */

public class NewsAdapter extends BaseQuickAdapter<News,BaseViewHolder> {

    public NewsAdapter(int layoutResId, List<News> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, News news) {
        holder.setText(R.id.tv_, news.getTitle());
        ImageManager.getInstance().loadImage(mContext, news.getThumbnail_pic_s(), (ImageView) holder.getView(R.id.iv_));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
