package com.cn.math.coumview;


import android.content.Context;

public class ProgressDialogUtil {
	
	private static CustomWaterProgressDialog  progressDialog;
	
//	public static void showProgressDialog(Context context, int msgId) {
//		showProgressDialog(context, context.getString(msgId));
//	}

	public static void showProgressDialog(Context context, String msg) {
		progressDialog=CustomWaterProgressDialog.createDialog(context,msg);
		progressDialog.show();
	}
	public static void colseProgressDialog()
	{
		if(progressDialog!=null)
		{
			progressDialog.dismiss();
			progressDialog=null;
		}
	}
	
}
