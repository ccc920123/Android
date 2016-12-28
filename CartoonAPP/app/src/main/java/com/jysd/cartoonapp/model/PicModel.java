package com.jysd.cartoonapp.model;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jysd.cartoonapp.MyStringRequest;
import com.jysd.cartoonapp.PanApplication;
import com.jysd.cartoonapp.bean.Juzimi;
import com.jysd.cartoonapp.inter.Callback;
import com.jysd.cartoonapp.model.impl.IPicModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
public class PicModel implements IPicModel {


    @Override
    public void parseJuzimiHtml(final Map<String, String> params, final Callback<List<Juzimi>> mCallback) {

    }

    @Override
    public void parseJuzimiVolley( final Map<String, String> params, final Callback<List<Juzimi>> mCallback) {
        Observable.create(new Observable.OnSubscribe<List<Juzimi>>() {
            @Override
            public void call(final Subscriber<? super List<Juzimi>> subscriber) {
                MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, params.get("url")+"/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Document result = Jsoup.parse(s);
                            Elements elements = result.select("li[class=am-thumbnail]");
                            List<Juzimi> list = new ArrayList<Juzimi>();
                            for (Element e : elements) {
                                Juzimi mJuzimi = new Juzimi();
                                Element a = e.getElementsByTag("a").get(0);
                                String href = a.attr("href");
                                mJuzimi.url = "http://www.1kkk.com" + href;
                                mJuzimi.content = a.attr("title");//标题
                                Element img = e.getElementsByTag("img").get(0);
                                mJuzimi.sender = img.attr("src");//图片地址
                               try {
                                   Element span = e.getElementsByTag("span").get(0);
                                   mJuzimi.item=span.text();
                               }catch (Exception e1)
                               {
                                   mJuzimi.item="完结";
                               }

                                list.add(mJuzimi);
                            }
                            subscriber.onNext(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                            subscriber.onNext(null);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        subscriber.onNext(null);
                    }
                });
                PanApplication.getQueues().getCache().clear();
                PanApplication.getQueues().add(stringRequest);
                PanApplication.getQueues().start();

//                try {
//                    Document result = Jsoup.connect(params.get("url")).get();//
//                    Elements elements = result.select("li[class=am-thumbnail]");
//                    List<Juzimi> list = new ArrayList<Juzimi>();
//                    for (Element e : elements) {
//                        Juzimi mJuzimi = new Juzimi();
//                        Element a = e.getElementsByTag("a").get(0);
//                        String href = a.attr("href");
//                        mJuzimi.url="http://www.1kkk.com"+href;
//                        mJuzimi.content = a.attr("title");//标题
//                        Element img=e.getElementsByTag("img").get(0);
//                        mJuzimi.sender =img.attr("src");//图片地址
//                        list.add(mJuzimi);
//                    }
//                    subscriber.onNext(list);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    subscriber.onNext(null);
//                }
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
}