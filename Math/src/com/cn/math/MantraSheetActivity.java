package com.cn.math;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;

public class MantraSheetActivity extends Activity {

	
	private ImageView image;
	private RadioButton radio1,radio2;
	PowerManager.WakeLock mWakeLock;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mantrasheet);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag"); 
		// in onResume() call
		
//		mWakeLock.acquire(); 
		// in onPause() call 
//		mWakeLock.release();
		image=(ImageView) findViewById(R.id.image);
		radio1=(RadioButton) findViewById(R.id.homePage);
		radio2=(RadioButton) findViewById(R.id.brand);
		radio1.setOnClickListener(Click);
		radio2.setOnClickListener(Click);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode==KeyEvent.KEYCODE_BACK)
	{
		Intent itt=new Intent();
		itt.setClass(MantraSheetActivity.this, MainActivity.class);
		this.startActivity(itt);
		finish();
	}
		return false;
	}
	
	private OnClickListener Click=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v==radio1)
			{
				image.setBackgroundDrawable(getResources().getDrawable(R.drawable.addrom));
			}else{
				image.setBackgroundDrawable(getResources().getDrawable(R.drawable.addsc));
			}
			
		}
	};



	@Override
	protected void onResume() {
		mWakeLock.acquire();
		super.onResume();
	}


	@Override
	protected void onPause() {
		mWakeLock.release();
		super.onPause();
	}
}
