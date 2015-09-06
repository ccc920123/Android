package com.cn.math.coumview;


import com.cn.math.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomWaterProgressDialog extends Dialog{
	
	private static TextView loadingmsg;
	private Context context = null;
    private static CustomWaterProgressDialog customWaterProgressDialog = null;
    
    public CustomWaterProgressDialog(Context context) {
		super(context);
		
	}
    public CustomWaterProgressDialog(Context context, int theme) {
		super(context, theme);
	}
    
    
    public static CustomWaterProgressDialog createDialog(Context context,String msg){
    	View v=LinearLayout.inflate(context, R.layout.customprogressdialog, null);
    	loadingmsg=(TextView) v.findViewById(R.id.id_tv_loadingmsg);
    	loadingmsg.setText(msg);
    	customWaterProgressDialog = new CustomWaterProgressDialog(context,R.style.CustomWaterProgressDialog);
//    	customWaterProgressDialog.setCancelable(false);//点击返回不可取消
    	customWaterProgressDialog.setCanceledOnTouchOutside(false);//点击返回可取消，点击空白不取消
    	customWaterProgressDialog.setContentView(v);
    	customWaterProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
     
        return customWaterProgressDialog;
    } 
  
    public void onWindowFocusChanged(boolean hasFocus){
        
    }
    
    /**
     *
     * [Summary]
     *       setTitile ����
     * @param strTitle
     * @return
     *
     */
    public CustomWaterProgressDialog setTitile(String strTitle){
        return customWaterProgressDialog;
    }
     
    /**
     *
     * [Summary]
     *       setMessage ��ʾ����
     * @param strMessage
     * @return
     *
     */
    public CustomWaterProgressDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)customWaterProgressDialog.findViewById(R.id.id_tv_loadingmsg);
         
        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }
         
        return customWaterProgressDialog;
}
    
}
