package cn.com.shuduwang.utils;

import android.graphics.Canvas;
import android.graphics.Paint;


/**
 * 记录显示时间并通过时间为游戏评分
 * */
public class CountTimeForRating {

	private Canvas _canvas ;	//画布
	private Paint paint ;		//画笔
	
	private float _x ;			//时间 画的位置x
	private float _y ;			// y的位置
	
	private static long time ;	//时间记录
	private String time_str ="00:00:00";
	
	public CountTimeForRating(Canvas canvas){
		this._canvas = canvas ;
		paint = new Paint() ;
	}
	
	public void drawTimer(float x, float y){
		this._x = x ;
		this._y = y ;
		
		_canvas.drawText("时间："+time_str , _x, _y, paint) ;
	}
	
	
	
}
