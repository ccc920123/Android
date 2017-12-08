package com.jysd.toypop.view.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jysd.toypop.R;
import com.jysd.toypop.presenter.ArticleDYFragmentPresenter;
import com.jysd.toypop.utils.ADIntentUtils;
import com.jysd.toypop.view.impl.DYFragmentView;

import java.util.TreeMap;

import butterknife.Bind;
import im.delight.android.webview.AdvancedWebView;

/**
 * Created by 陈渝金 on 2016/6/30.
 */
public class ArticleDYFragment extends BaseFragment implements AdvancedWebView.Listener,DYFragmentView {
    @Bind(R.id.myProgressBar)
    ProgressBar progressBar;

    @Bind(R.id.webview)
    AdvancedWebView mWebView;

    ADIntentUtils adi;

    @Bind(R.id.message)
    TextView messageText;
    @Bind(R.id.webviewlinear)
    LinearLayout mLinearLayout;

    private String mResId;
    private TreeMap<String, String> params;

    @Override
    public ArticleDYFragmentPresenter getPresenter() {
        return new ArticleDYFragmentPresenter();
    }
    public void setHref(String mResId) {
        this.mResId = mResId;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        mLinearLayout.setVisibility(View.VISIBLE);
        messageText.setVisibility(View.GONE);
        WebSettings webSettings = mWebView.getSettings();
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
        mWebView.setHorizontalScrollBarEnabled(false);// 水平不显示
        mWebView.setVerticalScrollBarEnabled(false); // 垂直不显示

        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
        webSettings.setNeedInitialFocus(false);
        mWebView.setBackgroundColor(Color.TRANSPARENT);// 设置其背景为透明

        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);

        webSettings.setDomStorageEnabled(true); //显示全景的问题
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webSettings.setPluginState(WebSettings.PluginState.ON);
        mWebView.setListener(getActivity(), this);
        mWebView.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }


        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (adi.shouldOverrideUrlLoadingByApp(view, url)) {
                    return true;
                }


                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        params = new TreeMap<String, String>();
        params.put("url", mResId);
        ((ArticleDYFragmentPresenter) mPresenter).getArticles(params);//搭建桥梁
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_dyweb;
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        mLinearLayout.setVisibility(View.GONE);
        messageText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }



    @Override
    public void setWebHtml(String data) {
         String html="<html lang=\"zh-CN\">";
        mWebView.loadData(html+data+"</html>","text/html; charset=UTF-8", null);
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showEmpty() {
        mLinearLayout.setVisibility(View.GONE);
        messageText.setVisibility(View.VISIBLE);
    }

}
