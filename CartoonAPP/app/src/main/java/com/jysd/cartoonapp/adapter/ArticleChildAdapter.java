package com.jysd.cartoonapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.jysd.cartoonapp.view.fragment.ArticleChlidFragment;

import java.util.List;

/**
 * Created by 陈渝金 on 2016/6/30.
 */
public class ArticleChildAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public ArticleChildAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        ArticleChlidFragment fragment = new ArticleChlidFragment();
        fragment.setHref(mTitles.get(position).split("@toypopchenyujin@")[1]);
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
