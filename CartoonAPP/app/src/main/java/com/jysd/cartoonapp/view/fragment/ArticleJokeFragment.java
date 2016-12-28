package com.jysd.cartoonapp.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.adapter.BaseRecyclerAdapter;
import com.jysd.cartoonapp.bean.Lz13;
import com.jysd.cartoonapp.inter.LoadingState;
import com.jysd.cartoonapp.inter.OnRetryListener;
import com.jysd.cartoonapp.presenter.ArticleJokeFragmentPresenter;
import com.jysd.cartoonapp.utils.NetWorkUtil;
import com.jysd.cartoonapp.view.holder.ArticleJokeHolder;
import com.jysd.cartoonapp.view.impl.IArticleFragmentView;
import com.jysd.cartoonapp.widget.LoadingView;

import java.util.List;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * 请求段子笑话
 * Created by 陈渝金 on 2016/7/7.
 */
public class ArticleJokeFragment  extends BaseFragment implements IArticleFragmentView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    private BaseRecyclerAdapter mAdapter;
    private boolean canLoadMore = true;

    @Override
    public void setAdapter(List<Lz13> list) {
        if (mRecyclerView == null) return;
        pageNo = list.size();
        if (pageNo < pageSize)
            canLoadMore = false;
        if (mAdapter == null) {
                mAdapter = new BaseRecyclerAdapter(list, R.layout.fragment_joke_item, ArticleJokeHolder.class);//该处需要改

            mRecyclerView.setAdapter(mAdapter);
        } else {
            if ((mAdapter.getItem(0) == null) && (list.size() == 0))
                return;
            if ((mAdapter.getItem(0) == null) || (list.size() == 0) || (!((Lz13) mAdapter.getItem(0)).href.equals(list.get(0).href)))
                mAdapter.setmDatas(list);
        }
    }

    @Override
    public void loadMore(List<Lz13> list) {
        if (mRecyclerView != null && mAdapter != null && list != null) {
            if (list.size() < pageSize)
                canLoadMore = false;
            if (list.size() <= 0) {
                return;
            }
            mAdapter.addAll(list);
            pageNo += list.size();
        }
    }

    @Override
    public void onRefreshComplete() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    public void onLoadMoreComplete() {
        isLoadingMore = false;
    }

    @Override
    public ArticleJokeFragmentPresenter getPresenter() {
        return new ArticleJokeFragmentPresenter();
    }

    private LinearLayoutManager mLayoutManager;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.fl_loading)
    LoadingView fl_loading;

    @Override
    public void showSuccess() {
        fl_loading.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_EMPTY);
    }

    @Override
    public boolean checkNet() {
        return NetWorkUtil.isNetWorkConnected(mContext);
    }

    @Override
    public void showFaild() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_ERROR);
    }

    @Override
    public void showNoNet() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_NO_NET);
    }

    private String mResId;

    public void setHref(String mResId) {
        this.mResId = mResId;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {


            if (TextUtils.isEmpty(mResId) || mPresenter == null || !(mPresenter instanceof ArticleJokeFragmentPresenter)) {
                return;
            }
            fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.mipmap.note_empty).withBtnEmptyEnnable(false)
                    .withErrorIco(R.mipmap.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                    .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.mipmap.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                    .withLoadingIco(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
                @Override
                public void onRetry() {
                    ((ArticleJokeFragmentPresenter) mPresenter).getArticles(params);
                }
            }).build();
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (canLoadMore)
                        ArticleJokeFragment.this.onScrolled(mRecyclerView, dx, dy);
                }
            });
            params = new TreeMap<String, String>();

            params.put("url", mResId);
            params.put("page", String.valueOf(page));
            ((ArticleJokeFragmentPresenter) mPresenter).getArticles(params);//搭建桥梁
    }

    @Override
    public int getContentLayout() {
        return R.layout.list_fragment;
    }

    private TreeMap<String, String> params;
    private int page = 0;//页面
    private int pageNo = 0;//当前返回数据的条数
    private final int pageSize = 5;//每一页的条数



    @Override
    public void onRefresh() {

            page = 0;
            params.put("url", mResId);
            params.put("page", String.valueOf(page));
            ((ArticleJokeFragmentPresenter) mPresenter).getArticles(params);

    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();
        //lastVisibleItem >= totalItemCount - 2 表示剩下2个item自动加载，各位自由选择
        // dy>0 表示向下滑动
        if (!isLoadingMore && lastVisibleItem >= totalItemCount - 4 && dy > 0) {
            isLoadingMore = true;
            loadPage();//这里多线程也要手动控制isLoadingMore
        }

    }

    private void loadPage() {

            params.put("url", mResId.replace("_1.html", "_" + (++page+1) + ".html"));
            params.put("page", String.valueOf(page));
            ((ArticleJokeFragmentPresenter) mPresenter).getArticles(params);

    }

    private boolean isLoadingMore;



}
