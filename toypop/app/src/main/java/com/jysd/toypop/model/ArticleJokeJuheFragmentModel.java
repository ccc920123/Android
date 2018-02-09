package com.jysd.toypop.model;

import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jysd.toypop.bean.Lz13;
import com.jysd.toypop.bean.BaisibudejieBean;
import com.jysd.toypop.inter.Callback;
import com.jysd.toypop.model.impl.IArticleFragmentModel;
import com.jysd.toypop.network.VolleyInterface;
import com.jysd.toypop.network.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈渝金 on 2016/9/18.
 */

public class ArticleJokeJuheFragmentModel implements IArticleFragmentModel {
    @Override
    public void parserLZ13(Map<String, String> params, final Callback<List<Lz13>> mCallback) {


        //获取数据  通过
        //包含聚合，说明是采用聚合数据
        if (params.get("url").contains("juhe")) {
            VolleyRequest.RequestGetString(params.get("url"), "postjh", new VolleyInterface() {
                @Override
                public void onMySuccess(String result) {
                    try {
//
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("error_code");
                        if (code == 0) {
                            JSONArray arryData = null;
                            if (result.contains("\\")) {
                                arryData = jsonObject.getJSONArray("result");
                            } else {
                                JSONObject object = new JSONObject(jsonObject.getString("result"));

                                arryData = object.getJSONArray("data");
                            }
                            List<Lz13> list = new ArrayList<Lz13>();
                            for (int i = 0; i < arryData.length(); i++) {
                                Lz13 lz13 = new Lz13();
                                JSONObject obj = (JSONObject) arryData.get(i);

                                String text = obj.getString("content");
                                lz13.text = text;
                                String url = "";
                                try {
                                    url = obj.getString("url");

                                } catch (Exception E) {
                                    url = "";

                                }
                                lz13.href = url;//如果是图片请求就有url

                                list.add(lz13);
                            }
                            mCallback.onSccuss(list);


                        }


                    } catch (JSONException e) {
                        mCallback.onFaild();
                    }


                }

                @Override
                public void onMySuccess(JSONObject result) {
                    //处理数据
                    System.out.print(result);
                    mCallback.onFaild();
                }

                @Override
                public void onMyError(VolleyError result) {
                    mCallback.onFaild();
                }
            });

        } else {
            VolleyRequest.RequestGetString(params.get("url"), "liget", new VolleyInterface() {
                @Override
                public void onMySuccess(String result) {
                    Gson gson = new Gson();
                    BaisibudejieBean bean = gson.fromJson(result, BaisibudejieBean.class);

                    if ("000000".equals(bean.getRetcode())) {
                        if (bean.getData().size() >= 0) {
                            List<Lz13> list = new ArrayList<Lz13>();
                            for (int i = 0; i < bean.getData().size(); i++) {
                                Lz13 lz13 = new Lz13();
                                lz13.text = bean.getData().get(i).getContent();
                                lz13.auth = bean.getPageToken();//页数存在lz13.auth中
                                if (bean.getData().get(i).getImageUrls() != null && bean.getData().get(i).getImageUrls().size() > 0) {
                                    lz13.href = bean.getData().get(i).getImageUrls().get(0);
                                } else if (bean.getData().get(i).getVideoUrls() != null && bean.getData().get(i).getVideoUrls().size() > 0) {
                                    lz13.href = bean.getData().get(i).getVideoUrls().get(0);
                                    lz13.title="void";//用这个标签来判断是否是视频。
                                } else {
                                    lz13.href = "";//表示文字
                                }
                                list.add(lz13);

                            }
                            mCallback.onSccuss(list);

                        }


                    } else {
                        mCallback.onFaild();
                    }
                }

                @Override
                public void onMySuccess(JSONObject result) {
                    Log.e("cyj", result.toString());
                    mCallback.onFaild();
                }

                @Override
                public void onMyError(VolleyError result) {
                    Log.e("cyj", result.toString());
                    mCallback.onFaild();
                }
            });

        }
    }
}
