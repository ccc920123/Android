package com.jysd.cartoonapp.model.impl;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.jysd.cartoonapp.bean.BaoZou;
import com.jysd.cartoonapp.bean.Beaty;
import com.jysd.cartoonapp.bean.Column;
import com.jysd.cartoonapp.bean.Videos;

import java.util.Map;

import retrofit.Callback;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IVideoModel extends BaseModel {

    void loadATVideos(Map<String, String> params, Callback<Videos> callback);

    void loadBeautifulVideos(Map<String, String> params, Callback<Beaty> callback);

    void loadYouKuVideos(Map<String, String> params, FindCallback<AVObject> callback);

    void loadCinemaMovies(Map<String, String> params, Callback<BaoZou> callback);

    void loadMovies(Map<String, String> params, Callback<Column> callback);
}
