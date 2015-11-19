package cn.com.shuduwang;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdView;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAd.SplashType;
import com.baidu.mobads.SplashAdListener;

public class LogoActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ǿ�ƺ��� portraitǿ������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo);
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);
		AdView.setAppSid(this, "e984bd39");//
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
		String adPlaceId ="2087423";//��Ҫ�����������Ĺ��λID2087423
		new SplashAd(this, adsParent, listener, adPlaceId, true, SplashType.REAL_TIME);
		
	}
	
	
	/**
	 * �����ÿ����ɵ��ʱ����Ҫ�ȴ���תҳ��رպ����л������������ڡ��ʴ�ʱ��Ҫ����waitingOnRestart�жϡ�
	 * ���⣬�����������Ҫ��onRestart�е���jumpWhenCanClick�ӿڡ�
	 */
	public boolean waitingOnRestart=false;
	private void jumpWhenCanClick() {
		if(this.hasWindowFocus()||waitingOnRestart){
			Intent intent = new Intent(LogoActivity.this,ShuDuActivity.class);
			LogoActivity.this.startActivity(intent);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			LogoActivity.this.finish();
		}else{
			waitingOnRestart=true;
		}
		
	}
	
	/**
	 * ���ɵ���Ŀ�����ʹ�ø�jump��������������jumpWhenCanClick
	 */
	private void jump() {
		Intent intent = new Intent(LogoActivity.this,ShuDuActivity.class);
		LogoActivity.this.startActivity(intent);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		LogoActivity.this.finish();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(waitingOnRestart){
			jumpWhenCanClick();
		}
	}
//		new Thread(){
//			@Override public void run(){
//				try {
//					sleep(3000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}finally{
//					Intent intent = new Intent(LogoActivity.this,ShuDuActivity.class);
//					LogoActivity.this.startActivity(intent);
//					overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
//					LogoActivity.this.finish();
//				}
//			}
//		}.start();
////        
//	}
}