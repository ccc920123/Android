package com.cn.math.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.cn.math.R;

public class AlerDialog {
	private static CustomAlertDialog dialog;
	private static BaseDialog mDialog;
	
	public static void showAlerDialog(Context context, String msg
			,String leftmsg,View.OnClickListener leftOnClick,
			String rightMessage,View.OnClickListener rightOnClick,boolean issb) {
		dialog=new CustomAlertDialog(context,
				msg, leftmsg, leftOnClick,
				rightMessage,rightOnClick,issb);
		dialog.setCancelable(false);
		dialog.show();
	}
	public static void closeDialog()
	{
		if(dialog!=null)
		{
			dialog.dismiss();
			dialog=null;
		}
		if(mDialog!=null)
		{
			mDialog.dismiss();
			mDialog=null;
		}
	}
	
	public static void exitDialog(Context context,final DialogInterface.OnClickListener positiveListener,
			final DialogInterface.OnClickListener negativeListener)
	{
		mDialog = BaseDialog.getDialog(context, R.string.prompt, "你真的要退出系统？", "确定", 
				positiveListener,"取消",
				negativeListener);
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(true);
		mDialog.show();
	}
	
	public static void exitDialog(Context context,final DialogInterface.OnClickListener positiveListener,
			final DialogInterface.OnClickListener negativeListener,String message)
	{
		mDialog = BaseDialog.getDialog(context, R.string.prompt, message, "确定", 
				positiveListener,"取消",
				negativeListener);
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(true);
		mDialog.show();
	}
}
