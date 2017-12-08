package com.jysd.toypop.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.jysd.toypop.R;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;

public class WelcomActivity extends Activity implements SplashADListener {

    boolean isFirstIn = false;
    // 跳转延时
    //应用id 1105701792
    private final String APPId = "QQ1105701792";
    //广告id 7070918559312744
    private final String SplashPosId = "QQ7070918559312744";
    private SplashAD splashAD;
    ViewGroup adsParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom);
        adsParent = (ViewGroup) this
                .findViewById(R.id.adsRl);
        // the observer of AD
        splashAD= new SplashAD(WelcomActivity.this, adsParent, APPId,
                SplashPosId, this, 3000);


    }

    private void jump() {
        this.startActivity(new Intent(WelcomActivity.this, MainActivity.class));
        this.finish();
    }

    @Override
    public void onADDismissed() {
        skips();
//                // 跳转至您的应用主界面
    }

    @Override
    public void onNoAD(int i) {
        MyThread m = new MyThread();
                new Thread(m).start();
    }

    @Override
    public void onADPresent() {

    }

    @Override
    public void onADClicked() {

    }

    @Override
    public void onADTick(long l) {

    }


    class MyThread implements Runnable {
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            skips();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    private void skips() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(
                "TOYPOP_SP", MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstInTOYPOP_SP", true);

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        if (!isFirstIn) {

            jump();//进入主界面

        } else {
            goGuide();//进入引导
        }

    }

    private void goGuide() { // 跳至引导页
        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
        startActivity(intent);
        finish();
    }
}