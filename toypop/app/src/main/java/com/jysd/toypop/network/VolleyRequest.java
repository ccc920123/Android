package com.jysd.toypop.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.jysd.toypop.PanApplication;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/19.
 */
public class VolleyRequest {
    public  static StringRequest stringRequest;
    public static JsonObjectRequest jsonObjectRequest;
    public static Context mContext;
    public  static void  RequestGetString(String url,String tag,VolleyInterface volleyInterface){
        PanApplication.getQueues().cancelAll(tag);
        stringRequest=new StringRequest(Request.Method.GET,url,volleyInterface.loadingListener(),volleyInterface.errorListener());
        stringRequest.setTag(tag);
        PanApplication.getQueues().add(stringRequest);
        PanApplication.getQueues().start();

    }

    /**
     * 设置头部请求方式
     * @param url
     * @param tag
     * @param volleyInterface
     */
    public  static void  RequestAddHeadGetString(String url,String tag,VolleyInterface volleyInterface){
        PanApplication.getQueues().cancelAll(tag);
        stringRequest=new HeaderStringRequest(Request.Method.GET,url,volleyInterface.loadingListener(),volleyInterface.errorListener());
        stringRequest.setTag(tag);
        PanApplication.getQueues().add(stringRequest);
        PanApplication.getQueues().start();

    }
    /**
     * POST String
     * @param url
     * @param tag
     * @param params
     * @param volleyInterface
     */
    public static void RequestPostString(String url,String tag, final Map<String,String> params,VolleyInterface volleyInterface){
        PanApplication.getQueues().cancelAll(tag);
        stringRequest=new StringRequest(Request.Method.POST,url,volleyInterface.loadingListener(),volleyInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);
        PanApplication.getQueues().add(stringRequest);
        PanApplication.getQueues().start();
    }
    /**
     * POST  Json
     * @param url
     * @param tag
     * @param params
     * @param volleyInterface
     */
    public static void RequestPostJson(String url,String tag, final Map<String,String> params,VolleyInterface volleyInterface){
        PanApplication.getQueues().cancelAll(tag);
//        JSONObject jsonObject=new JSONObject(params);
       jsonObjectRequest=new JsonObjectRequest(url, null, volleyInterface.loadingJsonListener(),volleyInterface.errorListener()){
    	   @Override
    	protected Map<String, String> getParams() throws AuthFailureError {
    		// TODO Auto-generated method stub
    		return params;
    	}
       };
        jsonObjectRequest.setTag(tag);
        PanApplication.getQueues().add(jsonObjectRequest);
        PanApplication.getQueues().start();
    }
}
