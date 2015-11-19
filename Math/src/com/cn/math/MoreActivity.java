package com.cn.math;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.cn.math.adpter.CommonAdapter;
import com.cn.math.adpter.ViewHolder;
import com.cn.math.bean.GridViewBean;
import com.cn.math.dialog.AlerDialog;
import com.cn.math.game.shuduing.gameactivity.MyViewPagerActivity;
import com.cn.math.lib.DragLayout;
import com.cn.math.lib.DragLayout.DragListener;
import com.cn.math.utli.CopyFileToSDcard;

public class MoreActivity extends Activity implements OnItemClickListener {
	public static final String PHONE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "Math/math.jpg";
	private TextView mText;
	private Button mShudu, mJiSuangTi;
	private DragLayout mDragLayout;
	/** ��߲໬�˵� */
	private ListView menuListView;// �˵��б�
	private ImageButton Seting, menu_left;
	private String url = "file:///android_asset/ico.png";

	private Map<String, Integer> imageidMap = null;
	private GridView mGridView;
	private CommonAdapter<GridViewBean> mAdapter;
	private List<GridViewBean> data;
	private int[] imageid = { R.drawable.suangsu, R.drawable.shuduioc,
			R.drawable.shuduioc,R.drawable.shuduioc };
	private String[] title = { "����", "����", "�������","ħ��" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activitymore);
		findViewid();
		initDatas();
		initView();
		sideslip();
		CopyFileToSDcard.copyFileToSdCard(MoreActivity.this, PHONE_PATH);
	}

	private void initDatas() {
		GridViewBean itemBean = null;
		data = new ArrayList<GridViewBean>();
		for (int i = 0; i < imageid.length; i++) {
			itemBean = new GridViewBean(imageid[i], title[i]);
			data.add(itemBean);
		}

	}

	private void initView() {
		mText.setText("������ѧ");
		menu_left.setOnClickListener(Click);
		mGridView.setOnItemClickListener(this);
		Seting.setOnClickListener(Click);

		mGridView.setAdapter(mAdapter = new CommonAdapter<GridViewBean>(
				getApplicationContext(), data, R.layout.gridviewimage) {

			@Override
			public void convert(ViewHolder helper, GridViewBean item) {
				helper.setImageResource(R.id.img, item.getImage());
				helper.setText(R.id.title, item.getTitMessage());
			}
		});

	}

	private void findViewid() {
		Seting = (ImageButton) findViewById(R.id.menu_imgbtn);
		menu_left = (ImageButton) findViewById(R.id.menu_left);
		// mShudu=(Button) findViewById(R.id.shudu);
		// mJiSuangTi=(Button) findViewById(R.id.jisuangti);
		mText = (TextView) findViewById(R.id.apptext);
		mGridView = (GridView) findViewById(R.id.gridview);

	}

	private View.OnClickListener Click = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menu_imgbtn:
				mDragLayout.open();
				break;
			case R.id.menu_left:
				mDragLayout.open();
				break;
			}

		}
	};

	/**
	 * �໬����
	 */
	private void sideslip() {

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
		item.put("item", "�������");
		item.put("image", R.drawable.alert_address_btn);
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("item", "�������");
		item.put("image", R.drawable.icon_phone);
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
					Intent itt = new Intent();
					itt.setClass(MoreActivity.this, AboutusActivity.class);
					startActivity(itt);
					break;
				case 1:
					showShare();
					break;
				default:
					break;
				}
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlerDialog.exitDialog(MoreActivity.this,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							System.exit(0);

						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							AlerDialog.closeDialog();
						}
					});
		}

		return false;
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();

		// ����ʱNotification��ͼ������� 2.5.9�Ժ�İ汾�����ô˷���
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		oks.setTitle(getString(R.string.share));
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cn.math");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText("���ʡ�ļҽ̡�������ѧ��");
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		//
		oks.setImagePath(PHONE_PATH);// ȷ��SDcard������ڴ���ͼƬ
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cn.math");
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		oks.setComment("����ûѧϰû��Ȥ��������ѧ�ñ���ѧϰ����Ȥ���ñ����������������ϣ�ѧϰ�ĺ����֣���������ѧ��");
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		oks.setSite(getString(R.string.app_name));
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		oks.setSiteUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cn.math");

		// ��������GUI
		oks.show(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ImageView image = (ImageView) view.findViewById(R.id.img);
		Animation alphaAnimation = AnimationUtils.loadAnimation(this,
				R.anim.probtn);
		image.startAnimation(alphaAnimation);

		switch (position) {
		case 0:

			// Intent itts=new Intent();
			// itts.setClass(MoreActivity.this, ShuDuActivity.class);
			// startActivity(itts);
			// finish();

			Intent ittmain = new Intent();
			ittmain.setClass(MoreActivity.this, MainActivity.class);
			startActivity(ittmain);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			finish();

			break;
		case 1:
			Intent intent = new Intent(MoreActivity.this,
					MyViewPagerActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

			break;
		case 2:
			
			Intent yourworld = new Intent(MoreActivity.this,
					YuorWorldActivity.class);
			startActivity(yourworld);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

			break;
			case 3:
				
				Intent intentshuzimianban = new Intent(MoreActivity.this,
					ShuZiMianBanActivity.class);
			startActivity(intentshuzimianban);
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				
				break;
		default:
			break;
		}

	}
}
