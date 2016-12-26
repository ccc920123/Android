package com.cdjysdkj.diary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cdjysdkj.diary.R;
import com.cdjysdkj.diary.tab.TextDiaryTab;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<TextDiaryTab> {

	private int resourceId;

	public FruitAdapter(Context context, int textViewResourceId,
			List<TextDiaryTab> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		Log.d("TAG", "get view " + position + " convertView is " + convertView);
		TextDiaryTab fruit = getItem(position);
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		} else {
			view = convertView;
		}
		TextView text = (TextView) view.findViewById(R.id.view_title);//标题
		text.setText(fruit.getTitle());
		TextView info = (TextView) view.findViewById(R.id.view_info);//内容
		info.setText(fruit.getText());
		TextView date = (TextView) view.findViewById(R.id.view_date);//时间
		date.setText(fruit.getCreatTime());
		return view;
	}

}
