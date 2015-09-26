package com.cn.math;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class WelcomActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		
		new Thread(new MyThread()).start();
		
	}
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
