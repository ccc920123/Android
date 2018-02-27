package com.jysd.toypop.view.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jysd.toypop.R;
import com.jysd.toypop.bean.Lz13;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.Bind;

/**
 * Created by 陈渝金 on 2016/7/7.
 * 处理笑话的holder  这里有布局
 */
public class ArticleJokeNeihanHolder extends BaseHolder<Lz13> {
    public ArticleJokeNeihanHolder(View view) {
        super(view);
    }

    @Bind(R.id.joke_jhneihan_auth)
    LinearLayout rl_auth;

    @Bind(R.id.niceneihan_video_player)
    public NiceVideoPlayer videoPlayer;

    @Bind(R.id.joke_jhneihan_des)
    TextView joke_des;
    TxVideoPlayerController mController;
//    @Override
//    public void init() {
//        super.init();
//        mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, ArticleImageTextActivity.class);
//                intent.putExtra("article", mData);
//                intent.putExtra("from", "joke");
//                mContext.startActivity(intent);
////                Toast.makeText(mContext, "ArticleActivity界面", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void setData(Lz13 mData) {
        super.setData(mData);
        joke_des.setText(mData.text);
        ViewGroup.LayoutParams params = videoPlayer.getLayoutParams();
        params.width = itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
//        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
        params.height = (params.width);   // 高度为屏幕宽度
        videoPlayer.setLayoutParams(params);
        mController = new TxVideoPlayerController(getView().getContext());

        Glide.with(itemView.getContext())
                        .load(mData.auth)
                        .placeholder(R.drawable.img_default)
                        .crossFade()
                        .into(mController.imageView());
        videoPlayer.setController(mController);
        mController.setTitle(mData.text);
        videoPlayer.setUp(mData.href, null);
    }
}
