package com.cncyj.mostbrain.game.brainsharp.utli;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cncyj.mostbrain.R;

/**
 * <p>
 * Title: CustomDialog
 * </p>
 * <p>
 * Description:�Զ���Dialog����������Dialog��ʽ�ļ���Dialog�����ļ���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * 
 * @author archie
 * @version 1.0
 */
public class CustomDialog extends Dialog implements android.view.View.OnClickListener {
	int layoutRes;// �����ļ�
	Context context;
	/** ȷ����ť **/
	private Button confirmBtn;
	/** ȡ����ť **/
	private Button cancelBtn;

	public CustomDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * �Զ��岼�ֵĹ��췽��
	 * 
	 * @param context
	 * @param resLayout
	 */
	public CustomDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * �Զ������⼰���ֵĹ��췽��
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public CustomDialog(Context context, int theme, int resLayout) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(layoutRes);
		
		// ����id�ڲ������ҵ��ؼ�����
		confirmBtn = (Button) findViewById(R.id.confirm_btn);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		
		// ���ð�ť���ı���ɫ
		confirmBtn.setTextColor(0xff1E90FF);
		cancelBtn.setTextColor(0xff1E90FF);
		
		// Ϊ��ť�󶨵���¼�������
		confirmBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.confirm_btn:
			break;
		case R.id.cancel_btn:
			break;
		}
	}
}