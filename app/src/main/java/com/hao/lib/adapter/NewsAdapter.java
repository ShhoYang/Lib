package com.hao.lib.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.hao.lib.R;
import com.hao.lib.bean.News;
import com.hao.lib.utils.ImageManager;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author Yang Shihao
 * @date 2018/3/16
 */

public class NewsAdapter extends CommonAdapter<News> {

    public NewsAdapter(Context context, int layoutId, List<News> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, News news, final int position) {
        holder.setText(R.id.tv_,news.getTitle());
        ImageManager.getInstance().loadImage(mContext,news.getThumbnail_pic_s(), (ImageView) holder.getView(R.id.iv_));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener!= null){
                    mOnItemClickListener.onItemClick(holder.getView(R.id.iv_),null,position);
                }
            }
        });
    }
}
