package com.cdjysdkj.diary;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cdjysdkj.diary.adapter.FruitAdapter;
import com.cdjysdkj.diary.anomotion.Dynamics;
import com.cdjysdkj.diary.application.BaseHomeActivity;
import com.cdjysdkj.diary.tab.TextDiaryTab;
import com.cdjysdkj.diary.tabhelper.TextDiaryTabHelper;
import com.cdjysdkj.diary.view.internal.AdapterView;
import com.cdjysdkj.diary.widget.dialog.UpDialog;

import java.util.List;

public class TextDiaryActivity extends BaseHomeActivity {

	private com.cdjysdkj.diary.view.internal.ListView listView;
	FruitAdapter adapter;
	private UpDialog myDialog;
	private int longClickPosition;
	private View currentItemView;
	private List<TextDiaryTab> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_diary);
		listView = (com.cdjysdkj.diary.view.internal.ListView) findViewById(R.id.my_listview);
		myDialog = new UpDialog(this, R.style.MyDialogStyle);
		////
		adapter = new FruitAdapter(TextDiaryActivity.this,
				R.layout.text_list_item, getDatafromDatabase());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("op", "look");
				TextDiaryTab diary = (TextDiaryTab) adapter.getItem(position);
				bundle.putSerializable("DIARY", diary);
				goToActivity(TextDiaryActivity.this, WriteDiaryActivity.class,
						bundle);
				finish();
			}
		});
//
	}
	/**
	 * 从数据库中获取日记信息
	 *
	 * @return
	 */
	private List<TextDiaryTab> getDatafromDatabase() {
		TextDiaryTabHelper diaryTabHelper = TextDiaryTabHelper.getInstance();
		list = diaryTabHelper.queryAll();
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	protected String getActionBarTitle() {
		return "我的日记";
	}

	@Override
	protected boolean isHomeAsUpEnabled() {
		return true;
	}

	@Override
	protected void onHomeActionClick() {
		goToActivity(this, HomeActivity.class, null);
		finish();
	}

	class SimpleDynamics extends Dynamics {

		private float mFrictionFactor;
		private float mSnapToFactor;

		public SimpleDynamics(final float frictionFactor,
							  final float snapToFactor) {
			mFrictionFactor = frictionFactor;
			mSnapToFactor = snapToFactor;
		}

		@Override
		protected void onUpdate(final int dt) {
			mVelocity += getDistanceToLimit() * mSnapToFactor;
			// 速度 * 时间间隔 = 间隔时间内位移
			mPosition += mVelocity * dt / 1000;
			// 减速， 供下次onUpdate使用
			mVelocity *= mFrictionFactor;
		}
	}

	@Override
	protected void addActions() {
		actionBar.setRightAction(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Bundle bundle = new Bundle();
				bundle.putString("op", "new");
				goToActivity(TextDiaryActivity.this, WriteDiaryActivity.class,
						bundle);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		goToActivity(this, HomeActivity.class, null);
		finish();
	}


}
