package com.jysd.cartoonapp.model;

import com.android.volley.VolleyError;
import com.jysd.cartoonapp.bean.Lz13;
import com.jysd.cartoonapp.inter.Callback;
import com.jysd.cartoonapp.model.impl.IArticleFragmentModel;
import com.jysd.cartoonapp.network.VolleyInterface;
import com.jysd.cartoonapp.network.VolleyRequest;

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

            }

            @Override
            public void onMyError(VolleyError result) {
                mCallback.onFaild();
            }
        });
    }
}
