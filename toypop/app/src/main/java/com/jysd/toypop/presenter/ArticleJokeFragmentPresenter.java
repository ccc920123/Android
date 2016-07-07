package com.jysd.toypop.presenter;

import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.inter.Callback;
import com.jysd.toypop.model.ArticleChildFragmentModel;
import com.jysd.toypop.model.ArticleJokeFragmentModel;
import com.jysd.toypop.view.impl.IArticleFragmentView;

import java.util.List;
import java.util.Map;

/**
 * Created by 陈渝金 on 2016/7/7.
 */
public class ArticleJokeFragmentPresenter extends BasePresenter<IArticleFragmentView> {
    private ArticleJokeFragmentModel mIArticleModel;

    public ArticleJokeFragmentPresenter() {
        mIArticleModel = new ArticleJokeFragmentModel();//model获取数据

    }

    public void getArticles(final Map<String, String> params) {
        if (!mView.checkNet()) {
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        mIArticleModel.parserLZ13(params, new Callback<List<Lz13>>() {
            @Override
            public void onSccuss(List<Lz13> data) {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("0".equals(params.get("page"))) {
                    if (data.size() == 0) {
                        mView.showEmpty();
                    } else {
                        mView.setAdapter(data);
                        mView.showSuccess();
                    }
                } else {
                    mView.loadMore(data);
                }

            }

            @Override
            public void onFaild() {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("0".equals(params.get("page"))){
                    mView.showFaild();
                }
            }
        });
    }
}

