package com.cncyj.mostbrain;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CheckTopicActivity extends Activity {

	private SharedPreferences sp;
	private RadioGroup mGroup;
	private RadioButton mCheckBox10,mCheckBox10s,mCheckBox100,mCheckBox100s;
	private Button mTopicButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_checktopic);
		sp=this.getSharedPreferences("MATH", MODE_PRIVATE);
		String check=sp.getString("MATHCHECK", "1000");
		findView();
		mTopicButton.setOnClickListener(but_Click);
		
		if(check.equals("1"))
		{
			mCheckBox10.setChecked(true);
		}else if(check.equals("2"))
		{
			mCheckBox10s.setChecked(true);
		}else if(check.equals("3"))
		{
			mCheckBox100.setChecked(true);
		}else if(check.equals("4"))
		{
			mCheckBox100s.setChecked(true);
		}else{
			mCheckBox10.setChecked(true);
		}
		
		
	}
	private void findView() {
		
		mGroup=(RadioGroup) findViewById(R.id.checkgroup);
		
		mCheckBox10=(RadioButton) findViewById(R.id.checkBox1);
		mCheckBox10s=(RadioButton) findViewById(R.id.checkBox2);
		mCheckBox100=(RadioButton) findViewById(R.id.checkBox3);
		mCheckBox100s=(RadioButton) findViewById(R.id.checkBox4);
		mTopicButton=(Button) findViewById(R.id.button1);
		
	}
	
	private OnClickListener but_Click=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Editor editor = sp.edit();
			
			
			if(mCheckBox10.isChecked())
			{
				editor.putString("MATHCHECK", "1");
			}
			if(mCheckBox10s.isChecked())
			{
				editor.putString("MATHCHECK", "2");
			}
			if(mCheckBox100.isChecked())
			{
				editor.putString("MATHCHECK", "3");
			}
			if(mCheckBox100s.isChecked())
			{
				editor.putString("MATHCHECK", "4");
			}
			editor.commit();
			
			Intent itt=new Intent();
			itt.setClass(CheckTopicActivity.this,MainActivity.class);
			startActivity(itt);
			finish();
			
			
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
	
}
