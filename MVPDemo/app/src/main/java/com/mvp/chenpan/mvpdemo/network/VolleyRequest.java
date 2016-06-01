package com.mvp.chenpan.mvpdemo.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.mvp.chenpan.mvpdemo.base.MyApplication;
import com.mvp.chenpan.mvpdemo.network.volleyOK.VolleyHelper;
import com.mvp.chenpan.mvpdemo.utils.XMLRequest;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/19.
 */
public class VolleyRequest {
    public  static StringRequest stringRequest;
    public  static XMLRequest XMLRequest;

    public static Context mContext;
    public  static void  RequestGetString(String url,String tag,VolleyInterface volleyInterface){
        VolleyHelper.getInstance().getRequestQueue().cancelAll(tag);
        stringRequest=new StringRequest(Request.Method.GET,url,volleyInterface.loadingListener(),volleyInterface.errorListener());
        stringRequest.setTag(tag);
        VolleyHelper.getInstance().getRequestQueue().add(stringRequest);
        VolleyHelper.getInstance().getRequestQueue().start();

    }
    public static void RequestPostString(String url,String tag, final Map<String,String> params,VolleyInterface volleyInterface){
        VolleyHelper.getInstance().getRequestQueue().cancelAll(tag);
        stringRequest=new StringRequest(Request.Method.POST,url,volleyInterface.loadingListener(),volleyInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);
        VolleyHelper.getInstance().getRequestQueue().add(stringRequest);
        VolleyHelper.getInstance().getRequestQueue().start();
    }

    /**
     * xml请求
     * @param url
     * @param tag
     * @param params
     * @param volleyInterface
     */
    public static void RequestGetStringXml(String url,String tag, final Map<String,String> params,VolleyXmlInterface volleyInterface){
        VolleyHelper.getInstance().getRequestQueue().cancelAll(tag);
        XMLRequest=new XMLRequest(Request.Method.POST,url,volleyInterface.loadingListenerXml(),volleyInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        XMLRequest.setTag(tag);
        VolleyHelper.getInstance().getRequestQueue().add(XMLRequest);
        VolleyHelper.getInstance().getRequestQueue().start();
    }
}
