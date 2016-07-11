package com.jysd.toypop.model;

import com.jysd.toypop.inter.Callback;
import com.jysd.toypop.model.impl.IArticleActModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 陈渝金 on 2016/7/8.
 */
public class ArticleActJokeModel implements IArticleActModel {
    @Override
    public void parserLZ13Content(final Map<String, String> params, final Callback<String> mCallback) {
        Observable.create(new Observable.OnSubscribe<String>() {
                              @Override
                              public void call(Subscriber<? super String> subscriber) {
                                  try {
//                                      String url=params.get("url");
//                                      Document result = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
                                      Document result = Jsoup.connect(params.get("url")).get();
//                                      Elements postContent= result.getElementsByAttributeValue("class","doc-main box");
                                      Element postContent = result.getElementsByClass("f-l").get(0);
                                      Elements p = postContent.getElementsByTag("p");
                                      Elements img = postContent.getElementsByTag("img");
                                      StringBuffer sb = new StringBuffer();
                                      sb.append("<div class=\"f1\"> ");
                                      for (Element e : p) {

                                          String src="";

                                          try {
                                              src= e.getElementsByTag("img").get(0).attr("src");
                                          }catch (Exception e1){
                                              src="";
                                          }
                                         if(!"".equals(src)) {
                                             sb.append("<img src=").append(src).append(">");
                                         }
                                             sb.append("<p>").append(e.text()).append("</p>");

                                      }
                                      sb.append("</div>");
                                      subscriber.onNext(sb.toString());
                                  } catch (IOException e) {
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
                    public void call(String string) {
                        if (null == string) {
                            mCallback.onFaild();
                        } else {
                            mCallback.onSccuss(string);
                        }
                    }
                });
    }
}
