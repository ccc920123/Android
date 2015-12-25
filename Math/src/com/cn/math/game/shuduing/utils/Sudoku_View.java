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
 * 数独控件
 * @author Jason
 * 
 * */
public class Sudoku_View extends View{
	
	private Context _context ;		//静态上下文
	private Handler handler ;
	Paint paint_button ;				//画笔
	private int _width ;		//给予的宽度
	private int _height ;		//给予的高度
	
	private Sudoku sudoku ;    //数独
	private float left ;	   //数独盘面的左上角x坐标
	private float top ;		   //数独盘面的左上角y坐标
	private float distance ;   //数度盘面的宽度
	
	private KeyboardOverlay kvo ; //软键盘
	private int keyboard_x ;	  //软键盘的左上角的x
	private int keyboard_y ;	  //软键盘的左上角的y
	
	private ArrayList<String[]> defaultNumList ;	//默认给定的数字 (TIPS:是从1开始的 到9结束)
	private ArrayList<String[]> numList ;			//玩家填入的数字 (TIPS:是从1开始的 到9结束)
	private String[] itemList ;					//子容器
	private static final String DEFAULT_NULL = "0" ;	//ArrayList中默认的空位子
	private ArrayList<String[]> awsList ; 		//答案容器
	
	private float oldClick_x ;		//按下时的坐标， 为了判定抬起时是否一致
	private float oldClick_y ;		//若是不一致，则不作为
	
	private int[] array_xy ;		//装载返回的点击的格子位置
	private boolean isChooseNum = false ;	// 选择输入数字状态（是否在软键盘状态 ）
	private int getKeyboard ;
	private int color ;
	
//	private static final float KEYBOARD_SIZE = 100 ;
	
	private float checkButton_x ;	//完成Sudoku提交答案的按键 X坐标
	private float checkButton_y ;	//完成Sudoku提交答案的按键 Y坐标
	private float checkButton_x_dis ;	//完成Sudoku提交答案的按键 宽
	private float checkButton_y_dis ;	//完成Sudoku提交答案的按键 高
	private float button_text_size ;
	
	private float textsize_x_dis ;
	private float textsize_y_dis ;
	
	private float resetButton_x ;	//重置Sudoku的按键 X坐标
	private float resetButton_y ;	//重置Sudoku的按键 Y坐标
	private float resetButton_x_dis ;	//重置Sudoku的按键 宽
	private float resetButton_y_dis ;	//重置Sudoku的按键 高
	
	private Bitmap bm ;				//游戏背景
	private Matrix matrix ; 		//matrix用以调节背景大小
	private Paint paint_bg ;		//画背景的笔
	private float f = 1.0f ;		//f大小比例 想对于原画大小X*Y：480*800
	
	private ContinueSharedPreferences continueShare ;	//游戏的存储器 
	private int _stage ;				//游戏关卡数
	private boolean isContinue ;	//是否是继续游戏 true为是继续的游戏 false为新游戏
	
	private CountTime timer ;		//游戏计时
	private float timer_x ;			//游戏计时器x坐标
	private float timer_y ;			//游戏计时器y坐标
	private long time ;				//游戏进行的初始时间（如果是继续游戏 ，则从上次退出的时间开始
	
	private Bitmap time_bm_bg ;		//时间背景图片
	private Bitmap bm_wancheng ;	//完成按钮图片
	private Bitmap bm_chongzhi ;	//重置按钮图片
	
	private ProgressDialogControler pdc ;  //答案验证提示
	private boolean pdcBoolean ;	//出现Dialog
	
	private CheckSudoku check ;		//答案判定器
	
	/**
	 * 构造函数
	 * @param Context context 静态上下文 ,  int width, int height 屏幕宽 高
	 * */
	public Sudoku_View(Context context , int width, int height, int stage, boolean isContinue, Handler handler){
		super(context) ;
		//取得给入值    静态上下文   屏幕宽 高
		this._context = context ;
		this.handler = handler ;
		handler = new Handler() ;
		paint_button = new Paint() ;
		this._width = width ;
		this._height = height ;
		//得到选择的关数
		this._stage = stage ;
		//是否为继续
		this.isContinue = isContinue ;
		//以长宽中短的为标准
		int size = _width<_height?_width : _height ;
		//数独盘面宽度 
		this.distance = size - 20 ; ; 
		//定义数度盘面的坐标数值
		left = (_width-distance)/2 ;
		top = _height/6 ;
		
		//定义完成Sudoku提交答案的按键
		textsize_y_dis = _height/10-10;
		textsize_x_dis = _width/5;
		button_text_size = textsize_x_dis > textsize_y_dis ? textsize_y_dis : textsize_x_dis ;
		
		//计时器位置
		timer_x =  (_width-button_text_size*3)/2 ;
		timer_y =  _height/10;
		
		//能够按完成键进行检查
		pdcBoolean = true ;
		//重置游戏按键
		
//		resetButton_y_dis = _height/10-10;
		
//		resetButton_x_dis = _width/5;
		
		//背景图片设置
		bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.youxibeijing)).getBitmap() ;
		Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight()) ;
		//使用Matrix确定图片与界面比例
		matrix = new Matrix() ;
		matrix.setScale((float)(((float)_width/480)*f), (float)(((float)_height/854)*f)) ;
		//背景画笔
		paint_bg = new Paint() ;
		
		//游戏存储器
		continueShare = new ContinueSharedPreferences(_context) ;
		
		
		//初始化游戏，是继续上一盘游戏则取出上盘数据。
		getThisGame()  ;
		
		//初始化图片
		time_bm_bg = ((BitmapDrawable)getResources().getDrawable(R.drawable.shijianbeijing)).getBitmap() ;	//计时器
		time_bm_bg = Bitmap.createBitmap(time_bm_bg, 0, 0,  time_bm_bg.getWidth(), time_bm_bg.getHeight(), matrix, true) ;
		bm_wancheng = ((BitmapDrawable)getResources().getDrawable(R.drawable.wancheng)).getBitmap() ;		//完成按钮
		bm_wancheng = Bitmap.createBitmap(bm_wancheng, 0, 0,  bm_wancheng.getWidth(), bm_wancheng.getHeight(), matrix, true) ;
		bm_chongzhi =((BitmapDrawable)getResources().getDrawable(R.drawable.chongzhi)).getBitmap() ;		//重置按钮
		bm_chongzhi = Bitmap.createBitmap(bm_chongzhi, 0, 0,  bm_chongzhi.getWidth(), bm_chongzhi.getHeight(), matrix, true) ;
		//重置按钮
		resetButton_x_dis = bm_chongzhi.getWidth() ;
		resetButton_y_dis = bm_chongzhi.getHeight() ;
		resetButton_y =_height-bm_chongzhi.getHeight()-10;
		resetButton_x = 20;
		
		//完成按钮
		checkButton_x_dis = bm_wancheng.getWidth() ;
		checkButton_y_dis = bm_wancheng.getHeight() ;
		checkButton_y = _height-bm_wancheng.getHeight()-10;
		checkButton_x = _width - resetButton_x_dis - 20 ;
			
		
		//启动一个工作者线程来更新
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
			//记录下点击下去的(x,y)，以供up时进行判定，用户是否要点击这个键。
			oldClick_x = event.getX();
			oldClick_y = event.getY();
			break ;
		case MotionEvent.ACTION_UP:
			//sudoku盘面上的点击事件
			if(oldClick_x == event.getX()&&oldClick_y == event.getY()&&sudoku!=null){
				if(!isChooseNum){
					isChooseNum = sudoku.setXY(event.getX(), event.getY()) ;		//设置点击在数度盘面上的格数
				}else{
					getKeyboard = sudoku.setKeyboardXY(event.getX(), event.getY()) ;	//设置点击在软键盘上的格数
					numListWriter() ;												//将通过软键盘输入的数字存入numList中
System.out.println("getKeyboard = " + getKeyboard) ;	
					isChooseNum = false ;
					break ;
				}
			}
			
			//完成按钮
			if(oldClick_x == event.getX()&&oldClick_y == event.getY()&&event.getX()>checkButton_x&&event.getY()>checkButton_y
					&&event.getX()<checkButton_x+checkButton_x_dis&&event.getY()<checkButton_y+checkButton_y_dis){
				_pdc = new ProgressDialogControler(_context) ;
				new Thread(new Runnable(){
					@Override
					public void run(){
						//TODO
						check = new CheckSudoku(_context, _stage, handler, _pdc) ;
						setAws() ;
						//提示dialog
//						_pdc = new ProgressDialogControler(_context) ; 
						check.isRight(awsList, timer) ;
						System.out.println("检测答案是否正确：" + true ) ;
					}
				}).start() ;
			}
			
			//重置按钮
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
						},"你确定重置游戏吗？");
			}
			break ;
		}
		
		return super.onTouchEvent(event);
	}
	
	/**
	 * 记录玩家输入
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
		//画背景
		canvas.drawBitmap(bm, matrix, paint_bg) ;
		
		//完成按钮
//		color = paint_button.getColor() ;
		paint_button.setAntiAlias(true) ;
//		paint_button.setColor(Color.WHITE) ;
//		canvas.drawRect(checkButton_x, checkButton_y, checkButton_x+checkButton_x_dis, checkButton_y+checkButton_y_dis, paint_button) ;
//		paint_button.setColor(Color.BLUE) ;
//		button_text_size = textsize_x_dis > textsize_y_dis ? textsize_y_dis : textsize_x_dis ;
//		paint_button.setTextSize(button_text_size/2) ;
//		canvas.drawText("完成", checkButton_x+(checkButton_x_dis-button_text_size)/2, checkButton_y+2*checkButton_y_dis/3, paint_button) ;
//		paint_button.setColor(color) ;
		canvas.drawBitmap(bm_wancheng, checkButton_x, checkButton_y, paint_button) ;	
		
		//重置按钮
		canvas.drawBitmap(bm_chongzhi, resetButton_x, resetButton_y, paint_button) ;
//		color = paint_button.getColor() ;
//		paint_button.setAntiAlias(true) ;
//		paint_button.setColor(Color.WHITE) ;
//		canvas.drawRect(resetButton_x, resetButton_y, resetButton_x+resetButton_x_dis, resetButton_y+resetButton_y_dis, paint_button) ;
//		paint_button.setColor(Color.BLUE) ;
//		button_text_size = resetButton_x_dis > resetButton_y_dis ? resetButton_y_dis : resetButton_x_dis ;
//		paint_button.setTextSize(button_text_size/2) ;
//		canvas.drawText("重置", resetButton_x+(resetButton_x_dis-button_text_size)/2, resetButton_y+2*resetButton_y_dis/3, paint_button) ;
//		paint_button.setColor(color) ;
		

		//绘制数独控件
		if(sudoku==null){
			sudoku = new Sudoku(canvas, left, top, distance, defaultNumList, _width, _height, _context, matrix) ;
		}
		sudoku.sudokuDraw(numList) ;

		//游戏时间 
		if(timer==null){
			//TODO
			timer = new CountTime(canvas, time, time_bm_bg, _width) ;
		}
		System.out.println("timer_x"+timer_x+"timer_y"+timer_y+"button_text_size/2"+button_text_size/2);
		timer.drawTimer(timer_x, timer_y, button_text_size/2) ;
		
		
		canvas.restore() ;
		
	}
	
//	/**
//	 * check 玩家完成的sudoku是否和答案一致
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
	 * 当游戏停止时 ，请调用此方法，已保存本局游戏
	 * */
	public void saveThisGame(){
		if(continueShare!=null){
			continueShare.saveStage(_stage, numList) ;
			if(timer!=null)
				continueShare.saveTime(timer.getTime()) ;
		}
	}
	
	
	/**
	 * 进入游戏时 选关得到本次游戏 或者是继续上一盘游戏
	 * */
	private void getThisGame(){
		//创建默认数字ArrayList (TIPS:是从1开始的 到9结束)
		defaultNumList = new GetSudoku().getSudoku(_stage) ;
		//创建玩家填入的数字ArrayList (TIPS:是从1开始的 到9结束)
		if(isContinue){
			numList = continueShare.getList() ;
			time = continueShare.getTime() ;
		}else{
			//新建游戏则拿出一个待填的空容器
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
	 * 重置游戏，重置_numList
	 * */
	public void resetGame(){
		//新建游戏则拿出一个待填的空容器
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
	 * 放置答案容器
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
