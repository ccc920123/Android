package com.jysd.toypop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jysd.toypop.view.fragment.ArticleDYFragment;

import java.util.List;

/**类名: ArticleDYAdapter
 * <br/>功能描述: 段友
 * <br/>作者: 陈渝金
 * <br/>时间: 2017/11/20
 * <br/>最后修改者:
 * <br/>最后修改内容:
 */

public class ArticleDYAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public ArticleDYAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        ArticleDYFragment fragment = new ArticleDYFragment();
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
