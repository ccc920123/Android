package com.cdjysdkj.diary.sliding;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cdjysdkj.diary.R;
import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView
{
	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScreenWidth;

	private int mMenuWidth;
	// dp
	private int mMenuRightPadding = 50;

	private boolean once;

	public boolean isLeftOpen;
	public boolean isRightOpen;
	
	private onChangeListener mChangeListener;
	

	public onChangeListener getmChangeListener() {
		return mChangeListener;
	}

	public void setmChangeListener(onChangeListener mChangeListener) {
		this.mChangeListener = mChangeListener;
	}

	Context context;

	/**
	 *
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
		this.context=context;
	}

	/**
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		this.context=context;
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);

		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case R.styleable.SlidingMenu_rightPadding:
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50, context
										.getResources().getDisplayMetrics()));
				break;
			}
		}
		a.recycle();

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;
		Log.d("TAG", "slidingMenu");
	}

	public SlidingMenu(Context context)
	{
		this(context, null);
		this.context=context;
	}

	/**
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		if (!once)
		{
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
					- mMenuRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
			mContent.setClickable(true);
			mContent.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Log.d("TAG", "setOnClickListener");
					if (isRightOpen)
					{
						closeRightMenu();
					} else if(isLeftOpen)
					{
						closeMenuLeft();
					}
					
				}
			});
		}
		Log.d("TAG", "onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	/**
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			this.scrollTo(mMenuWidth, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		Log.e("TAG", "onTouchEvent  ");
		int action = ev.getAction();
		switch (action)
		{
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX >= mMenuWidth / 2&&scrollX<=mMenuWidth)
			{
				this.smoothScrollTo(mMenuWidth, 0);
				isLeftOpen = false;
				mChangeListener.closeLeft();
			} else if(scrollX<mMenuWidth / 2){
				this.smoothScrollTo(0, 0);
				isLeftOpen = true;
				mChangeListener.openLeft();
			}else if(scrollX>=mMenuWidth+30){
				this.smoothScrollTo(mMenuWidth*2, 0);
				isRightOpen = true;
				mChangeListener.openRight();
			}else{
				this.smoothScrollTo(mMenuWidth, 0);
				isRightOpen = false;
				mChangeListener.closeRight();
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	public void openMenuLeft()
	{
		if (isLeftOpen)
			return;
		this.smoothScrollTo(0, 0);
		isLeftOpen = true;
		mChangeListener.openLeft();
	}
	public void openRightMenu()
	{
		if (isRightOpen)
			return;
		this.smoothScrollTo(mMenuWidth*2, 0);
		isRightOpen = true;
		mChangeListener.openRight();
	}

	public void closeMenuLeft()
	{
		if (!isLeftOpen)
			return;
		this.smoothScrollTo(mMenuWidth, 0);
		isLeftOpen = false;
		mChangeListener.closeLeft();
	}
	public void closeRightMenu()
	{
		if (!isRightOpen)
			return;
		this.smoothScrollTo(mMenuWidth, 0);
		isRightOpen = false;
		mChangeListener.closeRight();
	}

	public void toggleLeft()
	{
		if (isLeftOpen)
		{
			closeMenuLeft();
		} else
		{
			openMenuLeft();
		}
	}
	/**
	 * �л��˵��ұ�
	 */
	public void toggleRight()
	{
		if (isRightOpen)
		{
			closeRightMenu();
		} else
		{
			openRightMenu();
		}
	}
	public void closeMune()
	{
		if (isRightOpen)
		{
			Toast.makeText(context, "isRightOpen�رղ˵�", Toast.LENGTH_LONG).show();
			closeRightMenu();
		} else if(isLeftOpen)
		{
			Toast.makeText(context, "isLeftOpen�رղ˵�", Toast.LENGTH_LONG).show();
			closeMenuLeft();
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);
		if(l<=mMenuWidth){
			float scale = l * 1.0f / mMenuWidth; //

			float rightScale = 0.7f + 0.3f * scale;
			float leftScale = 1.0f - scale * 0.3f;
			float leftAlpha = 0.6f + 0.4f * (1 - scale);

			ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.8f);
			
			ViewHelper.setScaleX(mMenu, leftScale);
			ViewHelper.setScaleY(mMenu, leftScale);
			ViewHelper.setAlpha(mMenu, leftAlpha);
			ViewHelper.setPivotX(mContent, 0);
			ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
			ViewHelper.setScaleX(mContent, rightScale);
			ViewHelper.setScaleY(mContent, rightScale);
		}else{
			float scale =1- (l * 1.0f- mMenuWidth)/ mMenuWidth; // 1 ~ 0
			float contentScale = 0.7f + 0.3f * scale;
			float rightScale = 1.0f - scale * 0.3f;
			float rightAlpha = 0.6f + 0.4f * (1 - scale);
			ViewHelper.setTranslationX(mMenu, -mMenuWidth * scale * 0.8f);
			
			ViewHelper.setScaleX(mMenu, rightScale);
			ViewHelper.setScaleY(mMenu, rightScale);
			ViewHelper.setAlpha(mMenu, rightAlpha);
			ViewHelper.setPivotX(mContent, mContent.getWidth());
			ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
			ViewHelper.setScaleX(mContent, contentScale);
			ViewHelper.setScaleY(mContent, contentScale);
		}
		

	}

	public interface onChangeListener {  
		void openLeft();
		void openRight();
		void closeLeft();
		void closeRight();
	}
}