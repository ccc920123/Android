package com.jysd.cartoonapp.model;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jysd.cartoonapp.model.impl.IRecommendModel;

/**
 * Created by sysadminl on 2015/12/9.
 */
public class RecommendModel implements IRecommendModel {
    @Override
    public void loadApps(FindCallback callback) {
        AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Game");
        avQuery.whereEqualTo("type",2);
        avQuery.orderByDescending("updatedAt");
        avQuery.findInBackground(callback);
    }
}