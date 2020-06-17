package com.zcs.demo.album.base;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public abstract class CommonRecyclerAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public CommonRecyclerAdapter(@LayoutRes int resId, @Nullable List<T> data) {
        super(resId, data);
    }
}