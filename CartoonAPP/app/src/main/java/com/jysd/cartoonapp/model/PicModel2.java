package com.jysd.cartoonapp.model;


import com.jysd.cartoonapp.bean.Juzimi;
import com.jysd.cartoonapp.inter.Callback;
import com.jysd.cartoonapp.model.impl.IPicModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class PicModel2 implements IPicModel {


    @Override
    public void parseJuzimiHtml(final Map<String, String> params, final Callback<List<Juzimi>> mCallback) {
        Observable.create(new Observable.OnSubscribe<List<Juzimi>>() {
            @Override
            public void call(final Subscriber<? super List<Juzimi>> subscriber) {
                try {
                    Document result = Jsoup.connect(params.get("url")).get();//
                    Element div= result.getElementsByClass("mainleft").get(0);
                    Element li=div.getElementsByTag("ul").get(0);
                    Elements elements= li.getElementsByTag("li");
                    List<Juzimi> list = new ArrayList<Juzimi>();
                    for (Element e : elements) {
                        Juzimi mJuzimi = new Juzimi();
                        Element a = e.getElementsByTag("a").get(0);
                        String href = a.attr("href");
                        mJuzimi.url="http://www.yaoqmh.net"+href;
                        mJuzimi.content = a.attr("title");//标题
                        Element img=e.getElementsByTag("img").get(0);
                        mJuzimi.sender =img.attr("src");//图片地址
                        list.add(mJuzimi);
                    }
                    subscriber.onNext(list);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onNext(null);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Juzimi>>() {
            @Override
            public void call(List<Juzimi> list) {
                if (null == list) {
                    mCallback.onFaild();
                } else {
                    mCallback.onSccuss(list);
                }
            }
        });
    }

    @Override
    public void parseJuzimiVolley(Map<String, String> params, Callback<List<Juzimi>> mCallback) {

    }
}