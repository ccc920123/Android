package cn.com.shuduwang.utils;

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
import cn.com.shuduwang.MyViewPagerActivity;
import cn.com.shuduwang.R;

public class GridBaseAd extends BaseAdapter{
	private Context context;
	private LayoutInflater mInflater;
	private SharedPreferences shared ;
	
	private static final String STAGE_SCORE = "score" ;
	
	public GridBaseAd(Context context){
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
			float rating = shared.getFloat(STAGE_SCORE+(position+15), 0) ;
			stage = shared.getInt("max_stage", -1);
			stage = stage>=1?stage:1;
			ratingBar.setRating(rating);
		}else{
			layout = (LinearLayout)convertView;
		}
		if(position+15<=stage+1){
			imageView.setImageResource(imageArr[position]);
		}else{
			layout.setBackgroundResource(R.drawable.layout_bg_suo);
			ratingBar.setVisibility(View.GONE);
			imageView.setVisibility(View.GONE);
		}
		return layout;
	}
	static final int[] imageArr = {R.drawable.image_017,R.drawable.image_018,R.drawable.image_019,
		R.drawable.image_020,R.drawable.image_021,R.drawable.image_022,R.drawable.image_023,R.drawable.image_024,
		R.drawable.image_025,R.drawable.image_026,R.drawable.image_027,R.drawable.image_028,R.drawable.image_029
		,R.drawable.image_030};
    private LinearLayout layout = null;
    private ImageView imageView = null;
    private RatingBar ratingBar = null;
    private int stage;
}
