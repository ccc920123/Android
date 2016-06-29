package com.jysd.toypop.view.holder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jysd.toypop.R;
import com.jysd.toypop.bean.Series;
import com.jysd.toypop.inter.AbsVideoRes;
import com.jysd.toypop.inter.ParallaxViewController;
import com.jysd.toypop.utils.FrecsoUtils;

import butterknife.Bind;

/**
 * Created by sysadminl on 2015/12/11.
 */
public class VideoHolder extends BaseHolder<AbsVideoRes> {
    public VideoHolder(View view) {
        super(view);
    }

    @Bind(R.id.iv_pic)
    public SimpleDraweeView iv_pic;
    @Bind(R.id.tv_title)
    public TextView tv_title;
    @Bind(R.id.tv_duration)
    public TextView tv_duration;
    Object mInt;

    @Override
    public void init() {
        super.init();
        Object mObject = mView.getTag(R.id.tag_first);
        mInt = mView.getTag(R.id.tag_second);
        if (mObject != null && mObject instanceof ParallaxViewController) {
            ((ParallaxViewController) mObject).imageParallax(iv_pic);
        }
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.setVideoInfo();
                if (mInt != null && mData instanceof Series) {
                    switch ((int) mInt) {
                        case 6:
                            ((Series) mData).type = 100;
                            break;
                        case 5:
                        case 7:
                            ((Series) mData).type = 2;
                            break;
                    }
                }
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
        tv_duration.setText(mData.getVideoDuration());
        tv_title.setText(mData.getVideoTitle());
        FrecsoUtils.loadImage(mData.getVideoThumbnail(), iv_pic);
    }
}
