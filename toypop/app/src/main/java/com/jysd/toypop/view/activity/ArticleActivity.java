package com.jysd.toypop.view.activity;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;


import com.jysd.toypop.R;
import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.inter.OnRetryListener;
import com.jysd.toypop.presenter.ArticleActPresenter;
import com.jysd.toypop.presenter.ArticleChildFragmentPresenter;
import com.jysd.toypop.presenter.BasePresenter;
import com.jysd.toypop.view.impl.IArticleActView;
import com.jysd.toypop.widget.LoadingView;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * 展示text内容
 */
public class ArticleActivity extends BaseActivity implements IArticleActView {
    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    private Lz13 mArticle;
//    private String from="";//判断是从哪个界面来的

    @Override
    public void getIntentValue() {
        super.getIntentValue();
        mArticle = getIntent().getParcelableExtra("article");
//        from=getIntent().getStringExtra("from");
    }

    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle(mArticle.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        return new ArticleActPresenter(getIntent().getStringExtra("from"));
    }
    @Bind(R.id.tv_content)
    TextView tv_content;

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null) return;
        Map<String, String> params = new HashMap<>();
        params.put("url", mArticle.href);
        ((ArticleActPresenter) mPresenter).getArticleContent(params);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_article;
    }

    @Override
    public void setContent(String content) {
        if (tv_content == null) return;
        Spanned sp =Html.fromHtml(content);
        tv_content.setText(sp);
    }


}
