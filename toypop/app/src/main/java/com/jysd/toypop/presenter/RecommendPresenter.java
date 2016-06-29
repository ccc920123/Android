package com.jysd.toypop.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.jysd.toypop.model.RecommendModel;
import com.jysd.toypop.model.impl.IRecommendModel;
import com.jysd.toypop.view.impl.IRecommendView;

import java.util.List;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class RecommendPresenter extends BasePresenter<IRecommendView> {
    private IRecommendModel mIRecommendModel;

    public RecommendPresenter() {
        mIRecommendModel = new RecommendModel();
    }

    public void getApps() {
        if(!mView.checkNet()){
            mView.showNoNet();
            return;
        }
        mIRecommendModel.loadApps(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (mView == null) return;
                if (e == null) {
                    if (list.size() == 0) {
                        mView.showEmpty();
                    } else {
                        mView.setAdapter(list);
                        mView.showSuccess();
                    }
                } else {
                    mView.showFaild();
                }
            }
        });
    }
}
