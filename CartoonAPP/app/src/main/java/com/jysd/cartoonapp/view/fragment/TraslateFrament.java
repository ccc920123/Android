package com.jysd.cartoonapp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.jysd.cartoonapp.R;
import com.jysd.cartoonapp.view.activity.MainActivity;

public class TraslateFrament extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		View view = inflater.inflate(bundle.getInt("layout"), null);
		int i=(Integer) bundle.get("key");
		if (i == 2) {
			Button mStartWeiboImageButton = (Button) view
					.findViewById(R.id.iv_start_weibo);
			mStartWeiboImageButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// ��������ֵ
					setGuided();

					// ��ת
					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					getActivity().startActivity(intent);
					getActivity().finish();
					getActivity().overridePendingTransition(0, 0);// ���finish()��SetFlagsʧЧ

				}

			});
		}
		return view;
	}

	/**
	 * method desc: �������ֵ ʹ�´�������������
	 */
	private void setGuided() {
		SharedPreferences preferences = getActivity().getSharedPreferences(
				"TOYPOP_SP", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		// ��������
		editor.putBoolean("isFirstInTOYPOP_SP", false);
		// �ύ�޸�
		editor.commit();
	}
}