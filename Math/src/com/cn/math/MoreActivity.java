package com.cn.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cn.math.game.shuduing.gameactivity.MyViewPagerActivity;
import com.cn.math.lib.DragLayout;
import com.cn.math.lib.DragLayout.DragListener;

public class MoreActivity extends Activity {

	private TextView mText;
	private Button mShudu;
	private DragLayout mDragLayout;
	/** 左边侧滑菜单 */
	private ListView menuListView;// 菜单列表
	private ImageButton Seting, menu_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activitymore);
		findViewid();
		sideslip();
	}
	private void findViewid() {
		Seting = (ImageButton) findViewById(R.id.menu_imgbtn);
		menu_left = (ImageButton) findViewById(R.id.menu_left);
		mShudu=(Button) findViewById(R.id.shudu);
		mText=(TextView) findViewById(R.id.apptext);
		mText.setText("脑力开发");
		menu_left.setOnClickListener(Click);
		mShudu.setOnClickListener(Click);
		Seting.setOnClickListener(Click);
	}
	
	private View.OnClickListener Click=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menu_left:
				mDragLayout.open();
				break;

			case R.id.shudu:
//				Intent itts=new Intent();
//				itts.setClass(MoreActivity.this, ShuDuActivity.class);
//				startActivity(itts);
//				finish();
				Intent intent = new Intent(MoreActivity.this,MyViewPagerActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				
				break;
			}
			
		}
	};
	/**
	 * 侧滑部分
	 */
	private void sideslip()
	{
		
		mDragLayout = (DragLayout) findViewById(R.id.dl);
		mDragLayout.setDragListener(new DragListener() {// 动作监听
					@Override
					public void onOpen() {
						Seting.setVisibility(View.GONE);
						menu_left.setVisibility(View.VISIBLE);
					}

					@Override
					public void onClose() {
						Seting.setVisibility(View.VISIBLE);
						menu_left.setVisibility(View.GONE);
					}

					@Override
					public void onDrag(float percent) {

					}
				});
		// 生成测试菜单选项数据
				List<Map<String, Object>> data = getMenuData();

				menuListView = (ListView) findViewById(R.id.menu_listview);
				menuListView.setAdapter(new SimpleAdapter(this, data,
						R.layout.qqredide_menulist_item_text, new String[] { "item",
								"image" }, new int[] { R.id.menu_text,
								R.id.menu_imageView1 }));
				initResideListener();// 自定义添加的东东
	}
	private List<Map<String, Object>> getMenuData() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> item;

		item = new HashMap<String, Object>();
		item.put("item", "试题设置");
		item.put("image", R.drawable.alert_address_btn);
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("item", "口诀表");
		item.put("image", R.drawable.reduce_cart);
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("item", "分享软件");
		item.put("image", R.drawable.icon_phone);
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("item", "更多精彩");
		item.put("image", R.drawable.more1_tabbar);
		data.add(item);
		return data;
	}
	private void initResideListener() {
		// 点击子菜单选项
		menuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
//					Intent itt=new Intent();
//					itt.setClass(MainActivity.this,CheckTopicActivity.class);
//					startActivity(itt);
//					finish();
					break;
				case 1:
//					Intent itts=new Intent();
//					itts.setClass(MainActivity.this, MantraSheetActivity.class);
//					startActivity(itts);
//					finish();
					break;
				case 2:
//					  Intent intent=new Intent(Intent.ACTION_SEND);
//					  intent.setType("image/*");   
//				      intent.putExtra(Intent.EXTRA_SUBJECT, "启蒙数学");
//				      intent.putExtra(Intent.EXTRA_TEXT, "让你的宝宝不输在起跑线上，学习的好助手，“启蒙数学”。");
//				      startActivity(Intent.createChooser(intent, getTitle()));
					break;
				case 3:
//					Toast.makeText(MainActivity.this, "更多精彩即将为您呈现",
//							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		});
	}
}
