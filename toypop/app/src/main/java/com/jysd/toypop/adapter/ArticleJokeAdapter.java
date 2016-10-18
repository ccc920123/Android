package com.jysd.toypop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.jysd.toypop.view.fragment.ArticleJokeFragment;
import com.jysd.toypop.view.fragment.ArticleJokeJuheFragment;
import com.jysd.toypop.view.fragment.BaseFragment;

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

    /**
     * 在此处处理聚合接口
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment=null;
        String url=mTitles.get(position).split("@toypopchenyujin@")[1];
        if(url.contains("juhe")) {
            if(url.contains("*"))
            {
                url=url.replace("*","&");
            }
            fragment = new ArticleJokeJuheFragment();//处理聚合笑话  （新开的Fragment）
            ((ArticleJokeJuheFragment)fragment).setHref(url);
        }else{
            fragment = new ArticleJokeFragment();//处理导姐笑话  （新开的Fragment）
            ((ArticleJokeFragment)fragment).setHref(url);
        }

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
