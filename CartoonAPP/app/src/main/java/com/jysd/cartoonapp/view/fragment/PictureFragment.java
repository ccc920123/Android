package com.jysd.cartoonapp.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.adapter.BaseRecyclerAdapter;
import com.jysd.cartoonapp.bean.Juzimi;
import com.jysd.cartoonapp.inter.LoadingState;
import com.jysd.cartoonapp.inter.OnRetryListener;
import com.jysd.cartoonapp.presenter.PicturePresenter;
import com.jysd.cartoonapp.utils.NetWorkUtil;
import com.jysd.cartoonapp.view.holder.PictureHolder;
import com.jysd.cartoonapp.view.impl.IPictureView;
import com.jysd.cartoonapp.widget.LoadingView;

import java.util.List;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * 图片fragment
 */
public class PictureFragment extends BaseFragment implements IPictureView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    private BaseRecyclerAdapter mAdapter;
    private boolean canLoadMore = true;

    @Override
    public void setAdapter(List<Juzimi> list) {
        if (mRecyclerView == null) return;
        pageNo = list.size();
        if (pageNo < pageSize)
            canLoadMore = false;
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter(list, R.layout.fragment_picture_item, PictureHolder.class);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if ((mAdapter.getItem(0) == null) && (list.size() == 0))
                return;
            if ((mAdapter.getItem(0) == null) || (list.size() == 0) || (!((Juzimi) mAdapter.getItem(0)).url.equals(list.get(0).url)))
                mAdapter.setmDatas(list);
        }
    }

    @Override
    public void loadMore(List<Juzimi> list) {
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
    public PicturePresenter getPresenter() {
        return new PicturePresenter();
    }

    private StaggeredGridLayoutManager mLayoutManager;

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


    private String mUrl;

    public void setmType(String mType) {
        this.mUrl = mType;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null || !(mPresenter instanceof PicturePresenter)) {
            return;
        }
        fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.mipmap.disk_file_filter_pic_no_data).withBtnEmptyEnnable(false)
                .withErrorIco(R.mipmap.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.mipmap.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingIco(R.drawable.loading_animation).withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                ((PicturePresenter) mPresenter).getJuzimiPictrues(params);
            }
        }).build();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (canLoadMore)
                    PictureFragment.this.onScrolled(recyclerView, dx, dy);
            }
        });
        params = new TreeMap<String, String>();
        params.put("url", mUrl);
        params.put("page", String.valueOf(page));

        ((PicturePresenter) mPresenter).getJuzimiPictrues(params);////搭建桥梁
    }

    @Override
    public int getContentLayout() {
        return R.layout.list_fragment;
    }

    private TreeMap<String, String> params;
    private int page = 1;
    private int pageNo = 0;
    private final int pageSize = 24;

    @Override
    public void onRefresh() {
        page = 1;
        params.put("url", mUrl);
        params.put("page", String.valueOf(page));
        ((PicturePresenter) mPresenter).getJuzimiPictrues(params);
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int[] visibleItem = mLayoutManager.findLastVisibleItemPositions(null);
        int totalItemCount = mLayoutManager.getItemCount();
        //lastVisibleItem >= totalItemCount - 2 表示剩下2个item自动加载，各位自由选择
        // dy>0 表示向下滑动
        int lastitem = Math.max(visibleItem[0], visibleItem[1]);

        if (!isLoadingMore && lastitem >= totalItemCount - 4 && dy > 0) {
                isLoadingMore = true;
                loadPage();//这里多线程也要手动控制isLoadingMore(暂时控制不下拉加载更多)

        }

    }

    private void loadPage() {
        String loadurl = mUrl.substring(0, mUrl.lastIndexOf("-") + 2);
        params.put("url", loadurl + String.valueOf(++page));
        params.put("page", String.valueOf(page));//上面page已经加了这里不在加了
        ((PicturePresenter) mPresenter).getJuzimiPictrues(params);
    }

    private boolean isLoadingMore;
}
