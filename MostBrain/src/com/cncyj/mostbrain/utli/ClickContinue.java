package com.cncyj.mostbrain.utli;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * ç”»æ±‰å­?
 * @author CXY
 *
 */
public class ClickContinue extends TextView {

	public ClickContinue(Context context) {
		super(context);
		init();
	}

	public ClickContinue(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ClickContinue(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG); 
		mPaint.setColor(Color.BLACK);
		mPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.ITALIC));
		mPaint.setStrokeWidth(5);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setTextSize(30);

		text_width = (int) mPaint.measureText("10¸öºº×Ö");

	}

	@Override
	public void onDraw(Canvas canvas) {
		mPaint.setColor(Color.RED);
		canvas.drawText(TEXTS[count], x+count*28, y-10, mPaint);
		
		mPaint.setColor(Color.GRAY);
		for(int i = 0;i<count;i++){
			canvas.drawText(TEXTS[i], x+i*28, y-10, mPaint);
		}
		
		for(int j = count+1;j<TEXTS.length;j++){
			canvas.drawText(TEXTS[j], x+j*28, y-10, mPaint);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		System.out.println("w:" + w + " h:" + h);
		x = (int) ((w - text_width)*0.5);
		y = h;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	public void upDateText(){
		TEXTS="".equals(getText().toString())?TEXTS:getarray();
		new UpdateText().start();
		
	}
	
	private String[] getarray() {
		char [] cr=getText().toString().toCharArray();
		String tex[]=new String[cr.length];
		for (int i = 0; i < cr.length; i++) {
			String s=String.valueOf(cr[i]);
			tex[i]=s;
		}
		return tex;
	}

	
	public void stopText(boolean flag){
		this.flag = flag;
	}

	class UpdateText extends Thread {
		@Override
		public void run() {
			try {
				while (flag) {
					Thread.sleep(300);
					count++;
					count = count == TEXTS.length ? 0 : count;
					postInvalidate();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int x;
	private int y;
	private int count;

	private int text_width = 0;

	private Paint mPaint;

	private boolean flag = true;

//	private static final String[] TEXTS = { "C", "l", "i", "c","k"," ","T","o"," ","C","o","n","t","i","n","u","e" };
	private static String[] TEXTS = { "Æô", "ÃÉ", "Êý", "Ñ§","-","J","¸ç","Rights"};
}
