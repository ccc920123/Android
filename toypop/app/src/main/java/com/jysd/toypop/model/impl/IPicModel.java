package com.jysd.toypop.model.impl;


import com.jysd.toypop.bean.Juzimi;
import com.jysd.toypop.inter.Callback;

import java.util.List;
import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IPicModel extends BaseModel {

    void parseJuzimiHtml(Map<String, String> params, Callback<List<Juzimi>> mCallback);
}
