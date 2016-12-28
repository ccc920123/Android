package com.jysd.cartoonapp.model.impl;

import com.avos.avoscloud.SaveCallback;

import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface ISettingModel extends BaseModel {
    void sendFeedback(Map<String, String> params, SaveCallback mCallback);
}
