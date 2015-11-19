package cn.com.shuduwang.utils;

import android.graphics.Canvas;
import android.graphics.Paint;


/**
 * ��¼��ʾʱ�䲢ͨ��ʱ��Ϊ��Ϸ����
 * */
public class CountTimeForRating {

	private Canvas _canvas ;	//����
	private Paint paint ;		//����
	
	private float _x ;			//ʱ�� ����λ��x
	private float _y ;			// y��λ��
	
	private static long time ;	//ʱ���¼
	private String time_str ="00:00:00";
	
	public CountTimeForRating(Canvas canvas){
		this._canvas = canvas ;
		paint = new Paint() ;
	}
	
	public void drawTimer(float x, float y){
		this._x = x ;
		this._y = y ;
		
		_canvas.drawText("ʱ�䣺"+time_str , _x, _y, paint) ;
	}
	
	
	
}
