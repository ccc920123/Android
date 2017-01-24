package com.cncyj.mostbrain.game.brainsharp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cncyj.mostbrain.MoreActivity;
import com.cncyj.mostbrain.R;
import com.cncyj.mostbrain.game.brainsharp.adpter.HomeAddappAdapter;
import com.cncyj.mostbrain.game.brainsharp.adpter.HomeEditappAdapter;
import com.cncyj.mostbrain.game.brainsharp.chartcart.ChartActivity;
import com.cncyj.mostbrain.game.brainsharp.modle.AppInfo;
import com.cncyj.mostbrain.game.brainsharp.utli.CommonUtil;
import com.cncyj.mostbrain.game.brainsharp.utli.DateUtil;
import com.cncyj.mostbrain.game.kuaifanying.LiveActivity;

public class HomeAddappActivity extends Activity {
	
	private ListView listView;
	private HomeAddappAdapter addappAdapter;
//	private List<AppInfo> appInfos;
//	private Button confim_btn;
	private Button btn_gd;
//	private DatabaseHelper sqlHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
	
	public static List<AppInfo> allApps;
	public static LinkedList<AppInfo> addAppInfos;
	private HomeEditappAdapter editappAdapter;
	private long firstime = 0;
	private Button btn_cang;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_add_app);
		initAppInfos();
		editappAdapter = new HomeEditappAdapter(HomeAddappActivity.this);
		editappAdapter.setData(addAppInfos);
		
		//搜藏
		btn_cang=(Button) this.findViewById(R.id.Button_left_cang);
		btn_cang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 Intent intent=new Intent();
			     intent.setClass(HomeAddappActivity.this, ChartActivity.class);
			     startActivity(intent); //启动一个新的Activity
			}
		});
		
		
		initView();
		//关于
		btn_gd=(Button) this.findViewById(R.id.home_add_app_btn_f);
		
		btn_gd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(allApps!=null)
				{
					allApps.clear();
					allApps=null;
				}
				if(addAppInfos!=null)
				{
					addAppInfos.clear();
					addAppInfos=null;
				}
				  	   Intent itt=new Intent();
				  	   itt.setClass(HomeAddappActivity.this,MoreActivity.class);
				  	   
				  		startActivity(itt);
				  		finish();
				
			}
		});
	}
	
	
	public void initView(){
		listView=(ListView) findViewById(R.id.home_add_app_listview);
		addappAdapter=new HomeAddappAdapter(HomeAddappActivity.this);
		addappAdapter.setData(allApps);
		listView.setAdapter(addappAdapter);
	}
	private void initAppInfos() {
		allApps = new ArrayList<AppInfo>();
		addAppInfos = new LinkedList<AppInfo>();
		
		// Map<String,String> homeAppMap = getHomeAppMapFromSql();
		for (int i = 0; i < CommonUtil.MainAppApp.length; i++) {
			AppInfo appInfo = (CommonUtil.getApp(CommonUtil.MainAppApp[i]));
			appInfo.setId(i + 1);
			allApps.add(appInfo);
		}
		AppInfo _appInfo = (CommonUtil.getApp(CommonUtil.appId10));
		addAppInfos.add(_appInfo);
	}
	
	private void updateHomeApp(){
//		ArrayList<HomeLastData> hldList = new ArrayList<HomeLastData>();
//		for(AppInfo appinfo:HomeActivity.allApps){
//			HomeLastData hld = new HomeLastData();
//			hld.setId(appinfo.getId());
//			hld.setData(appinfo.getAppId());
//			hldList.add(hld);
//		}
//		try {
//			Dao<HomeLastData,Integer> dao = sqlHelper.getHomeLastDataDao();
//			dao.delete(hldList);
//			for(AppInfo appinfo:HomeActivity.addAppInfos){
//				if(!appinfo.getAppId().equals(CommonUtil.appId10)){
//					HomeLastData _hld = new HomeLastData();
//					_hld.setId(appinfo.getId());
//					_hld.setData(appinfo.getAppId());
//					dao.createOrUpdate(_hld);
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
//	
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			Intent intent = new Intent(HomeAddappActivity.this, HomeActivity.class);
//			setResult(RESULT_OK, intent);
//			finish();
//			return false;
//		}
//		return false;
//	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (isShowDelete && keyCode == KeyEvent.KEYCODE_BACK) {
////			isShowDelete = false;
////			editappAdapter.setIsShowDelete(isShowDelete);
////			editappAdapter.notifyDataSetChanged();
////			return true;
//		}else{
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				
				if(allApps!=null)
				{
					allApps.clear();
					allApps=null;
				}
				if(addAppInfos!=null)
				{
					addAppInfos.clear();
					addAppInfos=null;
				}
				  	   Intent itt=new Intent();
				  	   itt.setClass(HomeAddappActivity.this,MoreActivity.class);
				  	   
				  		startActivity(itt);
				  		finish();
		}
//	}
		return super.onKeyDown(keyCode, event);
	}
	
}
