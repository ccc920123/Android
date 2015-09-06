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
	/** ��߲໬�˵� */
	private ListView menuListView;// �˵��б�
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
		mText.setText("��������");
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
	 * �໬����
	 */
	private void sideslip()
	{
		
		mDragLayout = (DragLayout) findViewById(R.id.dl);
		mDragLayout.setDragListener(new DragListener() {// ��������
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
		// ���ɲ��Բ˵�ѡ������
				List<Map<String, Object>> data = getMenuData();

				menuListView = (ListView) findViewById(R.id.menu_listview);
				menuListView.setAdapter(new SimpleAdapter(this, data,
						R.layout.qqredide_menulist_item_text, new String[] { "item",
								"image" }, new int[] { R.id.menu_text,
								R.id.menu_imageView1 }));
				initResideListener();// �Զ�����ӵĶ���
	}
	private List<Map<String, Object>> getMenuData() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> item;

		item = new HashMap<String, Object>();
		item.put("item", "��������");
		item.put("image", R.drawable.alert_address_btn);
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("item", "�ھ���");
		item.put("image", R.drawable.reduce_cart);
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("item", "�������");
		item.put("image", R.drawable.icon_phone);
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("item", "���ྫ��");
		item.put("image", R.drawable.more1_tabbar);
		data.add(item);
		return data;
	}
	private void initResideListener() {
		// ����Ӳ˵�ѡ��
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
//				      intent.putExtra(Intent.EXTRA_SUBJECT, "������ѧ");
//				      intent.putExtra(Intent.EXTRA_TEXT, "����ı����������������ϣ�ѧϰ�ĺ����֣���������ѧ����");
//				      startActivity(Intent.createChooser(intent, getTitle()));
					break;
				case 3:
//					Toast.makeText(MainActivity.this, "���ྫ�ʼ���Ϊ������",
//							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		});
	}
}
