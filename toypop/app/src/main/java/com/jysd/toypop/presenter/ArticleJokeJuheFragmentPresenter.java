package com.jysd.toypop.presenter;

import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.inter.Callback;
import com.jysd.toypop.model.ArticleJokeJuheFragmentModel;
import com.jysd.toypop.view.impl.IArticleFragmentView;

import java.util.List;
import java.util.Map;

/**
 * Created by 陈渝金 on 2016/9/18.
 */
public class ArticleJokeJuheFragmentPresenter extends BasePresenter<IArticleFragmentView>  {

    private ArticleJokeJuheFragmentModel  mIArticleModel;

    public  ArticleJokeJuheFragmentPresenter()
    {
        mIArticleModel=new ArticleJokeJuheFragmentModel();
    }

    public void getArticles(final Map<String, String> params) {
        if (!mView.checkNet()) {
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
        //可以通过params来判断来源
        mIArticleModel.parserLZ13(params, new Callback<List<Lz13>>() {
            @Override
            public void onSccuss(List<Lz13> data) {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
               if(params.get("url").contains("juhe")) {
                   if ("1".equals(params.get("page"))) {
                       if (data.size() == 0) {
                           mView.showEmpty();
                       } else {
                           mView.setAdapter(data);
                           mView.showSuccess();
                       }
                   } else {
                       mView.loadMore(data);
                   }
               }else{//说明是不得姐数据

                   if ("1".equals(params.get("page"))) {
                       if (data.size() == 0) {
                           mView.checkNet();
                       } else {
                           mView.setAdapter(data);
                           mView.showSuccess();
                       }
                   } else {
                       mView.loadMore(data);
                   }

               }
            }

            @Override
            public void onFaild() {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if(params.get("url").contains("juhe")) {
                    if ("1".equals(params.get("page"))) {
                        mView.showFaild();
                    }
                }else{////说明是不得姐数据
                    if ("1".equals(params.get("page"))) {
                        mView.showNoNet();
                    }
                }
            }
        });
    }
}

