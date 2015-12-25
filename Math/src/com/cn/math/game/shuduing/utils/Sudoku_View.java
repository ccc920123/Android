package com.cn.math.game.shuduing.utils;

import java.util.ArrayList;

import com.cn.math.MoreActivity;
import com.cn.math.R;
import com.cn.math.dialog.AlerDialog;





import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

/**
 * �����ؼ�
 * @author Jason
 * 
 * */
public class Sudoku_View extends View{
	
	private Context _context ;		//��̬������
	private Handler handler ;
	Paint paint_button ;				//����
	private int _width ;		//����Ŀ��
	private int _height ;		//����ĸ߶�
	
	private Sudoku sudoku ;    //����
	private float left ;	   //������������Ͻ�x����
	private float top ;		   //������������Ͻ�y����
	private float distance ;   //��������Ŀ��
	
	private KeyboardOverlay kvo ; //�����
	private int keyboard_x ;	  //����̵����Ͻǵ�x
	private int keyboard_y ;	  //����̵����Ͻǵ�y
	
	private ArrayList<String[]> defaultNumList ;	//Ĭ�ϸ��������� (TIPS:�Ǵ�1��ʼ�� ��9����)
	private ArrayList<String[]> numList ;			//������������ (TIPS:�Ǵ�1��ʼ�� ��9����)
	private String[] itemList ;					//������
	private static final String DEFAULT_NULL = "0" ;	//ArrayList��Ĭ�ϵĿ�λ��
	private ArrayList<String[]> awsList ; 		//������
	
	private float oldClick_x ;		//����ʱ�����꣬ Ϊ���ж�̧��ʱ�Ƿ�һ��
	private float oldClick_y ;		//���ǲ�һ�£�����Ϊ
	
	private int[] array_xy ;		//װ�ط��صĵ���ĸ���λ��
	private boolean isChooseNum = false ;	// ѡ����������״̬���Ƿ��������״̬ ��
	private int getKeyboard ;
	private int color ;
	
//	private static final float KEYBOARD_SIZE = 100 ;
	
	private float checkButton_x ;	//���Sudoku�ύ�𰸵İ��� X����
	private float checkButton_y ;	//���Sudoku�ύ�𰸵İ��� Y����
	private float checkButton_x_dis ;	//���Sudoku�ύ�𰸵İ��� ��
	private float checkButton_y_dis ;	//���Sudoku�ύ�𰸵İ��� ��
	private float button_text_size ;
	
	private float textsize_x_dis ;
	private float textsize_y_dis ;
	
	private float resetButton_x ;	//����Sudoku�İ��� X����
	private float resetButton_y ;	//����Sudoku�İ��� Y����
	private float resetButton_x_dis ;	//����Sudoku�İ��� ��
	private float resetButton_y_dis ;	//����Sudoku�İ��� ��
	
	private Bitmap bm ;				//��Ϸ����
	private Matrix matrix ; 		//matrix���Ե��ڱ�����С
	private Paint paint_bg ;		//�������ı�
	private float f = 1.0f ;		//f��С���� �����ԭ����СX*Y��480*800
	
	private ContinueSharedPreferences continueShare ;	//��Ϸ�Ĵ洢�� 
	private int _stage ;				//��Ϸ�ؿ���
	private boolean isContinue ;	//�Ƿ��Ǽ�����Ϸ trueΪ�Ǽ�������Ϸ falseΪ����Ϸ
	
	private CountTime timer ;		//��Ϸ��ʱ
	private float timer_x ;			//��Ϸ��ʱ��x����
	private float timer_y ;			//��Ϸ��ʱ��y����
	private long time ;				//��Ϸ���еĳ�ʼʱ�䣨����Ǽ�����Ϸ ������ϴ��˳���ʱ�俪ʼ
	
	private Bitmap time_bm_bg ;		//ʱ�䱳��ͼƬ
	private Bitmap bm_wancheng ;	//��ɰ�ťͼƬ
	private Bitmap bm_chongzhi ;	//���ð�ťͼƬ
	
	private ProgressDialogControler pdc ;  //����֤��ʾ
	private boolean pdcBoolean ;	//����Dialog
	
	private CheckSudoku check ;		//���ж���
	
	/**
	 * ���캯��
	 * @param Context context ��̬������ ,  int width, int height ��Ļ�� ��
	 * */
	public Sudoku_View(Context context , int width, int height, int stage, boolean isContinue, Handler handler){
		super(context) ;
		//ȡ�ø���ֵ    ��̬������   ��Ļ�� ��
		this._context = context ;
		this.handler = handler ;
		handler = new Handler() ;
		paint_button = new Paint() ;
		this._width = width ;
		this._height = height ;
		//�õ�ѡ��Ĺ���
		this._stage = stage ;
		//�Ƿ�Ϊ����
		this.isContinue = isContinue ;
		//�Գ����ж̵�Ϊ��׼
		int size = _width<_height?_width : _height ;
		//���������� 
		this.distance = size - 20 ; ; 
		//�������������������ֵ
		left = (_width-distance)/2 ;
		top = _height/6 ;
		
		//�������Sudoku�ύ�𰸵İ���
		textsize_y_dis = _height/10-10;
		textsize_x_dis = _width/5;
		button_text_size = textsize_x_dis > textsize_y_dis ? textsize_y_dis : textsize_x_dis ;
		
		//��ʱ��λ��
		timer_x =  (_width-button_text_size*3)/2 ;
		timer_y =  _height/10;
		
		//�ܹ�����ɼ����м��
		pdcBoolean = true ;
		//������Ϸ����
		
//		resetButton_y_dis = _height/10-10;
		
//		resetButton_x_dis = _width/5;
		
		//����ͼƬ����
		bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.youxibeijing)).getBitmap() ;
		Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight()) ;
		//ʹ��Matrixȷ��ͼƬ��������
		matrix = new Matrix() ;
		matrix.setScale((float)(((float)_width/480)*f), (float)(((float)_height/854)*f)) ;
		//��������
		paint_bg = new Paint() ;
		
		//��Ϸ�洢��
		continueShare = new ContinueSharedPreferences(_context) ;
		
		
		//��ʼ����Ϸ���Ǽ�����һ����Ϸ��ȡ���������ݡ�
		getThisGame()  ;
		
		//��ʼ��ͼƬ
		time_bm_bg = ((BitmapDrawable)getResources().getDrawable(R.drawable.shijianbeijing)).getBitmap() ;	//��ʱ��
		time_bm_bg = Bitmap.createBitmap(time_bm_bg, 0, 0,  time_bm_bg.getWidth(), time_bm_bg.getHeight(), matrix, true) ;
		bm_wancheng = ((BitmapDrawable)getResources().getDrawable(R.drawable.wancheng)).getBitmap() ;		//��ɰ�ť
		bm_wancheng = Bitmap.createBitmap(bm_wancheng, 0, 0,  bm_wancheng.getWidth(), bm_wancheng.getHeight(), matrix, true) ;
		bm_chongzhi =((BitmapDrawable)getResources().getDrawable(R.drawable.chongzhi)).getBitmap() ;		//���ð�ť
		bm_chongzhi = Bitmap.createBitmap(bm_chongzhi, 0, 0,  bm_chongzhi.getWidth(), bm_chongzhi.getHeight(), matrix, true) ;
		//���ð�ť
		resetButton_x_dis = bm_chongzhi.getWidth() ;
		resetButton_y_dis = bm_chongzhi.getHeight() ;
		resetButton_y =_height-bm_chongzhi.getHeight()-10;
		resetButton_x = 20;
		
		//��ɰ�ť
		checkButton_x_dis = bm_wancheng.getWidth() ;
		checkButton_y_dis = bm_wancheng.getHeight() ;
		checkButton_y = _height-bm_wancheng.getHeight()-10;
		checkButton_x = _width - resetButton_x_dis - 20 ;
			
		
		//����һ���������߳�������
		new Thread(new Runnable(){
			@Override
			public void run(){
				while(!Thread.interrupted()){
					try {
						Thread.sleep(100) ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					postInvalidate() ;
				}
			}
		}).start() ;
		
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() ;		//MotionEvent
		switch(action){
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_DOWN:
			//��¼�µ����ȥ��(x,y)���Թ�upʱ�����ж����û��Ƿ�Ҫ����������
			oldClick_x = event.getX();
			oldClick_y = event.getY();
			break ;
		case MotionEvent.ACTION_UP:
			//sudoku�����ϵĵ���¼�
			if(oldClick_x == event.getX()&&oldClick_y == event.getY()&&sudoku!=null){
				if(!isChooseNum){
					isChooseNum = sudoku.setXY(event.getX(), event.getY()) ;		//���õ�������������ϵĸ���
				}else{
					getKeyboard = sudoku.setKeyboardXY(event.getX(), event.getY()) ;	//���õ����������ϵĸ���
					numListWriter() ;												//��ͨ���������������ִ���numList��
System.out.println("getKeyboard = " + getKeyboard) ;	
					isChooseNum = false ;
					break ;
				}
			}
			
			//��ɰ�ť
			if(oldClick_x == event.getX()&&oldClick_y == event.getY()&&event.getX()>checkButton_x&&event.getY()>checkButton_y
					&&event.getX()<checkButton_x+checkButton_x_dis&&event.getY()<checkButton_y+checkButton_y_dis){
				_pdc = new ProgressDialogControler(_context) ;
				new Thread(new Runnable(){
					@Override
					public void run(){
						//TODO
						check = new CheckSudoku(_context, _stage, handler, _pdc) ;
						setAws() ;
						//��ʾdialog
//						_pdc = new ProgressDialogControler(_context) ; 
						check.isRight(awsList, timer) ;
						System.out.println("�����Ƿ���ȷ��" + true ) ;
					}
				}).start() ;
			}
			
			//���ð�ť
			if(oldClick_x == event.getX()&&oldClick_y == event.getY()&&event.getX()>resetButton_x&&event.getY()>resetButton_y
					&&event.getX()<resetButton_x+resetButton_x_dis&&event.getY()<resetButton_y+resetButton_y_dis){
				
				
				
				AlerDialog.exitDialog(_context,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								resetGame() ;

							}
						}, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								AlerDialog.closeDialog();
							}
						},"��ȷ��������Ϸ��");
			}
			break ;
		}
		
		return super.onTouchEvent(event);
	}
	
	/**
	 * ��¼�������
	 * */
	public void numListWriter(){
		switch(getKeyboard){
		case 0:return ;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			(numList.get(sudoku.getBOX_X()))[sudoku.getBOX_Y()] = ""+getKeyboard ;
			break ;
		default:return ;
		}
	}



	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save() ;
		//������
		canvas.drawBitmap(bm, matrix, paint_bg) ;
		
		//��ɰ�ť
//		color = paint_button.getColor() ;
		paint_button.setAntiAlias(true) ;
//		paint_button.setColor(Color.WHITE) ;
//		canvas.drawRect(checkButton_x, checkButton_y, checkButton_x+checkButton_x_dis, checkButton_y+checkButton_y_dis, paint_button) ;
//		paint_button.setColor(Color.BLUE) ;
//		button_text_size = textsize_x_dis > textsize_y_dis ? textsize_y_dis : textsize_x_dis ;
//		paint_button.setTextSize(button_text_size/2) ;
//		canvas.drawText("���", checkButton_x+(checkButton_x_dis-button_text_size)/2, checkButton_y+2*checkButton_y_dis/3, paint_button) ;
//		paint_button.setColor(color) ;
		canvas.drawBitmap(bm_wancheng, checkButton_x, checkButton_y, paint_button) ;	
		
		//���ð�ť
		canvas.drawBitmap(bm_chongzhi, resetButton_x, resetButton_y, paint_button) ;
//		color = paint_button.getColor() ;
//		paint_button.setAntiAlias(true) ;
//		paint_button.setColor(Color.WHITE) ;
//		canvas.drawRect(resetButton_x, resetButton_y, resetButton_x+resetButton_x_dis, resetButton_y+resetButton_y_dis, paint_button) ;
//		paint_button.setColor(Color.BLUE) ;
//		button_text_size = resetButton_x_dis > resetButton_y_dis ? resetButton_y_dis : resetButton_x_dis ;
//		paint_button.setTextSize(button_text_size/2) ;
//		canvas.drawText("����", resetButton_x+(resetButton_x_dis-button_text_size)/2, resetButton_y+2*resetButton_y_dis/3, paint_button) ;
//		paint_button.setColor(color) ;
		

		//���������ؼ�
		if(sudoku==null){
			sudoku = new Sudoku(canvas, left, top, distance, defaultNumList, _width, _height, _context, matrix) ;
		}
		sudoku.sudokuDraw(numList) ;

		//��Ϸʱ�� 
		if(timer==null){
			//TODO
			timer = new CountTime(canvas, time, time_bm_bg, _width) ;
		}
		System.out.println("timer_x"+timer_x+"timer_y"+timer_y+"button_text_size/2"+button_text_size/2);
		timer.drawTimer(timer_x, timer_y, button_text_size/2) ;
		
		
		canvas.restore() ;
		
	}
	
//	/**
//	 * check �����ɵ�sudoku�Ƿ�ʹ�һ��
//	 * */
//	public boolean checkAws(){
//		boolean isRight = true ;
//		for(int i=0;i<defaultNumList.size();i++){
//			for(int j=0;i<10;j++){
//				isRight = (defaultNumList.get(i))[j] .equals( (numList.get(i))[j]  ) ;
//				if(!isRight) 
//					return isRight;
//			}
//		}
//		return isRight ;
//	}
	
	/**
	 * ����Ϸֹͣʱ ������ô˷������ѱ��汾����Ϸ
	 * */
	public void saveThisGame(){
		if(continueShare!=null){
			continueShare.saveStage(_stage, numList) ;
			if(timer!=null)
				continueShare.saveTime(timer.getTime()) ;
		}
	}
	
	
	/**
	 * ������Ϸʱ ѡ�صõ�������Ϸ �����Ǽ�����һ����Ϸ
	 * */
	private void getThisGame(){
		//����Ĭ������ArrayList (TIPS:�Ǵ�1��ʼ�� ��9����)
		defaultNumList = new GetSudoku().getSudoku(_stage) ;
		//����������������ArrayList (TIPS:�Ǵ�1��ʼ�� ��9����)
		if(isContinue){
			numList = continueShare.getList() ;
			time = continueShare.getTime() ;
		}else{
			//�½���Ϸ���ó�һ������Ŀ�����
			numList = new ArrayList<String[]>() ;
			//
			time = 0 ;
			for(int i=0;i<10;i++){
				itemList = new String[10] ;
				for(int j=0;j<10;j++){
					itemList[j] = DEFAULT_NULL ;
				}
				numList.add(i, itemList) ;
			}
		}
	}
	
	/**
	 * ������Ϸ������_numList
	 * */
	public void resetGame(){
		//�½���Ϸ���ó�һ������Ŀ�����
		numList = new ArrayList<String[]>() ;
		for(int i=0;i<10;i++){
			itemList = new String[10] ;
			for(int j=0;j<10;j++){
				itemList[j] = DEFAULT_NULL ;
			}
			numList.add(i, itemList) ;
		}
		timer.resetGame() ;
	}
	
	
	/**
	 * ���ô�����
	 * */
	public void setAws(){
		awsList = new ArrayList<String[]>() ;
		awsList.add(new String[10]) ;
		for(int i=1;i<10;i++){
			itemList = new String[10] ;
			for(int j=1;j<10;j++){
				if((defaultNumList.get(i))[j].equals(DEFAULT_NULL)){
					itemList[j] = (numList.get(i))[j] ;
				}else{
					itemList[j] = (defaultNumList.get(i))[j] ;
				}
			}
			awsList.add(itemList) ;
		}
	}
	
	public static ProgressDialogControler _pdc;
}
