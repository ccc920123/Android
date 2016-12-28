package com.jysd.cartoonapp.model;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jysd.cartoonapp.model.impl.ICommentModel;

import java.util.Map;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class CommentModel implements ICommentModel {

    @Override
    public void loadComment(Map<String, String> params, FindCallback<AVObject> mCallback) {
        AVQuery<AVObject> query = AVQuery.getQuery(params.get("class"));
        query.whereEqualTo("rsId", params.get("rsId"));
        query.setLimit(Integer.parseInt(params.get("size")));
        query.setSkip(Integer.parseInt(params.get("pageNo")));
        query.orderByDescending(params.get("order"));
        query.findInBackground(mCallback);
    }

}