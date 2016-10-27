package com.jysd.toypop.view.holder;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.jysd.toypop.R;
import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.view.activity.ImageWebActivity;
import com.jysd.toypop.widget.WrapContentDraweeView;

import butterknife.Bind;

/**
 * Created by 陈渝金 on 2016/9/18.
 */
public class ArticleJokeJuheHolder extends BaseHolder<Lz13> {

    public ArticleJokeJuheHolder(View view) {
        super(view);
    }

    @Bind(R.id.joke_jh_auth)
    LinearLayout rl_auth;

    @Bind(R.id.joke_jh_des)
    TextView joke_des;

    @Bind(R.id.imageweb)
    WrapContentDraweeView webViewImage;

    private String title = "";
    private String urls = "";

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void setData(Lz13 mData) {
        super.setData(mData);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(webViewImage.getMeasuredWidth(),webViewImage.getMeasuredHeight());
//        webViewImage .setLayoutParams(layoutParams);
        title=mData.text;
        joke_des.setText(Html.fromHtml(mData.text));
        if (TextUtils.isEmpty(mData.href)) {
            webViewImage.setVisibility(View.GONE);
        } else {
            urls=mData.href;
            webViewImage.setVisibility(View.VISIBLE);
            webViewImage.setOnClickListener(click);
            String url = mData.href;
            String hz = url.substring(url.length() - 3, url.length());
            if ("gif".equals(hz)) {
                DraweeController draweeController =
                        Fresco.newDraweeControllerBuilder()
                                .setUri(mData.href)
                                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                                .build();
                webViewImage.setController(draweeController);
                webViewImage.setAspectRatio(1.33f);
            } else {

                webViewImage.setImageURI(mData.href);
            }
        }
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getView().getContext(), ImageWebActivity.class);
            intent.putExtra("TITLE",title);
            intent.putExtra("URLS",urls);
            getView().getContext().startActivity(intent);

        }
    };
}
