package com.cn.math;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdView;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAd.SplashType;
import com.baidu.mobads.SplashAdListener;

public class WelcomActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);
		AdView.setAppSid(this, "db99f30e");
		SplashAdListener listener=new SplashAdListener() {
			@Override
			public void onAdDismissed() {
//				Log.i("RSplashActivity", "onAdDismissed");
				jumpWhenCanClick();// ��ת������Ӧ��������
			}

			@Override
			public void onAdFailed(String arg0) {
//				Log.i("RSplashActivity", "onAdFailed");
				jump();
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
		String adPlaceId ="2060833";//��Ҫ�����������Ĺ��λID
		new SplashAd(this, adsParent, listener, adPlaceId, true, SplashType.REAL_TIME);
		
	}
	
	
	/**
	 * �����ÿ����ɵ��ʱ����Ҫ�ȴ���תҳ��رպ����л������������ڡ��ʴ�ʱ��Ҫ����waitingOnRestart�жϡ�
	 * ���⣬�����������Ҫ��onRestart�е���jumpWhenCanClick�ӿڡ�
	 */
	public boolean waitingOnRestart=false;
	private void jumpWhenCanClick() {
		if(this.hasWindowFocus()||waitingOnRestart){
			this.startActivity(new Intent(WelcomActivity.this, MoreActivity.class));
			this.finish();
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
//		new Thread(new MyThread()).start();
	
	class MyThread implements Runnable {
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent itts=new Intent();
			itts.setClass(WelcomActivity.this, MoreActivity.class);
			startActivity(itts);
			finish();
           
        }
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
	
}
