package com.jysd.cartoonapp.presenter;


import com.jysd.cartoonapp.bean.Material;
import com.jysd.cartoonapp.model.ProvideModel;
import com.jysd.cartoonapp.model.impl.IProvideModel;
import com.jysd.cartoonapp.view.impl.IProvideView;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class ProvidePresenter extends BasePresenter<IProvideView> {
    private IProvideModel mIProvideModel;

    public ProvidePresenter() {
        mIProvideModel = new ProvideModel();
    }

    public void provideMaterial() {
        if (mView.checkEmail())
            if (mView.checkTitle())
                if (mView.checkDes()) {
                    Material material = new Material();
                    material.des = mView.getmDes();
                    material.title = mView.getmTitle();
                    material.email = mView.getmEmail();
                    mIProvideModel.upProvide(material);
                }


    }
}
