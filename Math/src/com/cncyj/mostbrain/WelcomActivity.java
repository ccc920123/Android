package com.cncyj.mostbrain;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;

public class WelcomActivity extends Activity {
	
	boolean isFirstIn = false;
	// ��ת��ʱ


	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);
//		AdView.setAppSid(this, "db99f30e");
		SplashAdListener listener=new SplashAdListener() {
			@Override
			public void onAdDismissed() {
//				Log.i("RSplashActivity", "onAdDismissed");
				jumpWhenCanClick();// ��ת������Ӧ��������
			}

			@Override
			public void onAdFailed(String arg0) {
				 MyThread m=new MyThread();
	                new Thread(m).start();
			}

			@Override
			public void onAdPresent() {
//				Log.i("RSplashActivity", "onAdPresent");
			}

			@Override
			public void onAdClick() {
//				Log.i("RSplashActivity", "onAdClick");
				//���ÿ����ɽ��ܵ��ʱ���ûص�����
			}
		};
		
		/**
		 * ���캯����
		 * new SplashAd(Context context, ViewGroup adsParent,
		 * 				SplashAdListener listener,String adPlaceId, boolean canClick, SplashType splashType);
		 */
		String adPlaceId ="2398946";//��Ҫ�����������Ĺ��λID
		new SplashAd(this, adsParent, listener, adPlaceId, true);
		
	}
	
	
	/**
	 * �����ÿ����ɵ��ʱ����Ҫ�ȴ���תҳ��رպ����л������������ڡ��ʴ�ʱ��Ҫ����waitingOnRestart�жϡ�
	 * ���⣬�����������Ҫ��onRestart�е���jumpWhenCanClick�ӿڡ�
	 */
	public boolean waitingOnRestart=false;
	private void jumpWhenCanClick() {
		if(this.hasWindowFocus()||waitingOnRestart){
			skips();
		}else{
			waitingOnRestart=true;
		}
		
	}
	
	/**
	 * ���ɵ���Ŀ�����ʹ�ø�jump��������������jumpWhenCanClick
	 */
	private void jump() {
		this.startActivity(new Intent(WelcomActivity.this, MoreActivity.class));
		this.finish();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(waitingOnRestart){
			jumpWhenCanClick();
		}
	}
	 class MyThread implements Runnable {
         public void run() {
             try {
                 Thread.sleep(3000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
//             jump();
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
		SharedPreferences preferences = getSharedPreferences(
				"DIARY_SP", MODE_PRIVATE);

		// ȡ����Ӧ��ֵ�����û�и�ֵ��˵����δд�룬��true��ΪĬ��ֵ
		isFirstIn = preferences.getBoolean("isFirstIn", true);

		// �жϳ�����ڼ������У�����ǵ�һ����������ת���������棬������ת��������
		if (!isFirstIn) {
			
			 jump();//����������

		} else {
			goGuide();//��������
		}

	}
	private void goGuide() { // ��������ҳ
		Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
		startActivity(intent);
		finish();
	}
}
