package com.jysd.toypop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.jysd.toypop.view.fragment.ArticleJokeFragment;

import java.util.List;

/**
 * Created by 陈渝金 on 2016/7/7.
 */
public class ArticleJokeAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public ArticleJokeAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        ArticleJokeFragment fragment = new ArticleJokeFragment();//
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
