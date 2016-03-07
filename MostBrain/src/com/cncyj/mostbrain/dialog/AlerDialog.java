package com.cncyj.mostbrain.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.cncyj.mostbrain.R;

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
	public static void exitDialog(Context context,final DialogInterface.OnClickListener positiveListener,
			final DialogInterface.OnClickListener negativeListener,String message,boolean c)
	{
		mDialog = BaseDialog.getDialog(context, R.string.prompt, message, "确定", 
				positiveListener,"取消",
				negativeListener,c);
		if(c){
			mDialog.setCanceledOnTouchOutside(true);
		mDialog.setCancelable(true);
		}else{
			mDialog.setCancelable(false);
			mDialog.setCanceledOnTouchOutside(false);
		}
		
		mDialog.show();
	}
	/**
	 * 
	 * @param context
	 * @param positiveListener
	 * @param negativeListener
	 * @param message
	 * @param c
	 * @throws 
	 * @throw
	 */
	public static void exitDialog(Context context,final DialogInterface.OnClickListener positiveListener,
			final DialogInterface.OnClickListener negativeListener,DialogInterface.OnClickListener click,String message,String butmessage1
			,String butmessage2,String butmessage3,boolean c)
	{
		mDialog = BaseDialog.getDialog(context, R.string.prompt, message, butmessage1, 
				positiveListener,butmessage2,
				negativeListener,butmessage3,
				click,c);
		if(c){
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(true);
		}else{
			mDialog.setCancelable(false);
			mDialog.setCanceledOnTouchOutside(false);
		}
		mDialog.show();
	}
}
