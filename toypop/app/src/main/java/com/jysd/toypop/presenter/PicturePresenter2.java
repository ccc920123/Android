package com.jysd.toypop.presenter;


import com.jysd.toypop.bean.Juzimi;
import com.jysd.toypop.inter.Callback;
import com.jysd.toypop.model.PicModel2;
import com.jysd.toypop.model.impl.IPicModel;
import com.jysd.toypop.view.impl.IPictureView;

import java.util.List;
import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class PicturePresenter2 extends BasePresenter<IPictureView> {
    private IPicModel mIPicModel;

    public PicturePresenter2() {
        mIPicModel = new PicModel2();
    }


    public void getJuzimiPictrues(final Map<String, String> params) {
        if(!mView.checkNet()){
            mView.onRefreshComplete();
            mView.onLoadMoreComplete();
            mView.showNoNet();
            return;
        }
//web请求回来的数据
        mIPicModel.parseJuzimiHtml(params, new Callback<List<Juzimi>>() {
            @Override
            public void onSccuss(List<Juzimi> data) {
                if (mView == null) return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("1".equals(params.get("page"))) {
                    if(data.size()==0){
                        mView.showEmpty();
                    }else {
                        mView.setAdapter(data);
                        mView.showSuccess();
                    }
                } else {
                    mView.loadMore(data);
                }
            }

            @Override
            public void onFaild() {
                if (mView == null)
                    return;
                mView.onRefreshComplete();
                mView.onLoadMoreComplete();
                if ("1".equals(params.get("page"))) {
                  mView.showFaild();
                }
            }
        });
    }
}
