package com.cn.math.game.shuduing.gameactivity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.cn.math.R;
import com.cn.math.game.shuduing.utils.GridBaseAd;
import com.cn.math.game.shuduing.utils.GridBaseAdapter;

public class MyViewPagerActivity extends Activity{
	private ViewPager viewPager = null;
	private List<View> list = null;//页面列表
	private int currIndex = 0;//当前页面索引
	private int offset = 0;// 动画图片偏移量    
    private int bmpW;// 动画图片宽度
    public static int stage = 0;
    private LinearLayout layout_01 = null;
    private LinearLayout layout_02 = null;
    private  GridBaseAdapter adapter;
    private GridBaseAd adapter2;
	public void onCreate(Bundle cicil){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 强制横屏 portrait强制竖屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(cicil);
		setContentView(R.layout.viewpager);
		
		initLayout();
	}
  
	
	public void initLayout(){
		list = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.choosegame, null);
		GridView gird = (GridView) layout.findViewById(R.id.gridview);
		SharedPreferences shared = getSharedPreferences("passGame", Activity.MODE_PRIVATE);
		stage = shared.getInt("max_stage", -1);
		stage = stage>1?stage+1:2;
	    adapter = new GridBaseAdapter(this);
		gird.setAdapter(adapter);
		gird.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gird.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position >stage) return;
				Intent intent = new Intent();
				intent.putExtra("stage", position);
				intent.putExtra("action", false);
				intent.setClass(MyViewPagerActivity.this, Sudoku_Activity.class);
				startActivity(intent);
				MyViewPagerActivity.this.finish();
			}
		});
		list.add(layout);
		layout = (LinearLayout)inflater.inflate(R.layout.moregame, null);
		GridView gird2 = (GridView) layout.findViewById(R.id.gridview2);
		adapter2 = new GridBaseAd(this);
		gird2.setAdapter(adapter2);
		gird2.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gird2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position+15 >stage) return;
				Intent intent = new Intent();
				intent.putExtra("stage", position+15);
				intent.putExtra("action", false);
				intent.setClass(MyViewPagerActivity.this, Sudoku_Activity.class);
				startActivity(intent);
				MyViewPagerActivity.this.finish();
			}
		});
		list.add(layout);
		viewPager = (ViewPager)findViewById(R.id.vPager);
		viewPager.setAdapter(new MyPageAdapter(list));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	class MyPageAdapter extends PagerAdapter{
		
		private List<View> listViews = null;
		public MyPageAdapter(List<View> listViews){
			this.listViews = listViews;
		}
		
		@Override 
		public void destroyItem(View arg0,int arg1,Object arg2){
			((ViewPager)arg0).removeView(listViews.get(arg1));
		}
		
		@Override
		public int getCount() {
			return listViews.size();
		}
		
		@Override 
		public void finishUpdate(View arg0){
			
		}
		
		@Override 
		public Object instantiateItem(View arg0,int arg1){
			((ViewPager) arg0).addView(listViews.get(arg1), 0);
			 return listViews.get(arg1);
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
	}
	public class MyOnPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {
			System.out.println("arg0:"+arg0);
			switch(arg0){
			 case 0 : //空闲
				 break;
			 case 1://滑动中
				 break;
			 case 2://目标加载完毕
				 break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			//从1到2滑动，在1滑动前调用  

		}

		@Override
		public void onPageSelected(int arg0) {
			 //activity从1到2滑动，2被加载后掉用此方法  
//			initLayout();
		}
	}
}  
