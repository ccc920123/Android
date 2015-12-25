package com.cn.math;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.cn.math.dialog.AlerDialog;
import com.cn.math.utli.Calc;
import com.cn.math.utli.DisplayMetricsEN;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class YuorWorldActivity extends Activity {

	private String[] fh={"+","-","×"};
	private String[] fhs={"+","-","+"};
	private TextView numOne,fhOne,numTwo,fhTwo,numThree,fhThree,numFour,textResult;
	private Button [] btnOne;
	private Button [] btnTwo;
	private Button [] btnThree;
	private Button [] btnFour;
	private LinearLayout linearOne;
	RelativeLayout.LayoutParams btParams=null;
	private TableLayout tableLayout;
	private ProgressBar timePro;
	private Timer timer;
	private RadioButton checkRadio;
	private TextView textFenshu;
	private int num=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yuor_world);
		find();
		init();
		doing();
	}
	
	private void find() {
		timePro=(ProgressBar) findViewById(R.id.sys_pro);
		numOne=(TextView) findViewById(R.id.numone);
		fhOne=(TextView) findViewById(R.id.fhone);
		numTwo=(TextView) findViewById(R.id.numtwo);
		fhTwo=(TextView) findViewById(R.id.fhtwo);
		numThree=(TextView) findViewById(R.id.numthree);
		fhThree=(TextView) findViewById(R.id.fhthree);
		numFour=(TextView) findViewById(R.id.numfour);
		textResult=(TextView) findViewById(R.id.result);
		btnOne=new Button[40];
		tableLayout=(TableLayout) findViewById(R.id.table);
		checkRadio=(RadioButton) findViewById(R.id.checkjq);
		textFenshu=(TextView) findViewById(R.id.fenshu);
		
	}
	private void init() {
	
		setTopValus();
		createButton();
		textFenshu.setText("000");
	}
	
/**
 * 创建上面的结果
 * 
 * @throws 
 * @throw
 */
	private void setTopValus() {
		ArrayList list=createfh();
		int one=Integer.valueOf(list.get(0).toString());
		int two=Integer.valueOf(list.get(1).toString());
		int three=Integer.valueOf(list.get(2).toString());
		Random rand = new Random();
		String numone= String.valueOf(rand.nextInt(10));
		String numTwo=String.valueOf(rand.nextInt(10));
		String numThree=String.valueOf(rand.nextInt(10));
		String numFour=String.valueOf(rand.nextInt(10));
		if(checkRadio.isChecked())
		{
			int i = new Calc().process(numone+fh[one]+numTwo+fh[two]+numThree+fh[three]+numFour);
			textResult.setText(String.valueOf(i));
			fhOne.setText(fh[one]);
			fhTwo.setText(fh[two]);
			fhThree.setText(fh[three]);
		}else{
		int i = new Calc().process(numone+fhs[one]+numTwo+fhs[two]+numThree+fhs[three]+numFour);
		textResult.setText(String.valueOf(i));
		fhOne.setText(fhs[one]);
		fhTwo.setText(fhs[two]);
		fhThree.setText(fhs[three]);
		}
		
	}

	/**
	 * 创建符号
	 * 
	 * @throws 
	 * @throw
	 */
	private ArrayList createfh() {
		ArrayList list = new ArrayList();
		int n = 3;
		Random rand = new Random();
		boolean[] bool = new boolean[n];
		int num = 0;
		for (int i = 0; i < n; i++) {
		do {
		// 如果产生的数相同继续循环
		num = rand.nextInt(n);
		} while (bool[num]);
		bool[num] = true;
		list.add(num);
		}
		return list;
	}
	/**
	 * 创建按钮
	 * 
	 * @throws 
	 * @throw
	 */
	private void createButton()
	{
		for (int j = 0; j < 4; j++) {
			TableRow row=new TableRow(this);
			row.setPadding(0, 8, 0, 8);
			if(j==0){
				row.setBackgroundColor(getResources().getColor(R.color.bai));
			}else if(j==1){
				row.setBackgroundColor(getResources().getColor(R.color.hui));
			}else if(j==2)
			{
				row.setBackgroundColor(getResources().getColor(R.color.lan));
			}else{
				row.setBackgroundColor(getResources().getColor(R.color.hong));
			}
		
		for (int i = 0; i < 10; i++) {
			Button b=new Button(this);
			b.setTag(i);
			b.setText(String.valueOf(i));
//			b.setTextSize(getResources().getDimension(R.dimen.activity_vertical_margins));
//			b.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1));
			btnOne[i]=b;
			row.addView(btnOne[i]);
		
		  }
		tableLayout.addView(row);
		}
		
		
	}
	
	private void doing() {
		int count=tableLayout.getChildCount();
        for (int i = 0; i < count; i++) {
//        	y=i;
        	TableRow row=(TableRow) tableLayout.getChildAt(i);
        	int rownum = row.getChildCount();
        	for(int j=0;j<rownum;j++)
        	{
        		Button button = (Button) row.getChildAt(j);
        		if(i==0){
        		button.setOnClickListener(button_click0);
        		}else if(i==1)
        		{
        			button.setOnClickListener(button_click1);
        		}else if(i==2)
        		{
        			button.setOnClickListener(button_click2);
        		}else{
        			button.setOnClickListener(button_click3);
        		}
        		
        	}
		}
        
        time();//开始进度条
        
       
        
	}
	private void time() {
		 timePro.setMax(1200);
	        //開始計時
		 if(timer==null){
	        timer=new Timer();
	      //计时器任务，延迟多少开始数，数的速度
	        timer.schedule(new TimerTask() {
	    		int second = 1200;
	    		@Override
	    		public void run() {
	    			Message msg = new Message();
	    			msg.what = second;
	    			handler.sendMessage(msg);
	    			second--;
	    		}
	    	}, 0, 100);
		 }
		
	}
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
			if (msg.what > 0) {
				timePro.setProgress(msg.what);
			} else {
				if(timer!=null)
				{
				timer.cancel();
				timer	= null;
				}
				AlerDialog.exitDialog(YuorWorldActivity.this,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								AlerDialog.closeDialog();
								textFenshu.setText("000");
								num=0;
								time();
                               

								

							}
						}, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								AlerDialog.closeDialog();
								finish();
							}
						},"时间到,请选择确定继续，取消返回");
			}
		}
	};
	
	
	private View.OnClickListener button_click0 = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Button v1 = (Button) v;
			numOne.setText(v1.getText().toString());
            String none=numOne.getText().toString();
			String ntwo = numTwo.getText().toString();
			String nthree = numThree.getText().toString();
			String nfour = numFour.getText().toString();
			String fhone=fhOne.getText().toString();
			String fhtwo=fhTwo.getText().toString();
			String fhthree=fhThree.getText().toString();
			if (!"".equals(ntwo) && !"".equals(nthree) && !"".equals(nfour)) {

				if (Integer.parseInt(textResult.getText().toString()) == new Calc()
						.process(none+fhone + ntwo
								+ fhtwo + nthree + fhthree + nfour)) {
					setTopValus();
					numOne.setText("");
					numTwo.setText("");
					numThree.setText("");
					numFour.setText("");
					num=num+100;
					textFenshu.setText(String.valueOf(num));
				}

			}

		}
	};
	private View.OnClickListener button_click1 = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Button v1 = (Button) v;

			numTwo.setText(v1.getText().toString());
			String none = numOne.getText().toString();
			String ntwo=numTwo.getText().toString();
			String nthree = numThree.getText().toString();
			String nfour = numFour.getText().toString();
			String fhone=fhOne.getText().toString();
			String fhtwo=fhTwo.getText().toString();
			String fhthree=fhThree.getText().toString();

			if (!"".equals(none) && !"".equals(nthree) && !"".equals(nfour)) {
				if (Integer.parseInt(textResult.getText().toString()) == new Calc()
						.process(none + fhone + ntwo+ fhtwo + nthree + fhthree + nfour)) {
					setTopValus();
					numOne.setText("");
					numTwo.setText("");
					numThree.setText("");
					numFour.setText("");
					num=num+100;
					textFenshu.setText(String.valueOf(num));
				}
			}

		}
	};
	private View.OnClickListener button_click2 = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Button v1 = (Button) v;
			numThree.setText(v1.getText().toString());

			String none = numOne.getText().toString();
			String ntwo = numTwo.getText().toString();
			String nthree=numThree.getText().toString();
			String nfour = numFour.getText().toString();
			String fhone=fhOne.getText().toString();
			String fhtwo=fhTwo.getText().toString();
			String fhthree=fhThree.getText().toString();

			if (!"".equals(none) && !"".equals(ntwo) && !"".equals(nfour)) {
				if (Integer.parseInt(textResult.getText().toString()) == new Calc()
						.process(none + fhone + ntwo + fhtwo
								+ nthree + fhthree
								+ nfour)) {
					setTopValus();
					numOne.setText("");
					numTwo.setText("");
					numThree.setText("");
					numFour.setText("");
					num=num+100;
					textFenshu.setText(String.valueOf(num));
				}
			}
		}
	};
	private View.OnClickListener button_click3 = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Button v1 = (Button) v;
			numFour.setText(v1.getText().toString());
			String none = numOne.getText().toString();
			String ntwo = numTwo.getText().toString();
			String nthree = numThree.getText().toString();
			String nfour=numFour.getText().toString();
			String fhone=fhOne.getText().toString();
			String fhtwo=fhTwo.getText().toString();
			String fhthree=fhThree.getText().toString();

			if (!"".equals(none) && !"".equals(ntwo) && !"".equals(nthree)) {
				if (Integer.parseInt(textResult.getText().toString()) == new Calc()
						.process(none + fhone + ntwo + fhtwo + nthree + fhthree
								+nfour)) {
					setTopValus();
					numOne.setText("");
					numTwo.setText("");
					numThree.setText("");
					numFour.setText("");
					num=num+100;
					textFenshu.setText(String.valueOf(num));
					
				}
			}
		}
	};
	@Override
	protected void onDestroy() {
		if(timer!=null)
		{
		timer.cancel();
		}
		super.onDestroy();
	}
	
	
}
