package com.cn.math.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder
{
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	private ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到�?��ViewHolder对象
	 * 
	 * @param context 
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position)
	{
		if (convertView == null)
		{
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}

	public View getConvertView()
	{
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId)
	{
		View view = mViews.get(viewId);
		if (view == null)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
 
	/**
	 * 为TextView设置字符�?
	 * 
	 * @param viewId TextView
	 * @param text 
	 * @return
	 */
	public ViewHolder setText(int viewId, String text)
	{
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}
	/**
	 * �õ�view��ֵ
	 * @param viewId
	 * @return
	 * @throws 
	 * @throw
	 */
    public String getText(int viewId)
    {
    	TextView view = getView(viewId);
    	return view.getText().toString();
    }
	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId �?��设置图片的ImageView控件
	 * @param drawableId drawable下的图片
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId)
	{
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
		view.setVisibility(View.GONE);
		view.setVisibility(View.VISIBLE);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId �?��设置图片的ImageView控件
	 * @param bm bitmap类型
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm)
	{
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

   public ViewHolder setVisibility(int viewId,int ifgone)
   {
	  View view=getView(viewId);
	  view.setVisibility(ifgone);
	  return this;
   }
  
}
