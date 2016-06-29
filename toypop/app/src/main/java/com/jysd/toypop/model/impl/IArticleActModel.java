package com.jysd.toypop.model.impl;


import com.jysd.toypop.inter.Callback;

import java.util.Map;

/**
 * Created by sysadminl on 2016/1/18.
 */
public interface IArticleActModel extends BaseModel {

    void parserLZ13Content(Map<String, String> params, Callback<String> mCallback);

}
