package com.jysd.cartoonapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jysd.cartoonapp.view.fragment.PictureFragment;

import java.util.List;

public class JuzimiAdapter extends FragmentPagerAdapter {
    private List<String> mTitles;

    public JuzimiAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        PictureFragment fragment = new PictureFragment();//跳转到另一个逻辑
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
