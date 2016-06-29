package com.jysd.toypop.view.impl;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IProvideView extends IBaseView {
    String getmTitle();

    String getmEmail();

    String getmDes();

    boolean checkTitle();

    boolean checkEmail();

    boolean checkDes();

    void clear();
}
