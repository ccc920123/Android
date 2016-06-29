package com.jysd.toypop.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.jysd.toypop.model.SettingModel;
import com.jysd.toypop.model.impl.ISettingModel;
import com.jysd.toypop.view.impl.ISettingView;

import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class SettingPresenter extends BasePresenter<ISettingView> {
    private ISettingModel mISettingModel;

    public SettingPresenter() {
        mISettingModel = new SettingModel();
    }

    public void sendFeedback(Map<String, String> params) {
        mISettingModel.sendFeedback(params, new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (mView == null) return;
                if (e == null) {
                    mView.feedback(true);
                } else {
                    mView.feedback(false);
                }
            }
        });
    }

}
