package com.cncyj.mostbrain.game.kuaifanying;
   
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas; 
import android.graphics.Paint; 
import android.graphics.Rect; 
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	SurfaceHolder holder;
	public static long speedTime = 20;
	int screenWidth = 800; //  
	int screenHeight = 800; // 
	static boolean isRun = true;
	Gamemanager gameManager;

	public static int currentState = -1;
	public final static int STATE_GAMERUN = 5;

	public GameView(Context context) {
		super(context);
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		float density = dm.density;
		screenWidth = (int) (dm.widthPixels * density + 0.5f); // Tool.screenWidth;//
		screenHeight = (int) (dm.heightPixels * density + 0.5f); // Tool.screenHeight;//
	//	Tool.setWH(screenWidth, screenHeight);
		
		holder = this.getHolder();// 获取holder
		holder.addCallback(this);
		currentState = STATE_GAMERUN;
		gameManager = Gamemanager.getGamemanager();
		gameManager.resetGame();
		gameManager.LoadGameData();
		gameManager.changeState(Gamemanager.STATE_GAME_MAIN);
		
		
		
		
 
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isRun = true;
		new Thread(new MyThread()).start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isRun = false;
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	int lastTouchX;
	int lastTouchY;

	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
  
			break;
		}
		switch (currentState) {
		case STATE_GAMERUN: {
			gameManager.onTouchEvent(event);
		}
			break;
		}
		return true;

	}

	public void onDraw() {
		Canvas canvas = holder.lockCanvas(null);// 获取画布
		if (canvas==null){
			return;
		}
		//canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		try{
		cleanScreen(canvas);
		switch (currentState) {
		case STATE_GAMERUN: {
			gameManager.draw(canvas);
		}
			break;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{			
			holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
		}
	}

	public void cleanScreen(Canvas canvas) {
		Paint mPaint = new Paint();
		mPaint.setColor(0xffEED5B7);
		canvas.drawRect(new Rect(0, 0, screenWidth, screenHeight), mPaint);
	}

	public void onUpdate() {
		switch (currentState) {
		case STATE_GAMERUN: {
			gameManager.update();
		}
			break;
		}
	}

	// 内部类的内部类
	@SuppressLint("WrongCall")
	class MyThread implements Runnable {

		public void run() {
			while (isRun) {
				try {
					long lastTime = System.currentTimeMillis();

					onDraw();
					onUpdate();
					long useTime = System.currentTimeMillis() - lastTime;
					if (speedTime - useTime > 0) {
						Thread.sleep(speedTime - useTime);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

}
