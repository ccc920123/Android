package com.jysd.cartoonapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.bean.Share;
import com.jysd.cartoonapp.utils.ContextUtils;
import com.jysd.cartoonapp.utils.ViewUtils;

import java.util.List;

public class ShareAdapter extends BaseAdapter {
    private List<Share> mList;
    private Context mContext;

    public ShareAdapter(Context mContext, List<Share> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Share getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ContextUtils.inflate(parent.getContext(), R.layout.share_item);
        }
        ImageView iv_icon = (ImageView) ViewUtils.getViewById(convertView, R.id.iv_icon);
        TextView tv_name = (TextView) ViewUtils.getViewById(convertView, R.id.tv_name);
        iv_icon.setImageResource(getItem(position).resPic);
        tv_name.setText(getItem(position).resStr);
        return convertView;
    }

}