package com.jysd.toypop.view.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.jysd.toypop.R;
import com.jysd.toypop.bean.Anime;
import com.jysd.toypop.bean.Resources;
import com.jysd.toypop.inter.AbsVideoRes;
import com.jysd.toypop.presenter.PicturePresenter;

import butterknife.Bind;

public class VideoDesFragment extends BaseFragment {

    @Override
    public PicturePresenter getPresenter() {
        return null;
    }

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.play_count)
    TextView play_count;

    @Bind(R.id.des)
    TextView des;

    private AbsVideoRes mRes;


    public void setRes(AbsVideoRes mRes) {
        this.mRes = mRes;
    }

    @Override
    public void bindView(Bundle savedInstanceState){
        if(mRes==null)return;;
        title.setText(mRes.getVideoTitle());
        des.setText(mRes.getVideoDes());
        if(mRes instanceof Anime){
            play_count.setText("作者："+((Anime) mRes).Author);
        }else if(mRes instanceof Resources){
            play_count.setText("标签："+((Resources) mRes).tag);
        }
    }

    @Override
    public int getContentLayout() {
        return R.layout.info_item;
    }
}
