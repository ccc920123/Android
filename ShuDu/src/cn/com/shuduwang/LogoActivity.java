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
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 强制横屏 portrait强制竖屏
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
				jumpWhenCanClick();// 跳转至您的应用主界面
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
				//设置开屏可接受点击时，该回调可用
			}
		};
		
		/**
		 * 构造函数：
		 * new SplashAd(Context context, ViewGroup adsParent,
		 * 				SplashAdListener listener,String adPlaceId, boolean canClick, SplashType splashType);
		 */
		String adPlaceId ="2087423";//重要：请填上您的广告位ID2087423
		new SplashAd(this, adsParent, listener, adPlaceId, true, SplashType.REAL_TIME);
		
	}
	
	
	/**
	 * 当设置开屏可点击时，需要等待跳转页面关闭后，再切换至您的主窗口。故此时需要增加waitingOnRestart判断。
	 * 另外，点击开屏还需要在onRestart中调用jumpWhenCanClick接口。
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
	 * 不可点击的开屏，使用该jump方法，而不是用jumpWhenCanClick
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