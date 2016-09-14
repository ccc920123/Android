package com.jysd.toypop;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jysd.toypop.utils.UserManager;

public class PanApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        AVOSCloud.initialize(this, "mlzmkqw1vlpe833ro2m4h5oo3p5isc0i27vtmso7m4fqd2vu", "xtycr8ds169x5pywzd70egw7ph3xbrxmsvo4bzkzk3dtalo1");
        UserManager.getInstance().init(this);
    }

}
