package com.cncyj.mostbrain.adpter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.cncyj.mostbrain.R;
import com.cncyj.mostbrain.bean.MathBean;

public class MathAdpter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<MathBean> data;
	//定义一个HashMap，用来存放EditText的值，Key是position  
	private HashMap<Integer, String> hashMap = new HashMap<Integer, String>();  

	public MathAdpter(Context context,List<MathBean> data)
	{
		this.data=data;
		mInflater = LayoutInflater.from(context);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		System.out.println("外position==>"+position);
		ViewHolder holder = null;
		if(convertView==null)
		{
			holder=new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item, null);
		
			holder.addend1=(TextView) convertView.findViewById(R.id.addend1);
			holder.symbol=(TextView) convertView.findViewById(R.id.symbol);
			holder.addend2=(TextView) convertView.findViewById(R.id.addend2);
			holder.textView4=(TextView) convertView.findViewById(R.id.textView4);
			holder.other=(EditText) convertView.findViewById(R.id.setOther);
			holder.other.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					System.out.println("eit事件position"+position);
					  hashMap.put(position, s.toString());  
					
				}
			});
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.addend1.setText(String.valueOf(data.get(position).getAddend1()));
		holder.symbol.setText(data.get(position).getSymbol());
		holder.addend2.setText(String.valueOf(data.get(position).getAddend2()));
		holder.textView4.setText(data.get(position).getEql());
		holder.other.setText(data.get(position).getSetOther());
		if(hashMap.get(position) != null){  
			holder.other.setText(hashMap.get(position));  
			System.out.println("map的值====>"+hashMap.get(position));
	  }
		return convertView;
	}
	public final class ViewHolder {
		public TextView addend1;
		public TextView symbol;
		public TextView addend2;
		public TextView textView4;
		public EditText other;
	}
	
	
}
