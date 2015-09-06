package com.cn.math;


import com.cn.math.game.shudu.ShuduView;

import android.app.Activity;
import android.os.Bundle;

public class ShuDuActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new ShuduView(this));
	}
}
