package com.mvp.chenpan.mvpdemo.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.mvp.chenpan.mvpdemo.base.vbase.BasePresenter;

/**
 * V,T未指定类型，这里我们让子类去定义
 * Created by Administrator on 2016/5/24.
 */
public abstract class MVPBaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    public T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //创建presenter
        mPresenter = createPresenter();
        //内存泄漏,当Activity销毁，P，M都还在运行 ，就出现内存泄漏

    }

    /**
     * 在父类里面创建，在子类里面具体实现
     * @return
     */
    public abstract T createPresenter();
}
