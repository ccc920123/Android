package com.jysd.toypop.model;

import com.android.volley.VolleyError;
import com.jysd.toypop.bean.Lz13;
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
 * Created by 陈渝金 on 2016/7/7.
 * 这里处理数据
 */
public class ArticleJokeNeihanFragmentModel implements IArticleFragmentModel {
    @Override
    public void parserLZ13(final Map<String, String> params, final Callback<List<Lz13>> mCallback) {
        VolleyRequest.RequestGetString(params.get("url"), "postneihan", new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                try {
//
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("message");
                    if ("success".equals(code)) {
                        JSONArray arryData = null;
                        JSONObject object = new JSONObject(jsonObject.getString("data"));

                        arryData = object.getJSONArray("data");


                        List<Lz13> list = new ArrayList<Lz13>();
                        for (int i = 0; i < arryData.length(); i++) {
                            Lz13 lz13 = new Lz13();
                            JSONObject obj = (JSONObject) arryData.get(i);


                            JSONObject group = new JSONObject(obj.getString("group"));
                            lz13.text = group.getString("text");
                            String url = "";
                            try {
                                url = group.getString("mp4_url");

                            } catch (Exception E) {
                                url = "";

                            }
                            lz13.href = url;//如果是图片请求就有url
                            JSONObject objectcover = new JSONObject(group.getString("large_cover"));
                            JSONArray urllist = objectcover.getJSONArray("url_list");
                            lz13.auth = ((JSONObject) urllist.get(0)).getString("url");//得到视频图片
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
    }
}

