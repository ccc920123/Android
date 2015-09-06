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
	    protected Rect mSurfaceRect = new Rect(0, 0, 0, 0); //ǰ�������������Ͻǵ����꣬���������������½ǵ����꣨���ǿ�Ⱥ͸߶ȣ�
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
	        //��ȡ��ǰʱ��
	        starttime = System.currentTimeMillis();
	        mBegintime = System.currentTimeMillis();
	        // �������λ���
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
	    // ÿ֡ʱ�� 60֡���� ��һ�μ���ǰʹ�� ����
	    private float perframe = 6;   //6=16
	    private int count = 0;
	    // ÿ���೤ʱ�����һ��֡ʱ��
	    private int mRefreshSpeed = 40; //30=12
	    // �趨Ҫ��೤ʱ��ÿ֡ 24֡ÿ��
	    private float mFPS = 1000 / 4;
	    private float sleep = mFPS;
	    // ����ˢ��Ƶ��
	    public void setFPS(int fps) {
	        mFPS = 1000 / fps;
	    }
	   
	    protected void calculatePerframe() {
	        count++;
	        if (count == mRefreshSpeed) // ����ÿ֡����ή����ϷЧ�ʡ�20��50���
	        {
	            long timepass = System.currentTimeMillis();
	            long time = timepass - starttime;
	            perframe = time / mRefreshSpeed;// ÿ֡ʱ��
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
