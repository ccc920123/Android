package com.mvp.chenpan.mvpdemo.mvp.presenter;

import com.mvp.chenpan.mvpdemo.base.vbase.BasePresenter;
import com.mvp.chenpan.mvpdemo.mvp.modle.TelModle;
import com.mvp.chenpan.mvpdemo.mvp.modle.imp.TelModelImp;
import com.mvp.chenpan.mvpdemo.mvp.view.TelView;

import java.util.Map;

/**
 * 绑定 view和modle的presenter
 * Created by Administrator on 2016/5/24.
 */
public class TelPresenter extends BasePresenter<TelView>{
    //View
    //view与presenter可以相互实现
    TelView telView;
    //modle
    TelModle telModle = new TelModelImp();

    public TelPresenter(TelView telView) {
        this.telView = telView;
    }

    /**
     * 绑定view与modle
     */
    public void fetch( Map<String, String> map) {

        telView.showLoading();
        //modle 获取数据
        if (telModle != null) {
            telModle.queryTel(new TelModle.TelLisener() {
                @Override
                public void queryComppelete(String result) {
                    //得到数据，让View显示
                    telView.showData(result);
                }
            },   map);
        }

    }
}
