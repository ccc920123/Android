package com.jysd.cartoonapp.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.jysd.cartoonapp.MyStringRequest;
import com.jysd.cartoonapp.PanApplication;
import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.bean.Juzimi;
import com.jysd.cartoonapp.inter.LoadingState;
import com.jysd.cartoonapp.inter.OnRetryListener;
import com.jysd.cartoonapp.presenter.BasePresenter;
import com.jysd.cartoonapp.view.impl.IArticleActView;
import com.jysd.cartoonapp.widget.LoadingView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @类名: 漫画详情
 * @功能描述:
 * @作者: $zuozhe$
 * @时间: $date$
 * @最后修改者:
 * @最后修改内容:
 */


@RuntimePermissions
public class CartoonDetail extends BaseActivity implements IArticleActView {

    private String carUrltoonURL;
    private Juzimi data;
    @Bind(R.id.fl_loading)
    LoadingView fl_loading;
    @Bind(R.id.image)
    SubsamplingScaleImageView imageView;
    /**
     * 图片的url
     */
    private String mImageUrl;
    final File downDir = Environment.getExternalStorageDirectory();

//    private String suffix="jpg";
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
        if (Build.VERSION.SDK_INT >= 23) {
            chenckPermission();
        } else {
            getUrlData();
        }


    }

    private void getUrlData() {
        //设置长图加载控件
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        imageView.setMinScale(1.0F);
        imageView.setMaxScale(10.0F);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）
        //请求数据
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET,carUrltoonURL,new Response.Listener<String>(){

            @Override
            public void onResponse(String s) {
                Document result =Jsoup.parse(s);
                Element ul = result.getElementsByClass("mnlt").get(0);
                Element img=ul.getElementsByTag("img").get(0);
                mImageUrl =img.attr("src");//图片地址
//                FrecsoUtils.loadImage( mImageUrl, imageView);
/////////////////////////////
                //使用Glide下载图片,保存到本地
                Glide.with(CartoonDetail.this)
                        .load(mImageUrl)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                File file = new File(downDir, "/CartoonAPP/mcache.jpg");

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
        },new Response.ErrorListener(){
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
private  void  chenckPermission()
{
    CartoonDetailPermissionsDispatcher.sueessdPerWithCheck(this);
}

    @NeedsPermission({Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR})
    void sueessdPer() {
        getUrlData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CartoonDetailPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR})
    void onshowPer(final PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage("我们需要文件读写权限").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.proceed();
            }
        }).show();
    }

    @OnPermissionDenied({Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR})
    void noper() {

        Toast.makeText(this, "你没开启读写权限，请在设置中去开启", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR})
    void allNotPer() {

        Toast.makeText(this, "请在设置中去开启", Toast.LENGTH_SHORT).show();
    }
}

