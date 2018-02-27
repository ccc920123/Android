package com.jysd.toypop.view.holder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jysd.toypop.R;
import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.view.activity.ImageActivity;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

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
    ImageView webViewImage;

    @Bind(R.id.nice_video_player)
    public NiceVideoPlayer videoPlayer;
    private String title = "";
    private String urls = "";
    TxVideoPlayerController mController;

    @Override
    public void init() {
        super.init();


    }

    @Override
    public void setData(Lz13 mData) {
        super.setData(mData);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(webViewImage.getMeasuredWidth(),webViewImage.getMeasuredHeight());
//        webViewImage .setLayoutParams(layoutParams);
        title = mData.text;
        joke_des.setText(Html.fromHtml(mData.text));
        if (TextUtils.isEmpty(mData.href)) {//表示是文字
            webViewImage.setVisibility(View.GONE);
            videoPlayer.setVisibility(View.GONE);
        } else {

            if ("void".equals(mData.title)) {
                //表示是视频
                videoPlayer.setVisibility(View.VISIBLE);
                webViewImage.setVisibility(View.GONE);


                // 将列表中的每个视频设置为默认16:9的比例
                ViewGroup.LayoutParams params = videoPlayer.getLayoutParams();
                params.width = itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
//                params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
                params.height = (params.width);   // 高度为屏幕宽度
                videoPlayer.setLayoutParams(params);
                mController = new TxVideoPlayerController(getView().getContext());
                new AsyncTaskHttpImage().execute(mData, mController);
//                createVideoThumbnail(mData.href, 100, 100);
//                Glide.with(itemView.getContext())
//                        .load(createVideoThumbnail(mData.href, 100, 100))
//                        .placeholder(R.drawable.img_default)
//                        .crossFade()
//                        .into(mController.imageView());
                videoPlayer.setController(mController);
                bindData(mData);
            } else {
                urls = mData.href;
                webViewImage.setVisibility(View.VISIBLE);
                videoPlayer.setVisibility(View.GONE);
                webViewImage.setOnClickListener(click);
                String url = mData.href;

                Glide.with(getView().getContext())
                        .load(url)
                        .placeholder(R.mipmap.ic_loading)
                        .error(R.mipmap.ic_loading_error)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(webViewImage);
            }
        }
    }

    public void bindData(Lz13 mData) {
        mController.setTitle(mData.text);
//        mController.setLenght(0);
        videoPlayer.setUp(mData.href, null);
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    private Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                     int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private byte[] createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        byte[] bytes = null;
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {

            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            bytes = baos.toByteArray();
        }
        return bytes;
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getView().getContext(), ImageActivity.class);
            intent.putExtra("TITLE", title);
            intent.putExtra("URLS", urls);
            getView().getContext().startActivity(intent);

        }
    };

    class AsyncTaskHttpImage extends AsyncTask<Object, Void, byte[]> {

        Lz13 data;
        TxVideoPlayerController controller;

        @Override
        protected byte[] doInBackground(Object... strings) {

            data = (Lz13) strings[0];
            controller = (TxVideoPlayerController) strings[1];
            return createVideoThumbnail(data.href, 100, 100);
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            if (bytes != null) {
                Glide.with(itemView.getContext())
                        .load(bytes)
                        .placeholder(R.drawable.img_default)
                        .crossFade()
                        .into(controller.imageView());
            }

            super.onPostExecute(bytes);
        }
    }
}
