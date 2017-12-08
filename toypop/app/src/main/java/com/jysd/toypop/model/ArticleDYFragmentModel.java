package com.jysd.toypop.model;

import com.jysd.toypop.inter.Callback;
import com.jysd.toypop.model.impl.DYFragmentModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 陈渝金 on 2016/6/30.
 */
public class ArticleDYFragmentModel implements DYFragmentModel {
    @Override
    public void parserLZ13(final Map<String, String> params, final Callback<String> mCallback) {
        Observable.create(new Observable.OnSubscribe<String>() {
                              @Override
                              public void call(Subscriber<? super String> subscriber) {
                                  try {
                                      StringBuffer htmlBuffer=new StringBuffer();
                                      Document result = Jsoup.connect(params.get("url")).get();
                                      Element listHead = result.head();
                                      htmlBuffer.append(listHead.outerHtml());
                                      htmlBuffer.append("<body>");
                                      //得到列表的内容
                                      Elements listBody = result.getElementsByClass("j-r-list");
                                      for(Element div:listBody)
                                      {
                                          Elements body = div.select(".j-r-list-c");
                                          for (Element content:body)
                                          {
                                              htmlBuffer.append(content.outerHtml());
                                          }
                                      }
                                    //得到页数
                                      Elements listpage = result.getElementsByClass("j-page");
                                      Element page= listpage.get(0);
                                      htmlBuffer.append(page);
                                      htmlBuffer.append("</body>");


                                      subscriber.onNext(htmlBuffer.toString());
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

                subscribe(new Action1<String>() {
                    @Override
                    public void call(String list) {
                        if (null == list) {
                            mCallback.onFaild();
                        } else {
                            mCallback.onSccuss(list);
                        }
                    }
                });
    }
}
