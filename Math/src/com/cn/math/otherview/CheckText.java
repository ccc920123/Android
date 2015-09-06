package com.cn.math.otherview;


import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * æ£?µ‹ç»“æžœ
 * 
 * @author CXY
 * 
 */
public class CheckText extends TextView {
	private int value = 0;

	public CheckText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setText("");
		setTextSize(18.0F);
//		setBackgroundDrawable(getResources().getDrawable(R.drawable.reto));
		setPadding(6, 6, 6, 6);
		getPaint().setFakeBoldText(true);
		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (CheckText.this.value >= 3) {
					CheckText.this.value = 1;
				} else {
					CheckText.this.value++;
				}
				CheckText.this.setFocusableInTouchMode(true);
				CheckText.this.requestFocus();
				CheckText.this.setValue(CheckText.this.value);

			}
		});
	}

	public int getValue() {
		
		if(this.getText().toString().equals("+"))
		{
			return 1;
		}else if(this.getText().toString().equals("-")){
		return 2;
		}else if(this.getText().toString().equals("*")){
			return 3;
		}else if(this.getText().toString().equals("/"))
		{
			return 4;
		}
		return 0;
	}

	public void setValue(int paramInt) {
		switch (paramInt) {
		default:
		case 0:
			setText("");
			break;
		case 1:
			setText("+");
			break;
		case 2:
			setText("-");
			break;
		case 3:
			setText("x");
			break;
		case 4:
			setText("¡Â");
			break;
		}
	}

	public void setValue(int paramInt, boolean paramBoolean) {
		setEnabled(paramBoolean);
		setValue(paramInt);
	}
}
