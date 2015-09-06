package com.cn.math.fireworks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawTopBase implements Runnable,Callback,OnTouchListener {
	 protected DrawStatus mStatus = DrawStatus.NoWork;
	    protected SurfaceView mSurfaceView;
	    protected SurfaceHolder mSurfaceHolder;
	    protected Context mContext;
	    protected Rect mSurfaceRect = new Rect(0, 0, 0, 0); //前两个参数是左上角的坐标，后两个参数是右下角的坐标（不是宽度和高度）
	    public DrawTopBase() {
	        setSurfaceView(HolderSurfaceView.getInstance().getSurfaceView());
	    }
	    public void setSurfaceView(SurfaceView view) {
	        mSurfaceView = view;
	        mContext = mSurfaceView.getContext();
	        mSurfaceHolder = mSurfaceView.getHolder();
	        mSurfaceHolder.addCallback(this);
//	        mSurfaceRect.set(new Rect(0, 0, mSurfaceView.getWidth(), mSurfaceView.getHeight()));
	        mSurfaceRect.set(new Rect(100, 100, 250, 250));
	        set();
	    }
	   
	    public void set() {
	        setRect(mSurfaceRect);
	    }
	    protected Thread mThread = null;
	   
	    public void begin() {
	     
	        if (mThread == null) {
	            mThread = new Thread(this);
	            mThread.start();
	        }
	    }
	    public void end() {
	        mStatus = DrawStatus.Ending;
	    }
	   
	    protected void doWork(Canvas canvas) {
	    }
	   
	    protected void endWork() {
	    }
	    protected Paint mPaint = new Paint();
	   
	    protected void clear(Canvas canvas) {
	        mPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
	        canvas.drawPaint(mPaint);
	        mPaint.setXfermode(new PorterDuffXfermode(Mode.SRC));
	    }
	    protected void clear() {
	        synchronized (mSurfaceHolder) {
	            Canvas canvas = this.mSurfaceHolder.lockCanvas();
	            try {
	                clear(canvas);
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                if (canvas != null)
	                    mSurfaceHolder.unlockCanvasAndPost(canvas);
	            }
	        }
	    }
	    protected void doChange() {
	        change();
	    }
	   
	    protected void change() {
	    }
//	    protected Rect mRect = new Rect(0, 0, 0, 0);
	    protected Rect mRect = new Rect(50, 50, 250, 250);
	    public void setRect(Rect r) {
	        mRect.set(r);
	    }
	    public Rect getRect() {
	        return mRect;
	    }
	   
	   
	    protected enum DrawStatus {
	        NoWork, Drawing, Ending, Destroyed
	    };
	    protected long mBegintime;
	    public void run() {
	        mStatus = DrawStatus.Drawing;
	        //获取当前时间
	        starttime = System.currentTimeMillis();
	        mBegintime = System.currentTimeMillis();
	        // 建立两次缓存
	        clear();
	        clear();
	        while (mStatus == DrawStatus.Drawing) {
	            synchronized (mSurfaceHolder) {
	                Canvas canvas = this.mSurfaceHolder.lockCanvas(getRect());
	                
	                try {
	                    if (canvas != null) {
	                        clear(canvas);
	                        doWork(canvas);
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } finally {
	                    if (canvas != null)
	                        mSurfaceHolder.unlockCanvasAndPost(canvas);
	                }
	                doChange();               
	            }
	            calculatePerframe();
	        }
	        if (mStatus != DrawStatus.Destroyed)
	            clear();
	        mThread = null;
	        endWork();
	    }
	   
	    protected long starttime;
	    // 每帧时间 60帧附近 第一次计算前使用 毫秒
	    private float perframe = 6;   //6=16
	    private int count = 0;
	    // 每隔多长时间测试一次帧时间
	    private int mRefreshSpeed = 40; //30=12
	    // 设定要求多长时间每帧 24帧每秒
	    private float mFPS = 1000 / 4;
	    private float sleep = mFPS;
	    // 设置刷新频率
	    public void setFPS(int fps) {
	        mFPS = 1000 / fps;
	    }
	   
	    protected void calculatePerframe() {
	        count++;
	        if (count == mRefreshSpeed) // 由于每帧计算会降低游戏效率。20到50差不多
	        {
	            long timepass = System.currentTimeMillis();
	            long time = timepass - starttime;
	            perframe = time / mRefreshSpeed;// 每帧时间
	            sleep = perframe > mFPS ? mFPS - (perframe - mFPS) / mRefreshSpeed : mFPS;
	            starttime = timepass;
	            count = 0;
	        }
	        try {
	         
	            Thread.sleep((long) (sleep));
//	         Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
//	        new Thread(){
//	          public void run(){
//	           try {
//	            
//	      sleep(2000);
//	     } catch (Exception e) {
//	      // TODO: handle exception
//	      e.printStackTrace();
//	     }finally{
////	      finish();
//	     }
//	          }
//	         }.start();
	    }
	    public void surfaceChanged(SurfaceHolder holder, int format, int width,
	            int height) {
	        mSurfaceRect.set(new Rect(0, 0, width, height));
//	        mSurfaceRect.set(new Rect(0, 0, 200, 500));
	        set();
	    }
	    public void surfaceCreated(SurfaceHolder holder) {
	    }
	    public void surfaceDestroyed(SurfaceHolder holder) {
	        mStatus = DrawStatus.Destroyed;
	    }
	    public boolean onTouch(View v, MotionEvent event) {
	        // TODO Auto-generated method stub
	        return false;
	    }
	    
	 
}
