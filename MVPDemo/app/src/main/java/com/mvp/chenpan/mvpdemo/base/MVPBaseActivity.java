package com.mvp.chenpan.mvpdemo.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.mvp.chenpan.mvpdemo.R;
import com.mvp.chenpan.mvpdemo.base.pbase.BasePresenter;
import com.mvp.chenpan.mvpdemo.utils.ContextUtils;
import com.mvp.chenpan.mvpdemo.utils.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * V,T未指定类型，这里我们让子类去定义
 * Created by Administrator on 2016/5/24.
 */
public abstract class MVPBaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    public T mPresenter;
    /**
     * 主线程
     */
    private long mUIThreadId;
    /**
     * 导航栏
     */
    private Toolbar mToolbar;
    /**
     * 管理通知栏的对象
     */
    private SystemBarTintManager tintManager;

    private View mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        initWindow();
        mRootView = createView(null, null, savedInstanceState);
        setContentView(mRootView);
        //创建presenter
        mPresenter = createPresenter();
        //内存泄漏,当Activity销毁，P，M都还在运行 ，就出现内存泄漏
        //关联View
        mPresenter.attachView((V) this);
        mToolbar = (Toolbar) findViewById(getToolBarId());
        setSupportActionBar(mToolbar);//这里要用到主题必须是隐藏了action的
        bindViewAndAction(savedInstanceState);
    }

    /**
     * 绑定视图监听
     */
    public abstract void bindViewAndAction(Bundle savedInstanceState);

    /**
     * 得到当前的xml布局
     *
     * @return
     */
    public abstract int getContentLayout();

    /**
     * 绑定视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = ContextUtils.inflate(this, getContentLayout());
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 获取UI线程ID
     *
     * @return UI线程ID
     */
    public long getUIThreadId() {
        return mUIThreadId;
    }

    /**
     * 得到各个界面的tools
     *
     * @return
     */
    public abstract int getToolBarId();

    @Override
    protected void onDestroy() {
        //解除关联
        mPresenter.detachView((V) this);
        AppManager.getAppManager().finishActivity(this);//弹出栈
        super.onDestroy();
    }

    /**
     * 在父类里面创建，在子类里面具体实现
     *
     * @return
     */
    public abstract T createPresenter();

    /**
     * 设置通知栏颜色
     */
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isSetStatusBar()) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.toolbar_color);
        }
    }

    /**
     * 是否设置沉浸式
     *
     * @return
     */
    protected boolean isSetStatusBar() {
        return false;
    }

    /**
     * 将模式设为singletask，跳转时系统调用
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        mUIThreadId = android.os.Process.myTid();
        super.onNewIntent(intent);
    }

}
