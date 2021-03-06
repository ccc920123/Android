package com.jysd.toypop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jysd.toypop.view.fragment.PictureFragment;
import com.jysd.toypop.view.fragment.WebFragment;

import java.util.List;

public class JuzimiAdapter extends FragmentPagerAdapter {
    private List<String> mTitles;

    public JuzimiAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if(3==position){
            return new WebFragment();
        }
        PictureFragment fragment = new PictureFragment();
        fragment.setmType(Integer.parseInt(mTitles.get(position).split("@toypopchenyujin@")[1]));
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
