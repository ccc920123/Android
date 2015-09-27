package com.cn.math.game.shuduing.utils;

import java.util.ArrayList;

import com.cn.math.R;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas ;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint ;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;


/**
 * 数独
 * 
 * 能画除数度表框，填入数字 ，
 * 
 *  @author Jason
 * */
public class Sudoku {

	private static int _width ;		//屏幕的宽
	private static int _height ;	//屏幕的高
	
	private Context _context ;		//静态上下文
	private Canvas _canvas ; 		//画布
	private Matrix _matrix ;
	private float _left ;			//数度控件左上角 x坐标
	private float _top ;			//速度空件左上角y坐标
	private float _distance ;		//数度表格宽度
	private ArrayList<String[]> _numList ;  //需要画出的数字的容器 
	private ArrayList<String[]> _defaultNumList ; //默认给定的数字的容器
	private ArrayList<String> _havingNum ;			//默认含有的数字位置
	private boolean checkin = true ;				//是否应该记录下默认数字的位置    让记录工作只在第一次进入时执行一次
	private boolean isClickRight ;					//是否点击在默认拥有的数字上  true 点击在默认数字上
	private static final String DEFAULT_NULL = "0" ;	//ArrayList中默认的空位子
	private float _perDis ;			//每个小格的宽度
	
	private float click_x ;			//点击x
	private float click_y ;			//点击y
	
	private int click_x_num ;		//点击的x所代表的格子x下表
	private int click_y_num ;		//点击的x所代表的格子y下表
	
	private static boolean click_boolean = false ; //效果方框及小键盘是否显示
	
	private static float drawRect_x ;		//效果方框左上角 x
	private static float drawRect_y ;		//效果方框 左上角y
	private static float drawRect_x_b ;		//效果方框 右上角x
	private static float drawRect_y_b ;		//效果方框 右上角y
	
	private KeyboardOverlay kvo ;		//软键盘
	private static float KEYBOARD_SIZE = 100 ;	//软键盘的size宽度
	private static float keyboard_x ;		//软键盘的X坐标
	private static float keyboard_y ;		//软键盘的y坐标
	
	private float drawNum_x ;		//绘制数字的x坐标
	private float drawNum_y ;		//绘制数字的y坐标
	private String drawNum_v ;		//绘制的数字
	
	private Paint _paint ;	//数字的画笔 
	private static float TEXTSIZE ;  		//数字的大小
	
	private static final String NUM_X = "x" ;	//x坐标在contentvalues中的key
	private static final String NUM_Y = "y" ;	//y坐标在contentvalues中的key
	private static final String NUM_V = "value" ;	//value坐标在contentvalues中的key
	
	//绘制数独盘面
	private int  color ;
	private Paint paint_line ;
	float xiantiao ;
	private Bitmap bm_num_bg ;
	private Bitmap bm_num_bg_2 ;
	//
	private float sz_bg_x ;
	private float sz_bg_y ;
	
	/**
	 * Sudoku的构造函数
	 * @param Canvas canvas 画布，float left 控件左上角x坐标，float top 控件左上角的y坐标，float distance 控件的宽度（此控件为正方形），
	 * @param ArrayList<ContentValues> defaultNumList 默认给入的数字的容器 contentvalues中Index——0："x" x轴的格数，Index——1："y" y轴的格数,Index——2："value" 需要填写的数字的String
	 * @param  int width, int height 屏幕的宽高
	 * */
	public Sudoku(Canvas canvas, float left, float top , float distance, ArrayList<String[]> defaultNumList, int width, int height, Context context, Matrix matrix){
		//取得给入数据 canvas 控件绘制的位置（左上角坐标 (left,top)）
		this._canvas = canvas ;
		this._context = context ;
		this._matrix = matrix ;
		this._left = left ;
		this._top = top ;
		this._distance = distance ;
		//得到小方格的宽度
		_perDis = _distance/9 ;
		//字的大小为小方格的2/3
		TEXTSIZE = 2*_perDis/3 ;	
		//得到默认数字
		this._defaultNumList = defaultNumList ;
		//得到画笔实例
		_paint = new Paint() ;
		_paint.setAntiAlias(true) ;				//抗锯齿
		//得到屏幕的宽高
		_width = width ;
		_height = height ;
		//小键盘的size
		KEYBOARD_SIZE = _width/3 ;
//		keyboard_x = _witdh - KEYBOARD_SIZE ;
//		keyboard_y = _height/2 ;
		//得到_havingNum 的实例
		_havingNum = new ArrayList<String> () ;
		//数字背景
		bm_num_bg = ((BitmapDrawable)_context.getResources().getDrawable(R.drawable.sz_bg)).getBitmap() ;
		
		bm_num_bg_2 = ((BitmapDrawable)_context.getResources().getDrawable(R.drawable.sz_bg_2)).getBitmap() ;
		_matrix.setScale((float)(((float)(9*_perDis/10)/bm_num_bg.getWidth())*1f), (float)(((float)(9*_perDis/10)/bm_num_bg.getHeight())*1f)) ;
		bm_num_bg = Bitmap.createBitmap(bm_num_bg, 0, 0, bm_num_bg.getWidth(), bm_num_bg.getHeight(), _matrix, true) ;
		bm_num_bg_2 = Bitmap.createBitmap(bm_num_bg_2, 0, 0, bm_num_bg_2.getWidth(), bm_num_bg_2.getHeight(), _matrix, true) ;
		_matrix.setScale((float)(((float)_width/480)*1f), (float)(((float)_height/800)*1f)) ;
		//绘制图片的坐标
		sz_bg_x = _left + (float)_distance/200 ;
		sz_bg_y = _top + (float)_distance/200 ;
		System.out.println("bm_num_bg1+++"+bm_num_bg);
	}
	
	/**
	 * 绘制图形 给入数字的容器 绘制填入的数字
	 * @param ArrayList<ContentValues> numList 已填写数字的容器 contentvalues中Index——0："x" x轴的格数，Index——1："y" y轴的格数,Index——2："value" 需要填写的数字的String
	 * */
	public void sudokuDraw(ArrayList<String[]> numList){
		this._numList = numList ;   //取得需要绘制的数字的容器
		
		//测试用 绘制左上角
		_paint.setColor(Color.WHITE) ;
		_canvas.drawPoint(_left, _top, _paint) ;
		_canvas.drawLine(_left, _top, _left, _top+_distance, _paint) ;
		_canvas.drawLine(_left, _top, _left+_distance, _top, _paint) ;
		_canvas.drawLine(_left+(_distance/9), _top, _left+(_distance/9),_top+_distance, _paint) ;
		_canvas.drawLine(_left, _top+(_distance/9), _left+_distance, _top+(_distance/9), _paint) ;
		
		for(int i=0;i<10;i++){
			_paint.setColor(Color.WHITE) ;
			if(i%3==0)
				continue ;
			_canvas.drawLine(_left+i*(_distance/9), _top, _left+i*(_distance/9), _top+_distance, _paint) ;
			_canvas.drawLine(_left, _top+i*(_distance/9), _left+_distance, _top+i*(_distance/9), _paint) ;
		}
		
		for(int i=0;i<10;i++){
			if(i%3==0){
				color = _paint.getColor() ;
				_paint.setColor(Color.argb(250, 192, 192, 192)) ;
				xiantiao = _paint.getStrokeWidth() ;
				_paint.setStrokeWidth(4) ;
				_canvas.drawLine(_left+i*(_distance/9), _top, _left+i*(_distance/9), _top+_distance, _paint) ;
				_canvas.drawLine(_left, _top+i*(_distance/9), _left+_distance, _top+i*(_distance/9), _paint) ;
				_paint.setColor(color) ;
				_paint.setStrokeWidth(xiantiao) ;
			}
		}
		
		
		
		//将字体大小设置到格子大小的2/3
		_paint.setTextSize(TEXTSIZE) ;
		
		//绘制默认给定的数字
		_paint.setColor(Color.argb(200, 255, 128, 0)) ;
		
		//画出默认数字并记录下来
			for(int i=1;i<10;i++){
				for(int j=1;j<10;j++){
	
					drawNum_x = (float)(_left + (i*_perDis) - (2*_perDis/3)) ;
					drawNum_y = (float)(_top + (j*_perDis) - (_perDis/4)) ;
					drawNum_v = (_defaultNumList.get(i))[j].equals(DEFAULT_NULL) ? "" :  (_defaultNumList.get(i))[j] ;
					if(!((_defaultNumList.get(i))[j].equals(DEFAULT_NULL))&&checkin){
						_havingNum.add(""+i+j) ;
						//记录完成 下次不再进入
					}
					
					if(!drawNum_v.equals("")){
						System.out.println("bm_num_bg2+++"+bm_num_bg);
						_canvas.drawBitmap(bm_num_bg, sz_bg_x + (i*_perDis) - _perDis , sz_bg_y + (j*_perDis) - _perDis , _paint) ;
					}
						_canvas.drawText(drawNum_v, drawNum_x, drawNum_y, _paint) ;
				}
			}
			checkin = false ;
		
		//绘制玩家填写的数字
		_paint.setColor(Color.argb(255, 2, 147, 235)) ;
		
		for(int i=1;i<10;i++){
			for(int j=1;j<10;j++){
				drawNum_x = (float)(_left + (i*_perDis) - (2*_perDis/3)) ;
				drawNum_y = (float)(_top + (j*_perDis) - (_perDis/4)) ;
				drawNum_v = (_numList.get(i))[j].equals(DEFAULT_NULL) ? "" :  (_numList.get(i))[j];
				if(!drawNum_v.equals(""))
					_canvas.drawBitmap(bm_num_bg_2, sz_bg_x + (i*_perDis) - _perDis , sz_bg_y + (j*_perDis) - _perDis , _paint) ;
				_canvas.drawText(drawNum_v, drawNum_x, drawNum_y, _paint) ;
			}
		}
		
		
		if(click_boolean){
			//绘制选择效果方块
			_paint.setColor(Color.argb(100, 255, 128, 0)) ;
			_canvas.drawRect(drawRect_x, drawRect_y, drawRect_x_b, drawRect_y_b, _paint) ;
			
			if(kvo==null)
				kvo = new KeyboardOverlay(_canvas, KEYBOARD_SIZE, _context) ;
			//绘制出软键盘
			kvo.myDraw(keyboard_x, keyboard_y) ;
		}
		
	}
	
	/**
	 * 通过坐标
	 * */
	public boolean setXY(float x , float y){
		this.click_x = x - _left ;
		this.click_y = y - _top ;
		
		click_x_num = (int) (click_x/_perDis)+1 ;  //+1 是因为我算数字填写位置的时候会减间隔距离,所以这里需要加一格,
		click_y_num = (int) (click_y/_perDis)+1 ;	//而且转换为INT型时会丧失精度,因而格数是 0，1，2，3，4···
		
		drawRect_x	= _left+(click_x_num-1)*_perDis ;	//得到绘制效果方框
		drawRect_y  = _top+(click_y_num-1)*_perDis ;	//的left top right bottom
		drawRect_x_b = _left+(click_x_num)*_perDis ;	//
		drawRect_y_b = _top+(click_y_num)*_perDis ;		//
		
		keyboard_x = _left+(click_x_num+1)*_perDis ;	//软键盘的x坐标
		keyboard_y = drawRect_y ;						//软键盘的y坐标     这两个是随着Sudoku盘面走动
		keyboard_x = keyboard_x>(_width-KEYBOARD_SIZE) ? (_width-KEYBOARD_SIZE) : keyboard_x ;	//限制软键盘不出界
		keyboard_y = keyboard_y>(_height-KEYBOARD_SIZE) ? (_height-KEYBOARD_SIZE) : keyboard_y ;	//限制软键盘不出界
		
	
		
		//判断点击的是否是已确定的默认数字
		for(int i=0;i<_havingNum.size();i++){
			
			isClickRight = _havingNum.get(i).equals(""+click_x_num+click_y_num) ;
			if(isClickRight){
				break ;
			}
		}
		
		//如果得到的click_x或者click_y（点击点坐标于控件原点坐标距离）小于0  或者click_x_num,click_y_num（格数）小于1大于9，那么没点击在数独盘面内，所以不显示效果方框
		if(click_x_num<1||click_y_num<1||click_x_num>9||click_y_num>9||click_x<0||click_y<0||isClickRight){
			//初始化click_x click_y
			this.click_x = -1 ;
			this.click_y = -1 ;
			//初始化click_x_num click_y_num
			click_x_num = 0 ;
			click_y_num = 0 ;
			//不显示效果方框 及 小键盘
			click_boolean = false ;
		}else{
			//显示效果方框 及 小键盘
			click_boolean = true ;
		}
		
		return click_boolean;
		
	}
	
	/**
	 * 返回格子坐标x
	 * */
	public int getBOX_X(){
		return click_x_num;
	}
	/**
	 * 返回格子坐标y
	 * */
	public int getBOX_Y(){
		return click_y_num;
	}
	
	/**
	 * 通过坐标得到小键盘的输出
	 * */
	public int setKeyboardXY(float x , float y){
		if(kvo==null)
			return 0 ;
		click_boolean = false ;
		return kvo.setXY(x , y);
	}
	
	
}
