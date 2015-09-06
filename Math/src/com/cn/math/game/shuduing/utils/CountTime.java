package com.cn.math.game.shuduing.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;


/**
 * 记录显示时间并通过时间为游戏评分
 * */
public class CountTime {

	private Canvas _canvas ;	//画布
	private Paint paint ;		//画笔
	private boolean isContinue ; //是否是继续游戏
	private Bitmap bm ;			//背景图画
	
	private float _x ;			//时间 画的位置x
	private float _y ;			// y的位置
	private float _witdh ;
	
	private static long time ;	//时间记录
	private static long time_plus ;	//时间增量
	private String time_str ="00:00:00";	//时间字符串
	
	private static final long LEVEL_ONE = 600000 ;		//三星 耗时
	private static final long LEVEL_ONE_HALF = 900000 ;		//二星半 办耗时
	private static final long LEVEL_TWO = 1200000 ;		//二星 耗时
	private static final long LEVEL_TWO_HALF = 1500000 ;		//一星半 办耗时
	private static final long LEVEL_THREE = 1800000 ;		//一星 耗时
	private static final long LEVEL_THREE_HALF = 2400000 ;		//半星 耗时
	
	public CountTime(Canvas canvas, long time, Bitmap bm, float witdh){
		this._canvas = canvas ;
		this.time = time ;
		paint = new Paint() ;
		paint.setColor(Color.BLUE) ;
		time_plus = 100 ;
		this.bm = bm ; 
		this._witdh = witdh ;
		
	}
	
	public void drawTimer(float x, float y, float textSize){
		this._x = x ;
		this._y = y ;
		paint.setTextSize(textSize) ;
		time+=time_plus ;			//因为每100毫秒画一次 所以每画一次加100毫秒
		paint.setColor(Color.WHITE) ;
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		time_str = getTimeString() ;		//得到当前时间
		paint.setTextAlign(Align.CENTER);
		System.out.println("bm===>"+bm);
		_canvas.drawBitmap(bm, (_witdh-bm.getWidth())/2, _y-bm.getHeight()/4*3, paint) ;				//绘制背景
		_canvas.drawText("计时："+time_str , _witdh/2, _y, paint) ;	//绘制文字
	}
	private String getTimeString(){
		return (""+time/36000000+(time-time/36000000*36000000)/3600000+":"+(time%3600000)/600000+(time%3600000-(time%3600000)/600000*600000)/60000+":"+(time%60000)/10000+(time%60000-(time%60000)/10000*10000)/1000) ;
	}
	
	public float getRating(){
		time_plus = 0 ;
		if(time<=LEVEL_ONE)
			return 3f ;
		if(time<=LEVEL_ONE_HALF)
			return 2.5f ;
		if(time<=LEVEL_TWO)
			return 2f ;
		if(time<=LEVEL_TWO_HALF)
			return 1.5f ;
		if(time<=LEVEL_THREE)
			return 1f ;
		if(time<=LEVEL_THREE_HALF)
			return 0.5f ;
		return 0f ;
		
	}
	
	public long getTime(){
		return time ;
	}
	
	public void resetGame(){
		time = 0 ;
	}
	
}
