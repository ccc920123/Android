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
	/** 左边侧滑菜单 */
	private ListView menuListView;// 菜单列表
	private ImageButton Seting, menu_left;
	private String url = "file:///android_asset/ico.png";

	private Map<String, Integer> imageidMap = null;
	private GridView mGridView;
	private CommonAdapter<GridViewBean> mAdapter;
	private List<GridViewBean> data;
	private int[] imageid = { R.drawable.suangsu, R.drawable.shuduioc,
			R.drawable.shuduioc,R.drawable.shuduioc };
	private String[] title = { "算数", "数独", "你的世界","魔板" };

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
		mText.setText("启蒙数学");
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
	 * 侧滑部分
	 */
	private void sideslip() {

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
		item.put("item", "软件详情");
		item.put("image", R.drawable.alert_address_btn);
		data.add(item);
		item = new HashMap<String, Object>();
		item.put("item", "分享软件");
		item.put("image", R.drawable.icon_phone);
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
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cn.math");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("你的省心家教“启蒙数学”");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		//
		oks.setImagePath(PHONE_PATH);// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cn.math");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("宝宝没学习没兴趣，启蒙数学让宝宝学习有兴趣，让宝宝不输在起跑线上，学习的好助手，“启蒙数学”");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cn.math");

		// 启动分享GUI
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
