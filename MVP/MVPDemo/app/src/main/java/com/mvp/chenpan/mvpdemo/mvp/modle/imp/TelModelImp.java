package com.mvp.chenpan.mvpdemo.mvp.modle.imp;

import com.android.volley.VolleyError;
import com.mvp.chenpan.mvpdemo.mvp.modle.TelModle;
import com.mvp.chenpan.mvpdemo.network.VolleyInterface;
import com.mvp.chenpan.mvpdemo.network.VolleyRequest;

import java.util.Map;

/**
 * modle的具体实现
 * <p/>
 * Created by Administrator on 2016/5/24.
 */
public class TelModelImp implements TelModle {
    String url = "http://apis.juhe.cn/mobile/get";
    String resultFormWork = "";

    @Override
    public void queryTel(final TelLisener telLisener, Map<String, String> map) {

//加载数据
        //String url,String tag, final Map<String,String> params,VolleyInterface volleyInterface
        VolleyRequest.RequestPostString(url, "postTel", map, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                resultFormWork = result;
                if (telLisener != null) {
                    telLisener.queryComppelete(resultFormWork);
                }
            }

            @Override
            public void onMyError(VolleyError result) {
                resultFormWork="数据加载出错";
                if (telLisener != null) {
                    telLisener.queryComppelete(resultFormWork);
                }
            }
        });

    }
}
