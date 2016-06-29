package com.jysd.toypop.presenter;


import com.jysd.toypop.bean.Material;
import com.jysd.toypop.model.ProvideModel;
import com.jysd.toypop.model.impl.IProvideModel;
import com.jysd.toypop.view.impl.IProvideView;

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
