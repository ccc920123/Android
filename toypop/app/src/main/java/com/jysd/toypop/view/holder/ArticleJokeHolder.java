package com.jysd.toypop.view.holder;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jysd.toypop.R;
import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.utils.FrecsoUtils;
import com.jysd.toypop.view.activity.ArticleActivity;
import com.jysd.toypop.view.activity.ArticleImageTextActivity;
import com.jysd.toypop.view.impl.IMainView;

import butterknife.Bind;

/**
 * Created by 陈渝金 on 2016/7/7.
 * 处理笑话的holder  这里有布局
 */
public class ArticleJokeHolder extends BaseHolder<Lz13> {
    public ArticleJokeHolder(View view) {
        super(view);
    }

    @Bind(R.id.joke_auth)
    LinearLayout rl_auth;

    @Bind(R.id.joke_title)
    TextView joke_title;

    @Bind(R.id.joke_image)
    public SimpleDraweeView title;
    @Bind(R.id.joke_des)
    TextView joke_des;

    @Override
    public void init() {
        super.init();
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ArticleImageTextActivity.class);
                intent.putExtra("article", mData);
                intent.putExtra("from", "joke");
                mContext.startActivity(intent);
//                Toast.makeText(mContext, "ArticleActivity界面", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setData(Lz13 mData) {
        super.setData(mData);
        FrecsoUtils.loadImage(mData.auth, title);
        joke_des.setText(Html.fromHtml(mData.text));
        if (TextUtils.isEmpty(mData.title)) {
            rl_auth.setVisibility(View.GONE);
        } else {
            rl_auth.setVisibility(View.VISIBLE);
            joke_title.setText(mData.title);
        }
    }
}
