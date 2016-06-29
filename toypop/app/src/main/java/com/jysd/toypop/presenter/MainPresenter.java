package com.jysd.toypop.presenter;

import android.content.Context;
import android.content.Intent;

import com.jysd.toypop.bean.User;
import com.jysd.toypop.model.MainModel;
import com.jysd.toypop.model.impl.IMainModel;
import com.jysd.toypop.utils.UserManager;
import com.jysd.toypop.view.impl.IMainView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by sysadminl on 2016/1/22.
 */
public class MainPresenter extends BasePresenter<IMainView> {
    private IMainModel mIMainModel;

    public MainPresenter() {
        mIMainModel = new MainModel();
    }

    public void login(final Context mContext, SHARE_MEDIA platform) {
        mIMainModel.login(mContext, platform, new UMAuthListener() {

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                mIMainModel.getPlateformInfo(mContext, share_media, new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        if (map != null) {
                            User user = new User();
                            user.screen_name = map
                                    .get("screen_name");
                            user.location = map
                                    .get("province") + map
                                    .get("city");
                            user.gender = map.get("gender");
                            user.profile_image_url = map
                                    .get("profile_image_url");
                            UserManager.getInstance().saveUser(user);
                            if (null != mView) {
                                mView.setUserInfo(user);
                            }
                            if (share_media == SHARE_MEDIA.QQ) {
                                MobclickAgent.onProfileSignIn("QQ", user.screen_name);
                            } else {
                                MobclickAgent.onProfileSignIn("Sina", user.screen_name);
                            }
                        }

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    public void onActResult(int requestCode, int resultCode, Intent data) {
        mIMainModel.onActivityResult(requestCode, resultCode, data);
    }

    public void getUserInfo() {

    }
}
