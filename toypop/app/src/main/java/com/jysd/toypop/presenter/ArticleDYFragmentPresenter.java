package com.jysd.toypop.presenter;

import com.jysd.toypop.inter.Callback;
import com.jysd.toypop.model.ArticleDYFragmentModel;
import com.jysd.toypop.view.impl.DYFragmentView;

import java.util.Map;

/**
 * Created by 陈渝金 on 2016/6/30.
 */
public class ArticleDYFragmentPresenter extends BasePresenter<DYFragmentView> {
    private ArticleDYFragmentModel mIArticleModel;

    public ArticleDYFragmentPresenter() {
              //model获取数据
            mIArticleModel = new ArticleDYFragmentModel();

    }

    public void getArticles(final Map<String, String> params) {

        mIArticleModel.parserLZ13(params, new Callback<String>() {
            @Override
            public void onSccuss(String data) {
                if (mView == null) return;

                    if (data == null || "".equals(data)) {
                        mView.showEmpty();
                    } else {
                        mView.setWebHtml(data);
                        mView.showSuccess();
                    }

            }

            @Override
            public void onFaild() {
                if (mView == null) return;

            }
        });
    }
}
