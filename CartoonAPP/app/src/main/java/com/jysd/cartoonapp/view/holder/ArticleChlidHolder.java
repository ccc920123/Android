package com.jysd.cartoonapp.view.holder;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.bean.Lz13;
import com.jysd.cartoonapp.view.activity.ArticleActivity;

import butterknife.Bind;

/**
 * Created by 陈渝金 on 2016/7/1.
 */
public class ArticleChlidHolder extends BaseHolder<Lz13> {
    public ArticleChlidHolder(View view) {
        super(view);
    }

    @Bind(R.id.rl_auth)
    RelativeLayout rl_auth;

    @Bind(R.id.tv_auth)
    TextView tv_auth;

    @Bind(R.id.title)
    public TextView title;
    @Bind(R.id.des)
    TextView des;

    @Override
    public void init() {
        super.init();
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ArticleActivity.class);
                intent.putExtra("article", mData);
                intent.putExtra("from","child");
                mContext.startActivity(intent);
//                Toast.makeText(mContext, "ArticleActivity界面", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setData(Lz13 mData) {
        super.setData(mData);
        title.setText(mData.title);
        des.setText(Html.fromHtml(mData.text));
        if (TextUtils.isEmpty(mData.auth)) {
            rl_auth.setVisibility(View.GONE);
        } else {
            rl_auth.setVisibility(View.VISIBLE);
            tv_auth.setText(mData.auth);
        }
    }
}
