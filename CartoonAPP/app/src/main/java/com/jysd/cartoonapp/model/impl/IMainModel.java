package com.jysd.cartoonapp.model.impl;

import android.content.Intent;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface IMainModel extends BaseModel {

    public void onActivityResult(int requestCode, int resultCode, Intent data);

}
