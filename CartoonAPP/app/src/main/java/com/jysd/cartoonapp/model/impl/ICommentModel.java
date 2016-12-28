package com.jysd.cartoonapp.model.impl;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;

import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface ICommentModel extends BaseModel {
    void loadComment(Map<String, String> params, FindCallback<AVObject> mCallback);
}
