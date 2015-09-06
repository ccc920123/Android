package com.cn.math.game.shuduing.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;


/**
 * ��¼��ʾʱ�䲢ͨ��ʱ��Ϊ��Ϸ����
 * */
public class CountTime {

	private Canvas _canvas ;	//����
	private Paint paint ;		//����
	private boolean isContinue ; //�Ƿ��Ǽ�����Ϸ
	private Bitmap bm ;			//����ͼ��
	
	private float _x ;			//ʱ�� ����λ��x
	private float _y ;			// y��λ��
	private float _witdh ;
	
	private static long time ;	//ʱ���¼
	private static long time_plus ;	//ʱ������
	private String time_str ="00:00:00";	//ʱ���ַ���
	
	private static final long LEVEL_ONE = 600000 ;		//���� ��ʱ
	private static final long LEVEL_ONE_HALF = 900000 ;		//���ǰ� ���ʱ
	private static final long LEVEL_TWO = 1200000 ;		//���� ��ʱ
	private static final long LEVEL_TWO_HALF = 1500000 ;		//һ�ǰ� ���ʱ
	private static final long LEVEL_THREE = 1800000 ;		//һ�� ��ʱ
	private static final long LEVEL_THREE_HALF = 2400000 ;		//���� ��ʱ
	
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
		time+=time_plus ;			//��Ϊÿ100���뻭һ�� ����ÿ��һ�μ�100����
		paint.setColor(Color.WHITE) ;
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		time_str = getTimeString() ;		//�õ���ǰʱ��
		paint.setTextAlign(Align.CENTER);
		System.out.println("bm===>"+bm);
		_canvas.drawBitmap(bm, (_witdh-bm.getWidth())/2, _y-bm.getHeight()/4*3, paint) ;				//���Ʊ���
		_canvas.drawText("��ʱ��"+time_str , _witdh/2, _y, paint) ;	//��������
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
