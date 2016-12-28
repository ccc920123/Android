package com.jysd.cartoonapp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jysd.cartoonapp.utils.UserManager;

public class PanApplication extends Application {
    public static String TAG;

    private static PanApplication myApplication = null;
    public  static RequestQueue queues;
    public static PanApplication getInstance(){
        return myApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        TAG = this.getClass().getSimpleName();
        //由于Application类本身已经单例，所以直接按以下处理即可。
        queues= Volley.newRequestQueue(getApplicationContext());
        myApplication = this;

//        AVOSCloud.initialize(this, "mlzmkqw1vlpe833ro2m4h5oo3p5isc0i27vtmso7m4fqd2vu", "xtycr8ds169x5pywzd70egw7ph3xbrxmsvo4bzkzk3dtalo1");
        UserManager.getInstance().init(this);
    }
    public static RequestQueue getQueues(){
        return  queues;
    }
}
