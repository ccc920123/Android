package com.jysd.cartoonapp.presenter;


import com.jysd.cartoonapp.view.impl.IBaseView;

/**
 * Created by sysadminl on 2015/12/9.
 */
public abstract class BasePresenter<T extends IBaseView> {
    public T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
