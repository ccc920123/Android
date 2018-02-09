package com.jysd.toypop.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @类名: $classname$
 * @功能描述:
 * @作者: $zuozhe$
 * @时间: $date$
 * @最后修改者:
 * @最后修改内容:
 */


public class HeaderStringRequest extends StringRequest {
    public HeaderStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public HeaderStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        Map<String,String> params = new HashMap<>();
       params.put("X-Channel-Code","official");
       params.put("X-Client-Agent","Xiaomi");
       params.put("X-Client-Hash","2f3d6ffkda95dlz2fhju8d3s6dfges3t");
       params.put("X-Client-ID","123456789123456");
       params.put("X-Client-Version","2.3.2");
       params.put("X-Long-Token", "");
       params.put("X-Platform-Type","0");
       params.put("X-Platform-Version","5.0");
       params.put("X-Serial-Num","1492140134");
       params.put("X-User-ID", "");
        return params;
    }
}
