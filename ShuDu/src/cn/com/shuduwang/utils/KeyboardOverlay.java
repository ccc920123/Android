package cn.com.shuduwang.utils;

import java.util.ArrayList;

import cn.com.shuduwang.R;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;


/**
 * �����
 * @author jason
 * */
public class KeyboardOverlay {

	private Canvas _canvas ;		//����
	private Context _context ;		//��̬������
	
	private Matrix matrix ;			//Matrix ���Ե���ͼƬ������Ӧ
	private float f = 1.0f ;		//f��С���� �����ԭ����СX*Y��480*854
	private Bitmap bm_kb ;			//����ͼƬ
	private Paint _paint ;
	
	private float _left ;			//keyboard�����Ͻ�x����
	private float _top ;			//keyboard�����Ͻ�y����
	private float _size ;		//keyboard�Ŀ��
	private float _perDis ;		//keyboardÿ�����ӵĿ��
	
	private float drawNum_x ;		//�������ֵ�x����
	private float drawNum_y ;		//�������ֵ�y����
	private String drawNum_v ;		//���Ƶ�����
	private static float TEXTSIZE ;  		//���ֵĴ�С
	
	private static final String NUM_X = "x" ;	//x������contentvalues�е�key
	private static final String NUM_Y = "y" ;	//y������contentvalues�е�key
	private static final String NUM_V = "value" ;	//value������contentvalues�е�key
	
	private float click_x ;			//����¼�x
	private float click_y ;			//����¼�y
	
	private int click_x_num ;		//�����x������ĸ���x�±�
	private int click_y_num ;		//�����x������ĸ���y�±�
	
	private static ArrayList<ContentValues> numList = new ArrayList<ContentValues>(); //������������
	//װ��1-9�����Ӧ��λ��
	static{
		int value = 0 ;
		for(int i=1;i<4;i++){
			for(int j=1;j<4;j++){
				ContentValues values = new ContentValues() ;
				values.put(NUM_X, j) ;
				values.put(NUM_Y, i) ;
				values.put(NUM_V, ++value) ;
				numList.add(values) ;
			}
		}
	}
	
	/**
	 * keyboard�Ĺ��캯��
	 * @param Canvas canvas ��̬������ 
	 * */
	public KeyboardOverlay(Canvas canvas, float size, Context context) {
		this._canvas = canvas ;	//ȡ�û���
		this._size = size ;		//ȡ������̿��
		this._context = context ;
		
		//ȡ��ͼƬ
		bm_kb = ((BitmapDrawable)_context.getResources().getDrawable(R.drawable.bm_kb)).getBitmap() ;
		//ʹ��Matrixȷ��ͼƬ��������
		matrix = new Matrix() ;
		matrix.setScale((float)(((float)size/bm_kb.getWidth())*f), (float)(((float)size/bm_kb.getHeight())*f)) ;
		
		bm_kb = Bitmap.createBitmap(bm_kb, 0, 0, bm_kb.getWidth(), bm_kb.getHeight(), matrix, true) ;
		
		_paint = new Paint() ;	//�õ�����ʵ��
		
		_perDis = _size/3 ;		//���ÿ�����ӵĿ��
		
		TEXTSIZE = 2*_perDis/3 ; //�ֵĴ�СΪС�����2/3
	}

	/**
	 *���������
	 *@param x ����̵����Ͻǵ�x���꣬y ����̵����Ͻǵ�y����
	 * */
	public void myDraw(float x, float y) {
		this._left = x ;
		this._top = y ;
		_paint.setAntiAlias(true) ;				//���ÿ����
		//���������
//		_paint.setColor(Color.argb(255, 100, 0, 0)) ;
		_canvas.drawBitmap(bm_kb, _left, _top, _paint) ;					//��������
		_paint.setStyle(Style.STROKE) ;									//����Ϊ����
		
		float xiantiao = _paint.getStrokeWidth() ;
		_paint.setStrokeWidth(4) ;
		_paint.setColor(Color.argb(255, 0, 200, 200)) ;
		_canvas.drawRect(_left, _top, _left+_size, _top+_size, _paint) ;
		_paint.setStrokeWidth(xiantiao) ;
//		_paint.setColor(Color.BLACK) ;
		_canvas.drawLine(_left+(_size/3), _top, _left+(_size/3), _top+_size, _paint) ;  //��һ������
		_canvas.drawLine(_left+(2*_size/3), _top, _left+(2*_size/3), _top+_size, _paint) ;  //�ڶ�������
		_canvas.drawLine(_left, _top+_size/3, _left+_size, _top+_size/3, _paint) ;  //��һ������
		_canvas.drawLine(_left, _top+(2*_size/3), _left+_size, _top+(2*_size/3), _paint) ;  //�ڶ�������
		//�������ֵ���ɫ
		_paint.setColor(Color.MAGENTA) ;
		//һ��д��1-9
		for(ContentValues c:numList){
			//�趨�������������
			drawNum_x = (float)(_left + (c.getAsInteger(NUM_X)*_perDis) - (2*_perDis/3)) ;
			drawNum_y = (float)(_top + (c.getAsInteger(NUM_Y)*_perDis) - (_perDis/4)) ;
			drawNum_v = c.getAsString(NUM_V) ;
			
			_paint.setTextSize(TEXTSIZE) ;								//���������С
			
			_canvas.drawText(drawNum_v, drawNum_x, drawNum_y, _paint) ;	//��������
		}
	}
	
	/**
	 * ���С����С���� ͨ�����λ��xy��ȡ����
	 * */
	public int setXY(float x , float y){
		
		click_x = x - _left ;
		click_y = y - _top ;
		
		//����������������򷵻�����
		if(click_x>0&&click_y>0&&click_x<_left+_size&&click_x<_top+_size){
			
			click_x_num = (int) (click_x/_perDis)+1 ;  //+1 ����Ϊ����������дλ�õ�ʱ�����������,����������Ҫ��һ��,
			click_y_num = (int) (click_y/_perDis)+1 ;	//����ת��ΪINT��ʱ��ɥʧ����,��������� 0��1��2��3��4������
			
			//ͨ���������귵������
			switch(click_x_num){
			case 1:
				switch(click_y_num){
				case 1: 
					return 1;
				case 2:
					return 4;
				case 3:
					return 7;
				} 
				break;
			case 2:
				switch(click_y_num){
				case 1: 
					return 2;
				case 2:
					return 5;
				case 3:
					return 8;
				} 
				break;
			case 3:
				switch(click_y_num){
				case 1: 
					return 3;
				case 2:
					return 6;
				case 3:
					return 9;
				} 
				break;
			}
		}
		//�������С������ �򷵻�0
		return 0;
		
	}
	
	
	

}
