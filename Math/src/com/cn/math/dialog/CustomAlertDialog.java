package com.cn.math.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.math.R;


/**
 *CustomAlertDialog.java
 * @author CXY
 * @Description
 * @Create Time 2015-7-31
 */
public class CustomAlertDialog extends Dialog{
	
	private int width;
	private String leftMessage;
	private String rightMessage;
	private String strzq;
	private View.OnClickListener leftOnClick;
	private View.OnClickListener rightOnClick;
	private boolean issb;
	private Context context;
	
    public CustomAlertDialog(Context context,String strzq,String leftmsg,View.OnClickListener leftOnClick,String rightMessage,View.OnClickListener rightOnClick,boolean issb){
        super(context, R.style.MyDialog);
        this.context=context;
        this.strzq=strzq;
        this.leftMessage=leftmsg;
		this.leftOnClick=leftOnClick;
		this.rightMessage=rightMessage;
		this.rightOnClick=rightOnClick;
		this.issb=issb;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.succeed_inclde);
		initViews();
    }
    
    void initViews(){
    	ImageView image=(ImageView) findViewById(R.id.imageView1);
    	if(issb){
    		image.setBackground(context.getResources().getDrawable(R.drawable.icon_nizhengbiang_normal));
//		image.setBackgroundResource(R.anim.fw);
    	}else{
//    	image.setBackgroundResource(R.anim.sb);
    		image.setBackground(context.getResources().getDrawable(R.drawable.icon_buyaohuixing_normal));
    	}
//    	AnimationDrawable animationDrawable = (AnimationDrawable) image.getBackground();
//        animationDrawable.start();	
    	TextView title=(TextView) findViewById(R.id.zq);
    	title.setText(this.strzq);
    	Button leftButton = (Button) findViewById(R.id.sbmit);
    	leftButton.setText(leftMessage);
    	Button rigthbtn=(Button) findViewById(R.id.exit);
    	rigthbtn.setText(rightMessage);
    	leftButton.setOnClickListener(leftOnClick);
    	rigthbtn.setOnClickListener(rightOnClick);
    	
    }
    
  
    
    public void setWidth(int width) {
		this.width = width;
	}


	public int getWidth() {
		return width;
	}

	public String getMessage() {
		return strzq;
	}
}
