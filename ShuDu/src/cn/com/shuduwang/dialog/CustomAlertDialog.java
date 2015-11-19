package cn.com.shuduwang.dialog;


import cn.com.shuduwang.R;
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
	
    public CustomAlertDialog(Context context,String strzq,String leftmsg,View.OnClickListener leftOnClick,String rightMessage,View.OnClickListener rightOnClick,boolean issb){
        super(context, R.style.MyDialog);
        this.strzq=strzq;
        this.leftMessage=leftmsg;
		this.leftOnClick=leftOnClick;
		this.rightMessage=rightMessage;
		this.rightOnClick=rightOnClick;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.succeed_inclde);
		initViews();
    }
    
    void initViews(){
    	ImageView image=(ImageView) findViewById(R.id.imageView1);
    	AnimationDrawable animationDrawable = (AnimationDrawable) image.getBackground();
        animationDrawable.start();	
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
