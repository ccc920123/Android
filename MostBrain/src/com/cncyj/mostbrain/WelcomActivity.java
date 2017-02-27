package com.cncyj.mostbrain;

import cn.bmob.v3.Bmob;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

public class WelcomActivity extends Activity {
	
	boolean isFirstIn = false;
	// ��ת��ʱ


	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
	     //Ĭ�ϳ�ʼ��bmob
        Bmob.initialize(this, "7308dc4fdb144ef5e906b58be11fb522");
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);
		 // the observer of AD
        SplashAdListener listener = new SplashAdListener() {
            @Override
            public void onAdDismissed() {
//                Log.i("RSplashActivity", "onAdDismissed");
            	skips(); // ��ת������Ӧ��������
            }

            @Override
            public void onAdFailed(String arg0) {
             				 MyThread m=new MyThread();
                new Thread(m).start();
            }

            @Override
            public void onAdPresent() {
//                Log.i("RSplashActivity", "onAdPresent");
            }

            @Override
            public void onAdClick() {
//                Log.i("RSplashActivity", "onAdClick");
                // ���ÿ����ɽ��ܵ��ʱ���ûص�����
            }
        };
//        2398946
        String adPlaceId = "2398946"; // ��Ҫ�����������Ĺ��λID������λ����ᵼ���޷����󵽹��
        new SplashAd(this, adsParent, listener, adPlaceId, true);


			

	}
	/**
	 * ���ɵ���Ŀ�����ʹ�ø�jump��������������jumpWhenCanClick
	 */
	private void jump() {
		this.startActivity(new Intent(WelcomActivity.this, MoreActivity.class));
		this.finish();
	}
	
	/**
	 * ��ʱδʹ��
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
