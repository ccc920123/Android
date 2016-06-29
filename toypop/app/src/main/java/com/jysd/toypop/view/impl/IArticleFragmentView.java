package com.jysd.toypop.view.impl;


import com.jysd.toypop.bean.Lz13;

import java.util.List;

/**
 * Created by sysadminl on 2016/1/18.
 */
public interface IArticleFragmentView extends IBaseView {
    void setAdapter(List<Lz13> data);

    void loadMore(List<Lz13> list);

    void onRefreshComplete();

    void onLoadMoreComplete();

    void showSuccess();

    void showEmpty();

    boolean checkNet();

    void showFaild();

    void showNoNet();
}
