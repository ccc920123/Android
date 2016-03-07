package com.cncyj.mostbrain;


import android.app.Activity;
import android.os.Bundle;

import com.cncyj.mostbrain.game.shudu.ShuduView;

public class ShuDuActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new ShuduView(this));
		
		
	}
}
