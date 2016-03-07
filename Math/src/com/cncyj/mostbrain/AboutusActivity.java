package com.cncyj.mostbrain;



import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class AboutusActivity extends Activity {
	/** Called when the activity is first created. */
	
	private TextView verstion;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 强制横屏 portrait强制竖屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		verstion=(TextView) findViewById(R.id.verstion);
		
		vsertion();
		
        
	}

	private void vsertion() {
		PackageManager pm = this.getPackageManager();
		PackageInfo info;
		try {
			info = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0);
			verstion.setText("版本号：V"+info.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}