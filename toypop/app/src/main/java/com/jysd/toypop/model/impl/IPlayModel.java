package com.jysd.toypop.model.impl;

import android.content.Context;

import com.avos.avoscloud.SaveCallback;
import com.jysd.toypop.bean.Episodes;
import com.jysd.toypop.bean.PlayAddress;
import com.jysd.toypop.bean.PlayUrl;
import com.jysd.toypop.bean.YouKu;

import java.util.Map;

import retrofit.Callback;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IPlayModel extends BaseModel {

    void loadVideoUrl(Map<String, String> params, Callback<PlayUrl> callback);

    boolean isAutoPlay(Context mContext);

    void loadBaozouUrl(Map<String, String> params, Callback<Episodes> callback);

    void parseYoukuHtml(Map<String, String> params, com.jysd.toypop.inter.Callback<YouKu> mCallback);

    void loadBaozouAddress(Map<String, String> params, Callback<PlayAddress> callback);

    void saveComment(Map<String, String> params, SaveCallback callBack);

    void loadRealAddress(Map<String, String> params, Callback<String> callback);
}
