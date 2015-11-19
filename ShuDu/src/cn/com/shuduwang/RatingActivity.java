package cn.com.shuduwang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

/**
 * RatingDialog
 * */
public class RatingActivity extends Activity {

	private int stage ;
	private int requestCode = 4 ;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.ratingdialog) ;
		//��intentȡ������ �ջ���������  ����+1�󴫵ݸ���һ��
		Intent intent = getIntent() ;
		float rating = intent.getFloatExtra("score", 0) ;
		stage = intent.getIntExtra("stage", 0)+1 ;
		
		((RatingBar)findViewById(R.id.ratingBar_dialog)).setRating(rating) ;
		findViewById(R.id.passgame_dialog).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�ر���һ����Ϸ
				finishActivity(requestCode) ;
				//��ת����һ��
				Intent intent = new Intent(RatingActivity.this, Sudoku_Activity.class) ;
				intent.putExtra("stage", stage) ;
				startActivity(intent) ;
				//�ر�dialog
				finish() ;
			}
		}) ;
	}
}
