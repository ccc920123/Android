package com.mvp.chenpan.mvpdemo.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mvp.chenpan.mvpdemo.network.volleyOK.VolleyHelper;

public class MyApplication extends Application {
   // public  static RequestQueue queues;
    @Override
    public void onCreate() {
        super.onCreate();
       // queues= Volley.newRequestQueue(getApplicationContext());
        VolleyHelper.getInstance().init(this);

    }
//    public static RequestQueue getQueues(){
//        return  queues;
//    }
}