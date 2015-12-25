package com.cn.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cn.math.R;
import com.cn.math.dialog.AlerDialog;
import com.cn.math.game.shuzimianban.DataHelper;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShuZiMianBanActivity extends Activity {
    private LinearLayout ll;
    private TextView[][] tv_group;
    private int number[];
    Vibrator mVibrator;
	private TextView tv_socer;
	private Button btnrestart;
	private int socer = 0;
	List<String> list;
	 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuzimianban);
        list=DataHelper.getDate();
        int sizelenght=list.size();
        int key=(int) (Math.random()*sizelenght);
        
        char[] newnumbe=list.get(key).toCharArray();
        number = new int[newnumbe.length];
        for(int i=0;i<newnumbe.length;i++){
        	number[i] = Integer.parseInt(String.valueOf(newnumbe[i]));
        }
        initView();
        initGame();
        tv_socer.setText("移动了 "+String.valueOf(socer)+" 步");
        mVibrator = (Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
		ll.setOnTouchListener(new OnTouchListener() {

			private int x1, x2;
			private int y1, y2;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					x1 = (int) event.getX();
					y1 = (int) event.getY();
					break;

				case MotionEvent.ACTION_UP:
					x2 = (int) event.getX();
					y2 = (int) event.getY();

					if (y2 - y1 > 20 && y2 - y1 > Math.abs(x2 - x1)) {// 判断手势方向
						System.out.println("下");
						down();
						overGame();
						initGame();
					} else if (y1 - y2 > 20 && y1 - y2 > Math.abs(x2 - x1)) {
						System.out.println("上");
						up();
						overGame();
						initGame();
					} else if (x2 - x1 > 20 && x2 - x1 > Math.abs(y2 - y1)) {
						System.out.println("左");
						left();
						overGame();
						initGame();
					} else if (x1 - x2 > 20 && x1 - x2 > Math.abs(y2 - y1)) {
						System.out.println("右");
						right();
						overGame();
						initGame();
					}

					break;
				}
				return true;
			}
		});
		
		btnrestart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int sizelenght=list.size();
		        int key=(int) (Math.random()*sizelenght);
		        
		        char[] newnumbe=list.get(key).toCharArray();
		        number = new int[newnumbe.length];
		        for(int i=0;i<newnumbe.length;i++){
		        	number[i] = Integer.parseInt(String.valueOf(newnumbe[i]));
		        }
		    	AlerDialog.exitDialog(ShuZiMianBanActivity.this,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								AlerDialog.closeDialog();
								initGame();
								 socer = 0 ;
								 tv_socer.setText("移动了 "+String.valueOf(socer)+" 步");
								

							}
						}, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								AlerDialog.closeDialog();
								
							}
						},"确定重新开始吗?");
		        
		        
				 
				
				 
			}
		});
    }

	protected void left() {
		int numzero = FindZero();
		int exchanged;
		if(numzero%3==0){
			//定义震动
	    mVibrator.vibrate( new long[]{100,10,100,200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
		}else{
			exchanged = number[numzero];
			number[numzero]=number[numzero-1];
			number[numzero-1]=exchanged;
			socer = socer + 1;
			tv_socer.setText("移动了 "+String.valueOf(socer)+" 步");
		}	
	}

	protected void right() {
		int numzero = FindZero();
		int exchanged;
		if(numzero%3==2){
			//定义震动
	    mVibrator.vibrate( new long[]{100,10,100,200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
		}else{
			exchanged = number[numzero];
			number[numzero]=number[numzero+1];
			number[numzero+1]=exchanged;
			socer = socer + 1;
			tv_socer.setText("移动了 "+String.valueOf(socer)+" 步");
		}	
		
	}

	protected void up() {
		int numzero = FindZero();
		int exchanged;
		if(numzero>5){
			//定义震动
	    mVibrator.vibrate( new long[]{100,10,100,200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
		}else{
			exchanged = number[numzero];
			number[numzero]=number[numzero+3];
			number[numzero+3]=exchanged;
			socer = socer + 1;
			tv_socer.setText("移动了 "+String.valueOf(socer)+" 步");
		}
	}
	public void down() {
		int numzero = FindZero();
		int exchanged;
		if(numzero<3){
			//定义震动
	    mVibrator.vibrate( new long[]{100,10,100,200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
		}else{
			exchanged = number[numzero];
			number[numzero]=number[numzero-3];
			number[numzero-3]=exchanged;
			socer = socer + 1;
			tv_socer.setText("移动了 "+String.valueOf(socer)+" 步");
		}	
	}
	public int FindZero(){
		for(int i=0;i<9;i++){
			if(number[i]==0){
				return i;
			}
		}
		return 0;	
	}
	
	
	
	
	private void initView() {
		tv_group = new TextView[3][3];
		ll= (LinearLayout)findViewById(R.id.ll);
		tv_group[0][0] = (TextView) findViewById(R.id.tv_1);
		tv_group[0][1] = (TextView)findViewById(R.id.tv_2);
		tv_group[0][2] = (TextView)findViewById(R.id.tv_3);
		tv_group[1][0] = (TextView)findViewById(R.id.tv_4);
		tv_group[1][1] = (TextView)findViewById(R.id.tv_5);
		tv_group[1][2] = (TextView)findViewById(R.id.tv_6);
		tv_group[2][0] = (TextView)findViewById(R.id.tv_7);
		tv_group[2][1] = (TextView)findViewById(R.id.tv_8);
		tv_group[2][2] = (TextView)findViewById(R.id.tv_9);
		tv_socer = (TextView)findViewById(R.id.tv_socer);
		btnrestart=(Button)findViewById(R.id.btn_restart);
	}
	public void initGame(){
		
		for(int i=0 ;i<3;i++){
			for(int j=0;j<3;j++){
				if(number[i*3+j]==0){
					tv_group[i][j].setText(" ");
				}else{
					System.out.println("number-----"+number[i*3+j]);
					tv_group[i][j].setText(String.valueOf(number[i*3+j]));
				}
			}
		}
		
		
		
	}
	
	public void overGame(){
		int total=0;
       for(int i = 0;i<7;i++){
    	   if(number[i+1]-number[i]==1){
    		   total=total+1;
    		 }
       }
       if(total==7&&number[8]==0){
		   Toast.makeText(ShuZiMianBanActivity.this, "GameOver", 0).show();
		   int sizelenght=list.size();
	        int key=(int) (Math.random()*sizelenght);
	        
	        char[] newnumbe=list.get(key).toCharArray();
	        number = new int[newnumbe.length];
	        for(int i=0;i<newnumbe.length;i++){
	        	number[i] = Integer.parseInt(String.valueOf(newnumbe[i]));
	        }
		   socer = 0 ;
		   tv_socer.setText("移动了 "+String.valueOf(socer)+" 步");
		   System.out.println("GameOver");
	   }
	}
}
