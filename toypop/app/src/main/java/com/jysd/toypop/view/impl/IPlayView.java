package com.jysd.toypop.view.impl;


import com.jysd.toypop.bean.YouKu;
import com.jysd.toypop.inter.AbsVideoRes;

import java.util.List;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IPlayView extends IBaseView {

    //  void setAdapter(List<Comment> list);

    //   void loadMore(List<Comment> list);

    void setVideoSource(String mVideoSource);

    void sendPlayMessage(boolean autoPlay);

    public void setInfo(YouKu youku);

    public boolean getFlag();

    public void setupViewPager(List<? extends AbsVideoRes> data);

    public void  showCommentSuccess();
}
