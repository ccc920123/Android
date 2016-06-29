package com.jysd.toypop.presenter;


import com.jysd.toypop.inter.Callback;
import com.jysd.toypop.model.ArticleActModel;
import com.jysd.toypop.model.impl.IArticleActModel;
import com.jysd.toypop.view.impl.IArticleActView;

import java.util.Map;

/**
 * Created by sysadminl on 2016/1/18.
 */
public class ArticleActPresenter extends BasePresenter<IArticleActView> {
    private IArticleActModel mIArticleModel;

    public ArticleActPresenter() {
        mIArticleModel = new ArticleActModel();
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
