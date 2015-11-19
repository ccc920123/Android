package com.cn.math;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class YuorWorldActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yuor_world);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.yuor_world, menu);
		return true;
	}

}
