package com.cncyj.mostbrain.game.brainsharp;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	protected void click(String appId){
		if ("10001".equals(appId)) {
			Toast.makeText(BaseActivity.this, "��1��Ӧ��", Toast.LENGTH_SHORT).show();
		}else if ("10002".equals(appId)) {
			Toast.makeText(BaseActivity.this, "��2��Ӧ��", Toast.LENGTH_SHORT).show();
		}else if ("10003".equals(appId)) {
			Toast.makeText(BaseActivity.this, "��3��Ӧ��", Toast.LENGTH_SHORT).show();
		}
		else if ("10004".equals(appId)) {
			Toast.makeText(BaseActivity.this, "��4��Ӧ��", Toast.LENGTH_SHORT).show();
		}
		else if ("10005".equals(appId)) {
			Toast.makeText(BaseActivity.this, "��5��Ӧ��", Toast.LENGTH_SHORT).show();
		}
		else if ("10006".equals(appId)) {
			Toast.makeText(BaseActivity.this, "��6��Ӧ��", Toast.LENGTH_SHORT).show();
		}
		else if ("10007".equals(appId)) {
			Toast.makeText(BaseActivity.this, "��7��Ӧ��", Toast.LENGTH_SHORT).show();
		}
		else if ("10008".equals(appId)) {
			Toast.makeText(BaseActivity.this, "��8��Ӧ��", Toast.LENGTH_SHORT).show();
		}
		else if ("10009".equals(appId)) {
			Toast.makeText(BaseActivity.this, "��9��Ӧ��", Toast.LENGTH_SHORT).show();
		}
	}

}
