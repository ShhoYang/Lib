package com.hao.lib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hao.lib.R;

public class MenuBottomDialog {

    private Context mContext;
    private RecyclerView.Adapter mAdapter;
    private BottomSheetDialog mBottomSheetDialog;

    private MenuBottomDialog(Context context) {
        mContext = context;
    }

    public boolean isShowing() {
        return mBottomSheetDialog.isShowing();
    }

    public MenuBottomDialog show() {
        mBottomSheetDialog.show();
        return this;
    }

    public void dismiss() {
        mBottomSheetDialog.dismiss();
    }

    public void destroy() {
        mContext = null;
        mAdapter = null;
    }

    @UiThread
    public BottomSheetDialog build() {
        View contentView = View.inflate(mContext, R.layout.menu_bottom_dialog, null);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        RecyclerView recyclerView = contentView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        mBottomSheetDialog = new BottomSheetDialog(mContext);
        mBottomSheetDialog.setContentView(contentView);
        return mBottomSheetDialog;
    }

    public static class Builder {

        private Context mContext;
        MenuBottomDialog mMenuBottomDialog;

        public Builder(@NonNull Context context) {
            mContext = context;
            mMenuBottomDialog = new MenuBottomDialog(mContext);
        }

        public Builder setAdapter(@NonNull RecyclerView.Adapter adapter) {
            mMenuBottomDialog.mAdapter = adapter;
            return this;
        }

        @UiThread
        public MenuBottomDialog create() {
            mMenuBottomDialog.build();
            return mMenuBottomDialog;
        }
    }
}
