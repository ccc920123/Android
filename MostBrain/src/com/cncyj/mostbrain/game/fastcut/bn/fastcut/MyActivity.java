package com.cncyj.mostbrain.game.fastcut.bn.fastcut;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.cncyj.mostbrain.MoreActivity;
import com.cncyj.mostbrain.R;
import com.cncyj.mostbrain.game.fastcut.bn.util.constant.Constant;
import com.cncyj.mostbrain.game.fastcut.bn.util.manager.SoundManager;
import com.cncyj.mostbrain.game.fastcut.bn.util.screenscale.ScreenScaleUtil;

public class MyActivity extends Activity 
{
	private MySurfaceView mGLSurfaceView;
	public SoundManager sm;//声音管理类
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//设置为竖屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constant.ssr=ScreenScaleUtil.calScale(dm.widthPixels, dm.heightPixels);
        sm=new SoundManager(this);//声音管理类
        
		//初始化GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);
        setContentView(mGLSurfaceView);	
        mGLSurfaceView.requestFocus();//获取焦点
        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控  
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }  
//    @Override
//    public void onBackPressed() {
//    	Intent itt=new Intent();
//    	itt.setClass(MyActivity.this,MoreActivity.class);
//		startActivity(itt);
//		
//		finish();
//    }
    @Override
    public void onBackPressed() {
//    	// TODO Auto-generated method stub
//    	super.onBackPressed();
    }
    
    @Override
    protected void onDestroy() {
    	sm.EndBackGroundSound();//关闭音效
    	super.onDestroy();
    }
}



