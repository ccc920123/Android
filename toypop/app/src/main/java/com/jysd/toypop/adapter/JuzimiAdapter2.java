package com.jysd.toypop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
        PictureFragment2 fragment = new PictureFragment2();//跳转到另一个逻辑
        fragment.setmType(mTitles.get(position).split("@toypopchenyujin@")[1]);
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
