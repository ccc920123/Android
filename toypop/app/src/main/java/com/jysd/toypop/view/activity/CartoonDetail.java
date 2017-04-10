package com.jysd.toypop.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.jysd.toypop.MyStringRequest;
import com.jysd.toypop.PanApplication;
import com.jysd.toypop.R;
import com.jysd.toypop.bean.Juzimi;
import com.jysd.toypop.inter.LoadingState;
import com.jysd.toypop.inter.OnRetryListener;
import com.jysd.toypop.presenter.BasePresenter;
import com.jysd.toypop.view.impl.IArticleActView;
import com.jysd.toypop.widget.LoadingView;
import com.jysd.toypop.widget.scrollview.OverScrollView;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.delight.android.webview.AdvancedWebView;

/**
 * @类名: 漫画详情
 * @功能描述:
 * @作者: $zuozhe$
 * @时间: $date$
 * @最后修改者:
 * @最后修改内容:
 */


public class CartoonDetail extends BaseActivity implements IArticleActView,AdvancedWebView.Listener {

    private String carUrltoonURL;
    private Juzimi data;
    @Bind(R.id.fl_loading)
    LoadingView fl_loading;
    @Bind(R.id.image)
    SubsamplingScaleImageView imageView;
    @Bind(R.id.webview)
    AdvancedWebView webView;
    @Bind(R.id.relative)
    OverScrollView mRelativeLayout;
    @Bind(R.id.errorText)
    TextView errorText;
    /**
     * 图片的url
     */
    private String mImageUrl;
    final File downDir = Environment.getExternalStorageDirectory();

//    private String suffix="jpg";

    private static final int REQUECT_CODE_SDCARD = 2;

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
//        MPermissions.requestPermissions(this, Common.REQUECT_CODE_READ_EXTERNAL_STORAGE, Manifest
//                .permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.mipmap.disk_file_filter_pic_no_data).withBtnEmptyEnnable(false)
                .withErrorIco(R.mipmap.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.mipmap.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingIco(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                //点击等框的确定事件
                getUrlData();
            }
        }).build();

        MPermissions.requestPermissions(CartoonDetail.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        errorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebView();
            }
        });

    }

    private void getUrlData() {
        //设置长图加载控件
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        imageView.setMinScale(1.0F);
        imageView.setMaxScale(10.0F);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）
        //请求数据
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, carUrltoonURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                Document result = Jsoup.parse(s);
                Element ul = result.getElementsByClass("mnlt").get(0);
                Element img = ul.getElementsByTag("img").get(0);
                mImageUrl = img.attr("src");//图片地址
//                FrecsoUtils.loadImage( mImageUrl, imageView);
/////////////////////////////
                //使用Glide下载图片,保存到本地
                Glide.with(CartoonDetail.this)
                        .load(mImageUrl)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                File file = new File(downDir, "/toypop/mcache.jpg");

                                if (!file.exists()) {
                                    File file2 = new File(file.getParent());
                                    file2.mkdir();


                                }
                                if (!file.isDirectory()) {
                                    try {
                                        file.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                FileOutputStream fout = null;
                                try {
                                    //保存图片
                                    fout = new FileOutputStream(file);
                                    resource.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                                    // 将保存的地址给SubsamplingScaleImageView,这里注意设置ImageViewState
                                    imageView.setImage(ImageSource.uri(file.getAbsolutePath()), new ImageViewState(0.5F, new PointF(0, 0), 0));
                                    fl_loading.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (fout != null) fout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fl_loading.setState(LoadingState.STATE_ERROR);
            }
        });
//将StringRequest对象添加进RequestQueue请求队列中
        PanApplication.getQueues().add(stringRequest);
        PanApplication.getQueues().start();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_cartoondetail;
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public void getIntentValue() {
        super.getIntentValue();
        data = CartoonDetail.this.getIntent().getParcelableExtra("PIC");
        carUrltoonURL = data.url;
    }

    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle(data.content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private  void  getWebView()
    {
        imageView.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setListener(this, this);
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, carUrltoonURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                Document result = Jsoup.parse(s);
                Element ul = result.getElementsByClass("mnlt").get(0);
                Element img = ul.getElementsByTag("img").get(0);
//                <img alt="5092章 妖气邪恶漫画浩的反击" src="http://pic.taov5.com/1/344/208.jpg">
                mImageUrl = img.attr("src");//图片地址
//                webView.loadUrl(mImageUrl);
                webView.loadHtml("<img width=\"100%\"src=\""+mImageUrl+"\">");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fl_loading.setState(LoadingState.STATE_ERROR);
            }
        });
//将StringRequest对象添加进RequestQueue请求队列中
        PanApplication.getQueues().add(stringRequest);
        PanApplication.getQueues().start();
    }
    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
        getUrlData();
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed() {
        getWebView();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText("请等待，正在为你加载数据...");
        mRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPageFinished(String url) {
        mRelativeLayout.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        errorText.setText("加载失败，点击重新加载");
        mRelativeLayout.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        webView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        webView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!webView.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }
}

