package com.cncyj.mostbrain.game.zuiqiangdanao;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cncyj.mostbrain.R;

public class Crad extends RelativeLayout{
	public ScaleAnimation saTo0 = new ScaleAnimation(1, 0, 1, 1, 1, 0.5f, 0, 1);
	public ScaleAnimation saTo1 = new ScaleAnimation(1, 0, 1, 1, 1, 0.5f, 1, 0);
	private TextView textview;
	private int num = 0;
	private boolean avisiable = true;
	
	public Crad(Context mycontext,int num) {
		super(mycontext);
		this.num = num;
		textview = new TextView(getContext());
		textview.setTextSize(20);
		textview.setTextColor(Color.WHITE);
		textview.setText(num+"");
		setGravity(Gravity.CENTER);
		//把所有的视图全部添加到view当中
		addView(textview);
		saTo0.setDuration(1000);
		saTo1.setDuration(1000);
		showA();
	}
	/**
	 * 获得数字
	 * @return
	 */
	public int getNum() {
		return num;
	}
	public boolean isAvisiable() {
		return avisiable;
	}
	/**
	 * 显示正面
	 */
	public void showA(){
		avisiable = true;
		textview.setVisibility(View.VISIBLE);
		textview.setAnimation(saTo0);
		setAnimation(saTo0);
		setBackgroundResource(R.drawable.dan);
	}
	/**
	 * 显示反面
	 */
	public void showB(){
		avisiable = false;
		textview.setVisibility(View.GONE);
		textview.setAnimation(saTo1);
		setAnimation(saTo1);
		setBackgroundResource(R.drawable.dan2);
		textview = new TextView(getContext());
		textview.setText("?");
		textview.setTextSize(20);
		textview.setTextColor(Color.WHITE);
		setGravity(Gravity.CENTER);
		//把所有的视图全部添加到view当中
		addView(textview);
		
		
	}

}
