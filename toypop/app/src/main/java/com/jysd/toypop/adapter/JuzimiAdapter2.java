package com.jysd.toypop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jysd.toypop.view.fragment.BaseFragment;
import com.jysd.toypop.view.fragment.PictureFragment2;

import java.util.List;

public class JuzimiAdapter2 extends FragmentStatePagerAdapter {
    private List<String> mTitles;

    public JuzimiAdapter2(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = null;//跳转到另一个逻辑
        String titleUrl=mTitles.get(position).split("@toypopchenyujin@")[1];
//        if(titleUrl.contains("weixin"))
//        {
//            if(titleUrl.contains("#"))
//            {
//                titleUrl= titleUrl.replace("#","&");
//            }
//            fragment=new WeiXinWuFragment();
//            ((WeiXinWuFragment)fragment).setHref(titleUrl);
//
//        }else {
            fragment=new PictureFragment2();
            ((PictureFragment2) fragment).setmType(titleUrl);
//        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split("@toypopchenyujin@")[0];
    }
}
