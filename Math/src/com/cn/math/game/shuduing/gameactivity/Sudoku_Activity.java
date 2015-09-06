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

	private Sudoku_View sv ;   		//��������
	private int _width ;		//����Ŀ��
	private int _height ;		//����ĸ߶�
	private float distance ;	//��������Ŀ��
	private int stage ;			//����
	private boolean isContinue ;//�Ƿ��������Ϸs
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		//ȡ��������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȫ��ģʽ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//ǿ�ƺ���
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) ;
		
		//ȡ����Ļ�Ŀ��
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

	//�Ѵ���ʱ�佻��view ����
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
		//��������ͣʱ ������������Ϸ�洢����
		sv.saveThisGame() ;	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==4){
			finish() ;
		}
	}
	
	
	
	
}
