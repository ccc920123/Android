package com.jysd.toypop.view.activity;

import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jysd.toypop.R;
import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.presenter.ArticleActPresenter;
import com.jysd.toypop.presenter.BasePresenter;
import com.jysd.toypop.utils.FrecsoUtils;
import com.jysd.toypop.view.impl.IArticleActView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by 陈渝金 on 2016/7/8.
 * 图文混排
 */
public class ArticleImageTextActivity extends BaseActivity implements IArticleActView {
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
    @Bind(R.id.jokeweb)
    WebView web;

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null) return;
        Map<String, String> params = new HashMap<>();
        params.put("url", mArticle.href);
        ((ArticleActPresenter) mPresenter).getArticleContent(params);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_articleimagetext;
    }

    @Override
    public void setContent(String content) {
        if (web == null) return;
//        Document html = Jsoup.parse(content);
//        String str="";
//        try {
//            str= URLDecoder.decode(content, "utf-8");
//        }catch (Exception E)
//        {
//
//        }
//            web.getSettings().setUseWideViewPort(true);
//            web.getSettings().setLoadWithOverviewMode(true);
        WebSettings mWebSettings = web.getSettings();
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setDomStorageEnabled(true);
            web.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
            web.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:left;font color=\"#ff607d8b\";} img{width:100%25;} </style></head><body>"+content+"</body></html>", "text/html; charset=UTF-8", null);//这种写法可以正确解码#ff607d8b


//        Element postContent = html.getElementsByClass("f1").get(0);
//        Elements p = postContent.getElementsByTag("p");
//        Elements img = postContent.getElementsByTag("img");
//        for (Element e : p) {
//            String src="";
//            try {
////                src=img.attr("src");
//                src= e.getElementsByTag("img").get(0).attr("src");
//            }catch (Exception e1){
//                src="";
//            }
//            if(!"".equals(src)) {
//                View view=this.getLayoutInflater().inflate(R.layout.joke_image_item,null);
//                SimpleDraweeView imageView=(SimpleDraweeView)view.findViewById(R.id.joke_items);
//                FrecsoUtils.loadImage(src,imageView);
//                tv_content.addView(view);
//            }
//            View viewt=this.getLayoutInflater().inflate(R.layout.joke_text_item,null);
//            TextView text=(TextView)viewt.findViewById(R.id.tv_content_ime);
//            text.setText(e.text());
//            tv_content.addView(viewt);
//
//        }


//        Spanned sp = Html.fromHtml(content);
//        tv_content.setText(sp);
    }


}
