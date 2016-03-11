package com.cncyj.mostbrain.game.kuaifanying;

 
import com.cncyj.mostbrain.MoreActivity;
import com.cncyj.mostbrain.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
 
public class LiveActivity extends Activity {
	public static LiveActivity app;
	public static GameView gameView;
	public static Context context;
	
 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，如：800p）
		Tool.setWH(screenWidth, screenHeight);
		app = this;
		context = this;
		    gameView = new GameView(this);
			setContentView(gameView);
	}
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		gameView.onTouchEvent(event);

		return super.onTouchEvent(event);
	}

	public void Toast(String paramString) {
		if (paramString == "") {
			return;
		}
		Toast.makeText(app, paramString, 0).show();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

 
//    public void showads(){
//    	// 展示插播广告，可以不调用loadSpot独立使用
//    	Tool.Log("showads");
//    	Calendar c = Calendar.getInstance();
//		int year = c.get(Calendar.YEAR);
//		int month = c.get(Calendar.MONTH);
//		int day = c.get(Calendar.DAY_OF_MONTH);
//		if (year == 2015 && month == 0 && day <21) {
//			return;
//		}
//    }
   
   
   public void openUrl(String url){
	   Uri uri = Uri.parse(url);
	   Intent it = new Intent(Intent.ACTION_VIEW,uri);
	   this.startActivity(it);
	   it = null;

   }
   @Override
public void onBackPressed() {
	   Intent itt=new Intent();
	   itt.setClass(LiveActivity.this,MoreActivity.class);
		startActivity(itt);
		finish();
}
   
}
