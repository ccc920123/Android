package com.mvp.chenpan.mvpdemo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mvp.chenpan.mvpdemo.base.MVPBaseActivity;
import com.mvp.chenpan.mvpdemo.mvp.presenter.TelPresenter;
import com.mvp.chenpan.mvpdemo.mvp.view.TelView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends MVPBaseActivity<TelView, TelPresenter> implements TelView {


    @Bind(R.id.tel)
    EditText tel;
    @Bind(R.id.query)
    Button query;
    @Bind(R.id.show)
    TextView show;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public void bindViewAndAction() {
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = tel.getText().toString();
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("key", "e59b086b3ef990049b3056d6a5d5e342");
                mPresenter.fetch(map);
            }
        });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }


    @Override
    public void showLoading() {
//加载过程的显示，可以显示进度条等
    }

    @Override
    public void showData(String result) {
//负责显示
        show.setText(result);
    }


    @Override
    public Toolbar getToolBarId() {
        return toolbar;
    }

    @Override
    public TelPresenter createPresenter() {
        return new TelPresenter();
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }
}
