package cn.com.shuduwang;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

public class AboutgameActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 强制横屏 portrait强制竖屏
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		View view = View.inflate(this, R.layout.aboutgame, null);
		Dialog dialog = new Dialog(this,R.style.dialog);
		dialog.setContentView(view);
		dialog.show();
	}
}