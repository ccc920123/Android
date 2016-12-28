package com.jysd.cartoonapp.view.activity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jysd.cartoonapp.MyStringRequest;
import com.jysd.cartoonapp.PanApplication;
import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.presenter.BasePresenter;
import com.jysd.cartoonapp.view.impl.IArticleActView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @类名: PreviewCarToon
 * @功能描述: 漫画预览界面
 * @作者: $zuozhe$
 * @时间: $date$
 * @最后修改者:
 * @最后修改内容:
 */


public class PreviewCarToon extends BaseActivity implements IArticleActView {


    private String url;
    private String page;

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


        getPageUrl();




    }

    /**
     * 请求url数据
     * #ipg2
     */
    private void getPageUrl() {



        MyStringRequest stringRequset=new MyStringRequest(Request.Method.GET, url+"#ipg"+1, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Document result = Jsoup.parse(s);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });

        PanApplication.getQueues().add(stringRequset);
        PanApplication.getQueues().start();

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_previewcartoon;
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public void getIntentValue() {
        super.getIntentValue();
        url=this.getIntent().getStringExtra("URL");
        page= this.getIntent().getStringExtra("PAGE");


    }

    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle("第"+page+"章");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

}
