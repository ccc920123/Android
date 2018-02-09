package com.jysd.toypop.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jysd.toypop.R;
import com.jysd.toypop.adapter.BaseRecyclerAdapter;
import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.inter.LoadingState;
import com.jysd.toypop.inter.OnRetryListener;
import com.jysd.toypop.presenter.ArticleJokeJuheFragmentPresenter;
import com.jysd.toypop.utils.NetWorkUtil;
import com.jysd.toypop.view.holder.ArticleJokeJuheHolder;
import com.jysd.toypop.view.impl.IArticleFragmentView;
import com.jysd.toypop.widget.LoadingView;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by 陈渝金 on 2016/9/19.
 */
public class ArticleJokeJuheFragment extends BaseFragment implements IArticleFragmentView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    private BaseRecyclerAdapter mAdapter;
    private boolean canLoadMore = true;
    private boolean isJuhe = true;

    /**
     * 当没有请求道聚合数据时立即调取梨视频数据
     */
    private String errorUrl = "http://120.76.205.241:8000/post/baisibudejie?catid=1&apikey=LrwvOvR2wip0KdHLsZHMwzA2XsEmm2Gsp5dVv6gjmhqGtuq2VQqkNcfC38gO7p7T";
//    private String errorUrl = "http://120.76.205.241:8000/post/baisibudejie?catid=1&apikey=LrwvOvR2wip0KdHLsZHMwzA2XsEmm2Gsp5dVv6gjmhqGtuq2VQqkNcfC38gO7p7T&pageToken=2374025050";


    @Override
    public void setAdapter(List<Lz13> list) {
        if (mRecyclerView == null) return;
        pageNo = list.size();
        if(!params.get("url").contains("juhe")) {
            pageToken = list.get(0).auth;//取的页数
        }
        if (pageNo < pageSize)
            canLoadMore = false;
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter(list, R.layout.fragment_joke_juhe_item, ArticleJokeJuheHolder.class);


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
    public ArticleJokeJuheFragmentPresenter getPresenter() {
        return new ArticleJokeJuheFragmentPresenter();
    }

    private LinearLayoutManager mLayoutManager;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.fl_loading)
    LoadingView fl_loading;

    /**
     * 数据请求成功
     */
    @Override
    public void showSuccess() {
        fl_loading.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 数据请求失败（空）
     */
    @Override
    public void showEmpty() {
//        mSwipeRefreshLayout.setVisibility(View.GONE);
//        fl_loading.setVisibility(View.VISIBLE);
//        fl_loading.setState(LoadingState.STATE_EMPTY);
        isJuhe = false;
        params = new TreeMap<String, String>();
        params.put("url", errorUrl);
        params.put("page", "0");
        ((ArticleJokeJuheFragmentPresenter) mPresenter).getArticles(params);//搭建桥梁

    }

    @Override
    public boolean checkNet() {
        return NetWorkUtil.isNetWorkConnected(mContext);
    }

    /**
     * 数据请求失败（数据错误）
     */
    @Override
    public void showFaild() {
//        mSwipeRefreshLayout.setVisibility(View.GONE);
//        fl_loading.setVisibility(View.VISIBLE);
//        fl_loading.setState(LoadingState.STATE_ERROR);
        //在这里请求不得姐数据
        isJuhe = false;
        params = new TreeMap<String, String>();
        params.put("url", errorUrl);
        params.put("page", "0");
        ((ArticleJokeJuheFragmentPresenter) mPresenter).getArticles(params);//搭建桥梁
    }

    /**
     * 没有网络
     */
    @Override
    public void showNoNet() {
        mSwipeRefreshLayout.setVisibility(View.GONE);
        fl_loading.setVisibility(View.VISIBLE);
        fl_loading.setState(LoadingState.STATE_NO_NET);
    }

    private String mResIdtrxt;
    private List<String> listData = new ArrayList<>();


    public void setHref(String mResId) {
        mResIdtrxt = mResId.substring(0, mResId.lastIndexOf("&"));
        listData.add(mResIdtrxt);
        listData.add(mResId);
        listData.add(errorUrl);

    }

    @Override
    public void bindView(Bundle savedInstanceState) {

        if (listData.size() <= 0 || mPresenter == null || !(mPresenter instanceof ArticleJokeJuheFragmentPresenter)) {
            return;
        }
        fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.mipmap.note_empty).withBtnEmptyEnnable(false)
                .withErrorIco(R.mipmap.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.mipmap.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingIco(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                ((ArticleJokeJuheFragmentPresenter) mPresenter).getArticles(params);
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
                    ArticleJokeJuheFragment.this.onScrolled(mRecyclerView, dx, dy);
            }
        });
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = ((ArticleJokeJuheHolder) holder).videoPlayer;
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });
        /**
         * 测试
         */
            Random rd = new Random();
            int num = rd.nextInt(3);
            params = new TreeMap<String, String>();
            if(listData.get(num).contains("juhe")) {
                params.put("url", listData.get(num) + url + (page) + "&pagesize=10");
            }else{
                params.put("url",errorUrl);
            }
            params.put("page", String.valueOf(page));
        ((ArticleJokeJuheFragmentPresenter) mPresenter).getArticles(params);//搭建桥梁
    }

    @Override
    public int getContentLayout() {
        return R.layout.list_fragment;
    }

    private TreeMap<String, String> params;
    private int page = 1;//页面
    private int pageNo = 0;//当前返回数据的条数
    private final int pageSize = 5;//每一页的条数
    //    ?key=f0da6636bf7cd5bbd8e2af4d4fdd5880
    private String url = "&page=";//聚合数据

    private  String pageToken="";

//************************************//


    //*******************************//
    @Override
    public void onRefresh() {
        page = 1;
        Random rd = new Random();
        int num = rd.nextInt(2);
        if(params.get("url").contains("juhe")) {
            params.put("url", listData.get(num) + url + page + "&pagesize=10");
        }else{
            params.put("url",errorUrl);
        }
        params.put("page", String.valueOf(page));
        ((ArticleJokeJuheFragmentPresenter) mPresenter).getArticles(params);//搭建桥梁


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
        Random rd = new Random();
        int num = rd.nextInt(2);
        if(params.get("url").contains("juhe")) {
            params.put("url", listData.get(num) + url + (++page) + "&pagesize=10");
            params.put("page", String.valueOf(page));
        }else{
            params.put("url",errorUrl+"&pageToken="+pageToken);
            params.put("page", pageToken);
        }
        ((ArticleJokeJuheFragmentPresenter) mPresenter).getArticles(params);//搭建桥梁


    }

    private boolean isLoadingMore;

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
    
}