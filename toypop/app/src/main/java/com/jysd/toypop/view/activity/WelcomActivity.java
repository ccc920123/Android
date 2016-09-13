package com.jysd.toypop.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.jysd.toypop.R;

public class WelcomActivity extends Activity {

	boolean isFirstIn = false;
	// 跳转延时




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);

		// the observer of AD
		SplashAdListener listener = new SplashAdListener() {
			@Override
			public void onAdDismissed() {
				jumpWhenCanClick(); // 跳转至您的应用主界面
			}

			@Override
			public void onAdFailed(String arg0) {
				skips(); //
			}

			@Override
			public void onAdPresent() {
			}

			@Override
			public void onAdClick() {
				// 设置开屏可接受点击时，该回调可用
			}
		};
		String adPlaceId = "2894720"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
		new SplashAd(this, adsParent, listener, adPlaceId, true);


//		MyThread m=new MyThread();
//		new Thread(m).start();


	}
	/**
	 * 当设置开屏可点击时，需要等待跳转页面关闭后，再切换至您的主窗口。故此时需要增加canJumpImmediately判断。 另外，点击开屏还需要在onResume中调用jumpWhenCanClick接口。
	 */
	public boolean canJumpImmediately = false;

	private void jumpWhenCanClick() {
		if (canJumpImmediately) {
			this.startActivity(new Intent(WelcomActivity.this,MainActivity.class));
			this.finish();
		} else {
			canJumpImmediately = true;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		canJumpImmediately = false;
	}
	/**
	 * 不可点击的开屏，使用该jump方法，而不是用jumpWhenCanClick
	 */
	private void jump() {
		this.startActivity(new Intent(WelcomActivity.this,MainActivity.class));
		this.finish();
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (canJumpImmediately) {
			jumpWhenCanClick();
		}
		canJumpImmediately = true;
	}


//	class MyThread implements Runnable {
//		public void run() {
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
////             jump();
//			skips();
//		}
//	}
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