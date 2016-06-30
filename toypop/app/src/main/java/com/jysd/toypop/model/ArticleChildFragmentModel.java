package com.jysd.toypop.model;

import android.text.TextUtils;

import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.inter.Callback;
import com.jysd.toypop.model.impl.IArticleFragmentModel;

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
 * Created by 陈渝金 on 2016/6/30.
 */
public class ArticleChildFragmentModel implements IArticleFragmentModel {
    @Override
    public void parserLZ13(final Map<String, String> params, final Callback<List<Lz13>> mCallback) {
        Observable.create(new Observable.OnSubscribe<List<Lz13>>() {
                              @Override
                              public void call(Subscriber<? super List<Lz13>> subscriber) {
                                  try {
                                      Document result = Jsoup.connect(params.get("url")).get();
                                      Elements postHeads = result.getElementsByClass("list");//找到我们需要的内容list  <div class="list">本网页只有一个list，但在list下面有<ui> <li>
                                      Elements postContent1s = result.getElementsByClass("PostContent1");
                                      List<Lz13> list = new ArrayList<Lz13>(postHeads.size());
                                      for (int i = 0; i < postHeads.size(); i++) {
                                          Lz13 lz13 = new Lz13();
                                          Element postHead = postHeads.get(i);
                                          Element a = postHead.getElementsByTag("a").get(0);
                                          lz13.href = a.attr("href");
                                          lz13.title = a.text();
                                          Element postContent1 = postContent1s.get(i);
                                          lz13.text = postContent1.text().replace(lz13.title, "");
                                          String[] split = lz13.text.split("\\s{2,}");
                                          StringBuffer sb = new StringBuffer();
                                          for (String s : split) {
                                              if (TextUtils.isEmpty(s)) {
                                                  continue;
                                              }
                                              if (lz13.title.equals(s)) {
                                                  continue;
                                              }
                                              if (s.startsWith("文/")) {
                                                  lz13.auth = s;
                                                  continue;
                                              }
                                              sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(s).append("<br/>");
                                          }
                                          int index = sb.lastIndexOf("<br/>");
                                          if (index == -1) continue;
                                          lz13.text = sb.substring(0, sb.lastIndexOf("<br/>") - 1) + "......";
                                          list.add(lz13);
                                      }
                                      subscriber.onNext(list);
                                  } catch (Exception e) {
                                      e.printStackTrace();
                                      subscriber.onNext(null);
                                  }
                              }
                          }
        ).

                subscribeOn(Schedulers.io()
                ).
                observeOn(AndroidSchedulers.mainThread()

                ).

                subscribe(new Action1<List<Lz13>>() {
                    @Override
                    public void call(List<Lz13> list) {
                        if (null == list) {
                            mCallback.onFaild();
                        } else {
                            mCallback.onSccuss(list);
                        }
                    }
                });
    }
}
