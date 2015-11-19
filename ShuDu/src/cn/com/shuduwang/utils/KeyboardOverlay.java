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
 * 软键盘
 * @author jason
 * */
public class KeyboardOverlay {

	private Canvas _canvas ;		//画布
	private Context _context ;		//静态上下文
	
	private Matrix matrix ;			//Matrix 用以调控图片的自适应
	private float f = 1.0f ;		//f大小比例 想对于原画大小X*Y：480*854
	private Bitmap bm_kb ;			//键盘图片
	private Paint _paint ;
	
	private float _left ;			//keyboard的左上角x坐标
	private float _top ;			//keyboard的左上角y坐标
	private float _size ;		//keyboard的宽度
	private float _perDis ;		//keyboard每个格子的宽度
	
	private float drawNum_x ;		//绘制数字的x坐标
	private float drawNum_y ;		//绘制数字的y坐标
	private String drawNum_v ;		//绘制的数字
	private static float TEXTSIZE ;  		//数字的大小
	
	private static final String NUM_X = "x" ;	//x坐标在contentvalues中的key
	private static final String NUM_Y = "y" ;	//y坐标在contentvalues中的key
	private static final String NUM_V = "value" ;	//value坐标在contentvalues中的key
	
	private float click_x ;			//点击事件x
	private float click_y ;			//点击事件y
	
	private int click_x_num ;		//点击的x所代表的格子x下表
	private int click_y_num ;		//点击的x所代表的格子y下表
	
	private static ArrayList<ContentValues> numList = new ArrayList<ContentValues>(); //建立数字容器
	//装载1-9和其对应的位置
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
	 * keyboard的构造函数
	 * @param Canvas canvas 静态上下文 
	 * */
	public KeyboardOverlay(Canvas canvas, float size, Context context) {
		this._canvas = canvas ;	//取得画布
		this._size = size ;		//取得软键盘宽度
		this._context = context ;
		
		//取得图片
		bm_kb = ((BitmapDrawable)_context.getResources().getDrawable(R.drawable.bm_kb)).getBitmap() ;
		//使用Matrix确定图片与界面比例
		matrix = new Matrix() ;
		matrix.setScale((float)(((float)size/bm_kb.getWidth())*f), (float)(((float)size/bm_kb.getHeight())*f)) ;
		
		bm_kb = Bitmap.createBitmap(bm_kb, 0, 0, bm_kb.getWidth(), bm_kb.getHeight(), matrix, true) ;
		
		_paint = new Paint() ;	//得到画笔实例
		
		_perDis = _size/3 ;		//算出每个格子的宽度
		
		TEXTSIZE = 2*_perDis/3 ; //字的大小为小方格的2/3
	}

	/**
	 *画出软键盘
	 *@param x 软键盘的左上角的x坐标，y 软键盘的左上角的y坐标
	 * */
	public void myDraw(float x, float y) {
		this._left = x ;
		this._top = y ;
		_paint.setAntiAlias(true) ;				//设置抗锯齿
		//画除软键盘
//		_paint.setColor(Color.argb(255, 100, 0, 0)) ;
		_canvas.drawBitmap(bm_kb, _left, _top, _paint) ;					//画出背景
		_paint.setStyle(Style.STROKE) ;									//设置为空心
		
		float xiantiao = _paint.getStrokeWidth() ;
		_paint.setStrokeWidth(4) ;
		_paint.setColor(Color.argb(255, 0, 200, 200)) ;
		_canvas.drawRect(_left, _top, _left+_size, _top+_size, _paint) ;
		_paint.setStrokeWidth(xiantiao) ;
//		_paint.setColor(Color.BLACK) ;
		_canvas.drawLine(_left+(_size/3), _top, _left+(_size/3), _top+_size, _paint) ;  //第一个竖线
		_canvas.drawLine(_left+(2*_size/3), _top, _left+(2*_size/3), _top+_size, _paint) ;  //第二个竖线
		_canvas.drawLine(_left, _top+_size/3, _left+_size, _top+_size/3, _paint) ;  //第一个横线
		_canvas.drawLine(_left, _top+(2*_size/3), _left+_size, _top+(2*_size/3), _paint) ;  //第二个横线
		//设置数字的颜色
		_paint.setColor(Color.MAGENTA) ;
		//一次写入1-9
		for(ContentValues c:numList){
			//设定数字坐标和内容
			drawNum_x = (float)(_left + (c.getAsInteger(NUM_X)*_perDis) - (2*_perDis/3)) ;
			drawNum_y = (float)(_top + (c.getAsInteger(NUM_Y)*_perDis) - (_perDis/4)) ;
			drawNum_v = c.getAsString(NUM_V) ;
			
			_paint.setTextSize(TEXTSIZE) ;								//设置字体大小
			
			_canvas.drawText(drawNum_v, drawNum_x, drawNum_y, _paint) ;	//绘制数字
		}
	}
	
	/**
	 * 点击小键盘小键盘 通过点击位置xy获取数字
	 * */
	public int setXY(float x , float y){
		
		click_x = x - _left ;
		click_y = y - _top ;
		
		//如果点击在软键盘中则返回数字
		if(click_x>0&&click_y>0&&click_x<_left+_size&&click_x<_top+_size){
			
			click_x_num = (int) (click_x/_perDis)+1 ;  //+1 是因为我算数字填写位置的时候会减间隔距离,所以这里需要加一格,
			click_y_num = (int) (click_y/_perDis)+1 ;	//而且转换为INT型时会丧失精度,因而格数是 0，1，2，3，4···
			
			//通过格子坐标返回数字
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
		//点击不再小键盘中 则返回0
		return 0;
		
	}
	
	
	

}
