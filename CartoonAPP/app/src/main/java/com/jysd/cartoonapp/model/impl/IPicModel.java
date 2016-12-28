package com.jysd.cartoonapp.model.impl;


import com.jysd.cartoonapp.bean.Juzimi;
import com.jysd.cartoonapp.inter.Callback;

import java.util.List;
import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IPicModel extends BaseModel {

    void parseJuzimiHtml(Map<String, String> params, Callback<List<Juzimi>> mCallback);

    void parseJuzimiVolley(Map<String, String> params, Callback<List<Juzimi>> mCallback);
}
