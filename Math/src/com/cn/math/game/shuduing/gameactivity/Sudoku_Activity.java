package com.cn.math.game.shuduing.gameactivity;


import com.cn.math.game.shuduing.utils.Sudoku_View;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;



public class Sudoku_Activity extends Activity {

	private Sudoku_View sv ;   		//数独界面
	private int _width ;		//给予的宽度
	private int _height ;		//给予的高度
	private float distance ;	//数独盘面的宽度
	private int stage ;			//关数
	private boolean isContinue ;//是否继续的游戏s
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		//取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//全屏模式
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//强制横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) ;
		
		//取得屏幕的宽高
		_width = getWindowManager().getDefaultDisplay().getWidth() ;
		_height = getWindowManager().getDefaultDisplay().getHeight() ;
		
		stage = getIntent().getIntExtra("stage", -1) ;
		isContinue = getIntent().getBooleanExtra("action", false) ;
		//
//		Handler handler = new Handler(){
//			public void handleMessage(Message m){
//				if(Sudoku_View._pdc!=null)
//				Sudoku_View._pdc.myDismiss();
//			}
//		} ;
		Handler handler = new Handler() ;
		sv = new Sudoku_View(this, _width, _height, stage, isContinue, handler) ;
//		sv.setBackgroundColor(Color.argb(225, 0, 225, 100)) ;
		setContentView(sv) ;
	}

	//把触摸时间交给view 处理
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if(sv!=null){
			return sv.onTouchEvent(event) ;
		}
		return super.onTouchEvent(event) ;
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		//当界面暂停时 ，就立刻让游戏存储数据
		sv.saveThisGame() ;	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==4){
			finish() ;
		}
	}
	
	
	
	
}
