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
	// ��ת��ʱ
	private SplashAD splashAD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		// Ĭ�ϳ�ʼ��bmob
		Bmob.initialize(this, "7308dc4fdb144ef5e906b58be11fb522");
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);

		fetchSplashAD(this, adsParent, null, "1105066677", "4080417990648402",
				this, 3000);

	}

	/**
	 * ��ȡ������棬�������Ĺ��췽����3�֣���ϸ˵����ο��������ĵ���
	 * 
	 * @param activity
	 *            չʾ����activity
	 * @param adContainer
	 *            չʾ���Ĵ�����
	 * @param skipContainer
	 *            �Զ����������ť�������view��SDK��SDK���Զ������󶨵�������¼���SkipView����ʽ�����ɿ��������ɶ��ƣ�
	 *            ��ߴ�������ο�activity_splash.xml���߽����ĵ��е�˵����
	 * @param appId
	 *            Ӧ��ID
	 * @param posId
	 *            ���λID
	 * @param adListener
	 *            ���״̬������
	 * @param fetchDelay
	 *            ��ȡ���ĳ�ʱʱ����ȡֵ��Χ[3000, 5000]����Ϊ0��ʾʹ�ù��ͨSDKĬ�ϵĳ�ʱʱ����
	 */
	private void fetchSplashAD(Activity activity, ViewGroup adContainer,
			View skipContainer, String appId, String posId,
			SplashADListener adListener, int fetchDelay) {
		splashAD = new SplashAD(activity, adContainer, skipContainer, appId,
				posId, adListener, fetchDelay);
	}

	/**
	 * ���ɵ���Ŀ�����ʹ�ø�jump��������������jumpWhenCanClick
	 */
	private void jump() {
		this.startActivity(new Intent(WelcomActivity2.this, MoreActivity.class));
		this.finish();
	}

	/**
	 * ��ʱδʹ��
	 * 
	 * @FILENAME WelcomActivity.java
	 * @Description
	 * @Author �����
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
		// ��ȡSharedPreferences����Ҫ������
		// ʹ��SharedPreferences����¼�����ʹ�ô���
		SharedPreferences preferences = getSharedPreferences("DIARY_SP",
				MODE_PRIVATE);

		// ȡ����Ӧ��ֵ�����û�и�ֵ��˵����δд�룬��true��ΪĬ��ֵ
		isFirstIn = preferences.getBoolean("isFirstIn", true);

		// �жϳ�����ڼ������У�����ǵ�һ����������ת���������棬������ת��������
		if (!isFirstIn) {

			jump();// ����������

		} else {
			goGuide();// ��������
		}

	}

	private void goGuide() { // ��������ҳ
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
		skips(); // ��ת������Ӧ��������

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
