package com.cncyj.mostbrain;

import cn.bmob.v3.Bmob;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class WelcomActivity2 extends Activity implements SplashADListener {

	boolean isFirstIn = false;
	// 跳转延时
	private SplashAD splashAD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		// 默认初始化bmob
		Bmob.initialize(this, "7308dc4fdb144ef5e906b58be11fb522");
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);

		fetchSplashAD(this, adsParent, null, "1105066677", "4080417990648402",
				this, 3000);

	}

	/**
	 * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
	 * 
	 * @param activity
	 *            展示广告的activity
	 * @param adContainer
	 *            展示广告的大容器
	 * @param skipContainer
	 *            自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，
	 *            其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
	 * @param appId
	 *            应用ID
	 * @param posId
	 *            广告位ID
	 * @param adListener
	 *            广告状态监听器
	 * @param fetchDelay
	 *            拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
	 */
	private void fetchSplashAD(Activity activity, ViewGroup adContainer,
			View skipContainer, String appId, String posId,
			SplashADListener adListener, int fetchDelay) {
		splashAD = new SplashAD(activity, adContainer, skipContainer, appId,
				posId, adListener, fetchDelay);
	}

	/**
	 * 不可点击的开屏，使用该jump方法，而不是用jumpWhenCanClick
	 */
	private void jump() {
		this.startActivity(new Intent(WelcomActivity2.this, MoreActivity.class));
		this.finish();
	}

	/**
	 * 暂时未使用
	 * 
	 * @FILENAME WelcomActivity.java
	 * @Description
	 * @Author 陈渝金
	 * @Create Time 2016
	 */
	class MyThread implements Runnable {
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// jump();
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
		SharedPreferences preferences = getSharedPreferences("DIARY_SP",
				MODE_PRIVATE);

		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
		isFirstIn = preferences.getBoolean("isFirstIn", true);

		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {

			jump();// 进入主界面

		} else {
			goGuide();// 进入引导
		}

	}

	private void goGuide() { // 跳至引导页
		Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onADClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onADDismissed() {
		skips(); // 跳转至您的应用主界面

	}

	@Override
	public void onADPresent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onADTick(long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNoAD(int arg0) {
		MyThread m = new MyThread();
		new Thread(m).start();

	}
}
