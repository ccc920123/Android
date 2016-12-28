package com.jysd.cartoonapp.model;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jysd.cartoonapp.ConstantValue;
import com.jysd.cartoonapp.bean.BaoZou;
import com.jysd.cartoonapp.bean.Beaty;
import com.jysd.cartoonapp.bean.Column;
import com.jysd.cartoonapp.bean.Videos;
import com.jysd.cartoonapp.inter.ApiService;
import com.jysd.cartoonapp.model.impl.IVideoModel;

import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * model层通过Retrofit来实现数据请求
 */
public class VideoModel implements IVideoModel {
    @Override
    public void loadATVideos(Map<String, String> params, Callback<Videos> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.AT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<Videos> call = service.repoATVideos(params.get("api_key"), params.get("timestamp"), params.get("page"), params.get("access_token"));
        call.enqueue(callback);
    }

    @Override
    public void loadBeautifulVideos(Map<String, String> params, Callback<Beaty> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.URL_BEATY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<Beaty> call = service.repoBeautifulVideos(params.get("pageNo"), params.get("pageSize"), params.get("deviceModel"), params.get("plamformVersion"), params.get("deviceName"), params.get("plamform"), params.get("imieId"));
        call.enqueue(callback);
    }

    @Override
    public void loadYouKuVideos(Map<String, String> params, FindCallback<AVObject> callback) {
        AVQuery<AVObject> query = AVQuery.getQuery("Video");
        query.whereContains("type", params.get("type"));
        query.setLimit(Integer.parseInt(params.get("pageSize")));
        int skip = Integer.parseInt(params.get("skip"));
        if (0 < skip) {
            query.setSkip(skip);
        }
        query.orderByDescending(params.get("orderBy"));
        query.findInBackground(callback);
    }

    @Override
    public void loadCinemaMovies(Map<String, String> params, Callback<BaoZou> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.BAOZOU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<BaoZou> call = service.repoCinemaMovies(params.get("client_id"), params.get("pagesize"), params.get("page"));
        call.enqueue(callback);
    }

    @Override
    public void loadMovies(Map<String, String> params, Callback<Column> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConstantValue.BAOZOU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<Column> call = service.repoMovies(params.get("type"), params.get("pagesize"), params.get("page"));
        call.enqueue(callback);
    }

}