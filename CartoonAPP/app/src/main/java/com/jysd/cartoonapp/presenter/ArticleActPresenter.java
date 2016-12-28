package com.jysd.cartoonapp.presenter;


import com.jysd.cartoonapp.inter.Callback;
import com.jysd.cartoonapp.model.ArticleActChildModel;
import com.jysd.cartoonapp.model.ArticleActJokeModel;
import com.jysd.cartoonapp.model.ArticleActModel;
import com.jysd.cartoonapp.model.impl.IArticleActModel;
import com.jysd.cartoonapp.view.impl.IArticleActView;

import java.util.Map;

/**
 * Created by sysadminl on 2016/1/18.
 */
public class ArticleActPresenter extends BasePresenter<IArticleActView> {
    private IArticleActModel mIArticleModel;


    public ArticleActPresenter(String from) {
        if("father".equals(from)) {

            mIArticleModel = new ArticleActModel();
        }else if("child".equals(from))
        {
            mIArticleModel=new ArticleActChildModel();

        }else if("joke".equals(from))//笑话
        {
            mIArticleModel=new ArticleActJokeModel();

        }
    }

    public void getArticleContent(final Map<String, String> params) {
        mIArticleModel.parserLZ13Content(params, new Callback<String>() {
            @Override
            public void onSccuss(String data) {
                if (mView == null) return;
                mView.setContent(data);
            }

            @Override
            public void onFaild() {
            }
        });
    }
}
