package com.cncyj.mostbrain;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cncyj.mostbrain.utli.ToastFactory;


public class AdviceActivity extends Activity {
	private EditText editText;
	private Button button;
	private ImageButton imageback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advice);
		getView();
		init();
	}

	private void init() {
		imageback.setOnClickListener(l);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String email = editText.getEditableText().toString();
				if (!"".equals(email)) {
					mail(email);
				} else {
					ToastFactory.getToast(AdviceActivity.this, "请输入您宝贵的意见或建议")
							.show();
				}
			}
		});
	}

	private void mail(String strjh) {

		// 必须明确使用mailto前缀来修饰邮件地址,如果使用
		// intent.putExtra(Intent.EXTRA_EMAIL, email)，结果将匹配不到任何应用
		Uri uri = Uri.parse("mailto:" + "645503254@qq.com"); // 616707902@qq.com
																// mailto:
		// String[] email = {"3802**92@qq.com"};
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		// intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
		intent.putExtra(Intent.EXTRA_SUBJECT, "意见或建议"); // 主题
		intent.putExtra(Intent.EXTRA_TEXT, strjh); // 正文
		startActivity(Intent.createChooser(intent, "请选择邮件类应用"));

	}


	private void getView() {
		editText = (EditText) findViewById(R.id.advice);
		button = (Button) findViewById(R.id.submit);
		imageback= (ImageButton)findViewById(R.id.imageback);
	}
private View.OnClickListener l=new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		Afinish();
	}
};



	@Override
	public void onBackPressed() {
		Afinish();

	}

	private void Afinish() {
		Intent it=new Intent(this,MoreActivity.class);
        startActivity(it);	
		finish();
	}
}
