package com.cncyj.mostbrain.game.brainsharp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.cncyj.mostbrain.R;

public class CangContenActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cang_content);
	}
	
	
}
