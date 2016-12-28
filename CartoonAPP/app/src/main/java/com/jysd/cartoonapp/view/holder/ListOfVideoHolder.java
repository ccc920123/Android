package com.jysd.cartoonapp.view.holder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.inter.AbsVideoRes;
import com.jysd.cartoonapp.inter.ParallaxViewController;
import com.jysd.cartoonapp.utils.FrecsoUtils;

import butterknife.Bind;

/**
 * Created by sysadminl on 2015/12/11.
 */
public class ListOfVideoHolder extends BaseHolder<AbsVideoRes> {
    public ListOfVideoHolder(View view) {
        super(view);
    }

    @Bind(R.id.image)
    public SimpleDraweeView image;
    @Bind(R.id.tv_title)
    public TextView tv_title;
    @Bind(R.id.image_serie)
    public SimpleDraweeView image_serie;

    @Override
    public void init() {
        super.init();
        Typeface mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Thin.ttf");
        tv_title.setTypeface(mTypeface);
        Object mObject = mView.getTag(R.id.tag_first);
        if (mObject != null && mObject instanceof ParallaxViewController) {
            ((ParallaxViewController) mObject).imageParallax(image);
        }
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, PlayActivity.class);
//                intent.putExtra("video", mData);
//                mContext.startActivity(intent);
                Toast.makeText(mContext, "PlayActivity界面", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setData(AbsVideoRes mData) {
        super.setData(mData);
        tv_title.setText(mData.getVideoTitle());
        FrecsoUtils.loadImage(mData.getVideoThumbnail(), image);
        FrecsoUtils.loadImage(mData.getSmallVideoThumbnail(), image_serie);
    }
}
