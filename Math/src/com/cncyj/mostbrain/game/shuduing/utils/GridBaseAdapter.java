package com.cncyj.mostbrain.game.shuduing.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.cncyj.mostbrain.R;

public class GridBaseAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater mInflater;
	private SharedPreferences shared ;
	private static final String STAGE_SCORE = "score" ;
	
	public GridBaseAdapter(Context context){
		this.context = context;
		 mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return imageArr.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			layout = (LinearLayout)mInflater.inflate(R.layout.linear, parent, false);
			imageView = (ImageView)layout.findViewById(R.id.imageView);
			imageView.setVisibility(View.VISIBLE);
			ratingBar = (RatingBar) layout.findViewById(R.id.ratingBar);
			ratingBar.setVisibility(View.VISIBLE);
			ratingBar.setEnabled(false);
			//得到SharedPerference，用以存储数据，这个数据用来存储玩家玩过的关卡成绩
			shared = this.context.getSharedPreferences("passGame", Activity.MODE_PRIVATE) ;
			
			float rating = shared.getFloat(STAGE_SCORE+position, 0) ;
			stage = shared.getInt("max_stage", -1);
			stage = stage>=1?stage:1;
			System.out.println("int stage:"+stage);
			ratingBar.setRating(rating);
		}else{
			layout = (LinearLayout)convertView;
		}
		if(position<=stage+1){
			imageView.setImageResource(imageArr[position]);
		}else{
			layout.setBackgroundResource(R.drawable.layout_bg_suo);
			ratingBar.setVisibility(View.GONE);
			imageView.setVisibility(View.GONE);
		}
		return layout;
	}
	static int[] rating = {};
	static final int[] imageArr = {R.drawable.image_01,R.drawable.image_02,R.drawable.image_03,R.drawable.image_04
		,R.drawable.image_05,R.drawable.image_06,R.drawable.image_07,R.drawable.image_08,R.drawable.image_09,
		R.drawable.image_010,R.drawable.image_011,R.drawable.image_012,R.drawable.image_013,R.drawable.image_014,
		R.drawable.image_015,R.drawable.image_016};
    private LinearLayout layout = null;
    private ImageView imageView = null;
    private RatingBar ratingBar = null;
    private int stage;
}
