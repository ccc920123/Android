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
 * ����
 * 
 * �ܻ������ȱ���������� ��
 * 
 *  @author Jason
 * */
public class Sudoku {

	private static int _width ;		//��Ļ�Ŀ�
	private static int _height ;	//��Ļ�ĸ�
	
	private Context _context ;		//��̬������
	private Canvas _canvas ; 		//����
	private Matrix _matrix ;
	private float _left ;			//���ȿؼ����Ͻ� x����
	private float _top ;			//�ٶȿռ����Ͻ�y����
	private float _distance ;		//���ȱ����
	private ArrayList<String[]> _numList ;  //��Ҫ���������ֵ����� 
	private ArrayList<String[]> _defaultNumList ; //Ĭ�ϸ��������ֵ�����
	private ArrayList<String> _havingNum ;			//Ĭ�Ϻ��е�����λ��
	private boolean checkin = true ;				//�Ƿ�Ӧ�ü�¼��Ĭ�����ֵ�λ��    �ü�¼����ֻ�ڵ�һ�ν���ʱִ��һ��
	private boolean isClickRight ;					//�Ƿ�����Ĭ��ӵ�е�������  true �����Ĭ��������
	private static final String DEFAULT_NULL = "0" ;	//ArrayList��Ĭ�ϵĿ�λ��
	private float _perDis ;			//ÿ��С��Ŀ��
	
	private float click_x ;			//���x
	private float click_y ;			//���y
	
	private int click_x_num ;		//�����x������ĸ���x�±�
	private int click_y_num ;		//�����x������ĸ���y�±�
	
	private static boolean click_boolean = false ; //Ч������С�����Ƿ���ʾ
	
	private static float drawRect_x ;		//Ч���������Ͻ� x
	private static float drawRect_y ;		//Ч������ ���Ͻ�y
	private static float drawRect_x_b ;		//Ч������ ���Ͻ�x
	private static float drawRect_y_b ;		//Ч������ ���Ͻ�y
	
	private KeyboardOverlay kvo ;		//�����
	private static float KEYBOARD_SIZE = 100 ;	//����̵�size���
	private static float keyboard_x ;		//����̵�X����
	private static float keyboard_y ;		//����̵�y����
	
	private float drawNum_x ;		//�������ֵ�x����
	private float drawNum_y ;		//�������ֵ�y����
	private String drawNum_v ;		//���Ƶ�����
	
	private Paint _paint ;	//���ֵĻ��� 
	private static float TEXTSIZE ;  		//���ֵĴ�С
	
	private static final String NUM_X = "x" ;	//x������contentvalues�е�key
	private static final String NUM_Y = "y" ;	//y������contentvalues�е�key
	private static final String NUM_V = "value" ;	//value������contentvalues�е�key
	
	//������������
	private int  color ;
	private Paint paint_line ;
	float xiantiao ;
	private Bitmap bm_num_bg ;
	private Bitmap bm_num_bg_2 ;
	//
	private float sz_bg_x ;
	private float sz_bg_y ;
	
	/**
	 * Sudoku�Ĺ��캯��
	 * @param Canvas canvas ������float left �ؼ����Ͻ�x���꣬float top �ؼ����Ͻǵ�y���꣬float distance �ؼ��Ŀ�ȣ��˿ؼ�Ϊ�����Σ���
	 * @param ArrayList<ContentValues> defaultNumList Ĭ�ϸ�������ֵ����� contentvalues��Index����0��"x" x��ĸ�����Index����1��"y" y��ĸ���,Index����2��"value" ��Ҫ��д�����ֵ�String
	 * @param  int width, int height ��Ļ�Ŀ��
	 * */
	public Sudoku(Canvas canvas, float left, float top , float distance, ArrayList<String[]> defaultNumList, int width, int height, Context context, Matrix matrix){
		//ȡ�ø������� canvas �ؼ����Ƶ�λ�ã����Ͻ����� (left,top)��
		this._canvas = canvas ;
		this._context = context ;
		this._matrix = matrix ;
		this._left = left ;
		this._top = top ;
		this._distance = distance ;
		//�õ�С����Ŀ��
		_perDis = _distance/9 ;
		//�ֵĴ�СΪС�����2/3
		TEXTSIZE = 2*_perDis/3 ;	
		//�õ�Ĭ������
		this._defaultNumList = defaultNumList ;
		//�õ�����ʵ��
		_paint = new Paint() ;
		_paint.setAntiAlias(true) ;				//�����
		//�õ���Ļ�Ŀ��
		_width = width ;
		_height = height ;
		//С���̵�size
		KEYBOARD_SIZE = _width/3 ;
//		keyboard_x = _witdh - KEYBOARD_SIZE ;
//		keyboard_y = _height/2 ;
		//�õ�_havingNum ��ʵ��
		_havingNum = new ArrayList<String> () ;
		//���ֱ���
		bm_num_bg = ((BitmapDrawable)_context.getResources().getDrawable(R.drawable.sz_bg)).getBitmap() ;
		
		bm_num_bg_2 = ((BitmapDrawable)_context.getResources().getDrawable(R.drawable.sz_bg_2)).getBitmap() ;
		_matrix.setScale((float)(((float)(9*_perDis/10)/bm_num_bg.getWidth())*1f), (float)(((float)(9*_perDis/10)/bm_num_bg.getHeight())*1f)) ;
		bm_num_bg = Bitmap.createBitmap(bm_num_bg, 0, 0, bm_num_bg.getWidth(), bm_num_bg.getHeight(), _matrix, true) ;
		bm_num_bg_2 = Bitmap.createBitmap(bm_num_bg_2, 0, 0, bm_num_bg_2.getWidth(), bm_num_bg_2.getHeight(), _matrix, true) ;
		_matrix.setScale((float)(((float)_width/480)*1f), (float)(((float)_height/800)*1f)) ;
		//����ͼƬ������
		sz_bg_x = _left + (float)_distance/200 ;
		sz_bg_y = _top + (float)_distance/200 ;
		System.out.println("bm_num_bg1+++"+bm_num_bg);
	}
	
	/**
	 * ����ͼ�� �������ֵ����� �������������
	 * @param ArrayList<ContentValues> numList ����д���ֵ����� contentvalues��Index����0��"x" x��ĸ�����Index����1��"y" y��ĸ���,Index����2��"value" ��Ҫ��д�����ֵ�String
	 * */
	public void sudokuDraw(ArrayList<String[]> numList){
		this._numList = numList ;   //ȡ����Ҫ���Ƶ����ֵ�����
		
		//������ �������Ͻ�
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
		
		
		
		//�������С���õ����Ӵ�С��2/3
		_paint.setTextSize(TEXTSIZE) ;
		
		//����Ĭ�ϸ���������
		_paint.setColor(Color.argb(200, 255, 128, 0)) ;
		
		//����Ĭ�����ֲ���¼����
			for(int i=1;i<10;i++){
				for(int j=1;j<10;j++){
	
					drawNum_x = (float)(_left + (i*_perDis) - (2*_perDis/3)) ;
					drawNum_y = (float)(_top + (j*_perDis) - (_perDis/4)) ;
					drawNum_v = (_defaultNumList.get(i))[j].equals(DEFAULT_NULL) ? "" :  (_defaultNumList.get(i))[j] ;
					if(!((_defaultNumList.get(i))[j].equals(DEFAULT_NULL))&&checkin){
						_havingNum.add(""+i+j) ;
						//��¼��� �´β��ٽ���
					}
					
					if(!drawNum_v.equals("")){
						System.out.println("bm_num_bg2+++"+bm_num_bg);
						_canvas.drawBitmap(bm_num_bg, sz_bg_x + (i*_perDis) - _perDis , sz_bg_y + (j*_perDis) - _perDis , _paint) ;
					}
						_canvas.drawText(drawNum_v, drawNum_x, drawNum_y, _paint) ;
				}
			}
			checkin = false ;
		
		//���������д������
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
			//����ѡ��Ч������
			_paint.setColor(Color.argb(100, 255, 128, 0)) ;
			_canvas.drawRect(drawRect_x, drawRect_y, drawRect_x_b, drawRect_y_b, _paint) ;
			
			if(kvo==null)
				kvo = new KeyboardOverlay(_canvas, KEYBOARD_SIZE, _context) ;
			//���Ƴ������
			kvo.myDraw(keyboard_x, keyboard_y) ;
		}
		
	}
	
	/**
	 * ͨ������
	 * */
	public boolean setXY(float x , float y){
		this.click_x = x - _left ;
		this.click_y = y - _top ;
		
		click_x_num = (int) (click_x/_perDis)+1 ;  //+1 ����Ϊ����������дλ�õ�ʱ�����������,����������Ҫ��һ��,
		click_y_num = (int) (click_y/_perDis)+1 ;	//����ת��ΪINT��ʱ��ɥʧ����,��������� 0��1��2��3��4������
		
		drawRect_x	= _left+(click_x_num-1)*_perDis ;	//�õ�����Ч������
		drawRect_y  = _top+(click_y_num-1)*_perDis ;	//��left top right bottom
		drawRect_x_b = _left+(click_x_num)*_perDis ;	//
		drawRect_y_b = _top+(click_y_num)*_perDis ;		//
		
		keyboard_x = _left+(click_x_num+1)*_perDis ;	//����̵�x����
		keyboard_y = drawRect_y ;						//����̵�y����     ������������Sudoku�����߶�
		keyboard_x = keyboard_x>(_width-KEYBOARD_SIZE) ? (_width-KEYBOARD_SIZE) : keyboard_x ;	//��������̲�����
		keyboard_y = keyboard_y>(_height-KEYBOARD_SIZE) ? (_height-KEYBOARD_SIZE) : keyboard_y ;	//��������̲�����
		
	
		
		//�жϵ�����Ƿ�����ȷ����Ĭ������
		for(int i=0;i<_havingNum.size();i++){
			
			isClickRight = _havingNum.get(i).equals(""+click_x_num+click_y_num) ;
			if(isClickRight){
				break ;
			}
		}
		
		//����õ���click_x����click_y������������ڿؼ�ԭ��������룩С��0  ����click_x_num,click_y_num��������С��1����9����ôû��������������ڣ����Բ���ʾЧ������
		if(click_x_num<1||click_y_num<1||click_x_num>9||click_y_num>9||click_x<0||click_y<0||isClickRight){
			//��ʼ��click_x click_y
			this.click_x = -1 ;
			this.click_y = -1 ;
			//��ʼ��click_x_num click_y_num
			click_x_num = 0 ;
			click_y_num = 0 ;
			//����ʾЧ������ �� С����
			click_boolean = false ;
		}else{
			//��ʾЧ������ �� С����
			click_boolean = true ;
		}
		
		return click_boolean;
		
	}
	
	/**
	 * ���ظ�������x
	 * */
	public int getBOX_X(){
		return click_x_num;
	}
	/**
	 * ���ظ�������y
	 * */
	public int getBOX_Y(){
		return click_y_num;
	}
	
	/**
	 * ͨ������õ�С���̵����
	 * */
	public int setKeyboardXY(float x , float y){
		if(kvo==null)
			return 0 ;
		click_boolean = false ;
		return kvo.setXY(x , y);
	}
	
	
}
