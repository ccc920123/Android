package com.cn.math;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.cn.math.adpter.CommonAdapter;
import com.cn.math.adpter.MathAdpter;
import com.cn.math.bean.MathBean;
import com.cn.math.data.MathData;
import com.cn.math.dialog.AlerDialog;
import com.cn.math.dialog.WritePadDialog;
import com.cn.math.lib.DragLayout;
import com.cn.math.lib.DragLayout.DragListener;
import com.cn.math.utli.DisplayMetricsEN;
/**
 * 265edbf9891bb3272f6841c44c5e4703
 * db99f30e
 *MainActivity.java
 * @author CXY
 * @Description
 * @Create Time 2015-8-21
 */
public class MainActivity extends Activity {
	
	private WindowManager wm=null;
	private WindowManager.LayoutParams wmParams=null;
    private Button leftbtn;
    WritePadDialog dialog;
	
	private MediaPlayer mediaPlayer;//������
	private static final float BEEP_VOLUME = 0.10f;
	private static final long VIBRATE_DURATION = 200L;
	private ListView mListView;
	private CommonAdapter<MathBean> mAdapter;
	private Button mButton;
	List zq=new ArrayList<Integer>();
	List cw=new ArrayList<Integer>();
	
	private DragLayout mDragLayout;
	/** ��߲໬�˵� */
	private ListView menuListView;// �˵��б�
	private ImageButton Seting, menu_left;
	private SharedPreferences sp;
	EditText edt;
	private String url="file:///android_asset/ico.png";
	public static final String PHONE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + File.separator+"Math/math.jpg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
//		ProgressDialogUtil.showProgressDialog(this, "���ڼ������Ժ�...");
		//////////
		
		/////
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		findViewById();
		sp=this.getSharedPreferences("MATH", MODE_PRIVATE);
		initView();
		sideslip();
		mButton.setOnClickListener(Click);
		Seting.setOnClickListener(Click);
		initBeepSound();
	}
	private void initView() {
		checkdata();
		MathAdpter adapter=new MathAdpter(MainActivity.this,MathData.mList);
		mListView.setAdapter(adapter);
//		mListView.setAdapter(mAdapter=new CommonAdapter<MathBean>(MainActivity.this,
//				MathData.mList,R.layout.list_item) {
//			
//			@Override
//			public void convert(final ViewHolder helper, final MathBean item) {
//				helper.setText(R.id.addend1, String.valueOf(item.getAddend1()));
//				helper.setText(R.id.symbol, item.getSymbol());
//				helper.setText(R.id.addend2, String.valueOf(item.getAddend2()));
//				helper.setText(R.id.textView4, "=");
//				helper.setText(R.id.setOther, "");
//				edt=(EditText)helper.getView(R.id.setOther);
//				edt.addTextChangedListener(new TextWatcher() {
//					
//					@Override
//					public void onTextChanged(CharSequence s, int start, int before, int count) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void beforeTextChanged(CharSequence s, int start, int count,
//							int after) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void afterTextChanged(Editable s) {
//						  //��editText�иı��ֵ���õ�HashMap��  
//	                
//
//						
//					}
//				});
//			}
//		});
		
		
	}
	/**
	 * �����ݸ�ֽ
	 * 
	 * @throws 
	 * @throw
	 */
	private void initFloatView(){
	    //��ȡWindowManager
	    wm=(WindowManager)getApplicationContext().getSystemService("window");
	    //����LayoutParams(ȫ�ֱ�������ز���
	     wmParams = new WindowManager.LayoutParams();
	         
	    wmParams.type=LayoutParams.TYPE_PHONE;   //����window type
	    wmParams.format=PixelFormat.RGBA_8888;   //����ͼƬ��ʽ��Ч��Ϊ����͸��
	     //����Window flag
	    wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL 
	                     | LayoutParams.FLAG_NOT_FOCUSABLE;
	 
	    //����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
	     wmParams.x=0;
	    wmParams.y=0;
	    //�����������ڳ�������
	     wmParams.width=50;
	    wmParams.height=50;
	    createLeftFloatView();
	}
	 /**
	    * �������������ť
	    */
	    private void createLeftFloatView(){
	        leftbtn=new Button(this);
	        leftbtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_cg));
	        leftbtn.setText("ֽ");
	        leftbtn.setTextSize(10);
	        leftbtn.setGravity(Gravity.RIGHT);
	        leftbtn.setTextColor(Color.WHITE);
	        dialog = new WritePadDialog(MainActivity.this);
	        leftbtn.setOnClickListener(new View.OnClickListener() { 
	        public void onClick(View arg0) {
	        	
	        	if(dialog.isShowing()){
	        		return;
	        	
	        	}else{
	        		dialog.setCancelable(false);
		        	dialog.show();
	        	}
	        }

			
	    });
	      
	        //������������
	        wmParams.gravity=Gravity.LEFT|Gravity.CENTER_VERTICAL;
	        //��ʾmyFloatViewͼ��
	        wm.addView(leftbtn, wmParams);
	    }

	private void checkdata()
	{
		String check=sp.getString("MATHCHECK", "1000");
		if(check.equals("1"))
		{
			MathData.init10Datas();
		}else if(check.equals("2"))
		{
			MathData.init10Datac();
		}else if(check.equals("3"))
		{
			MathData.init100Data();
		}else if(check.equals("4"))
		{
			MathData.init100Datac();
		}else{
			MathData.init10Datas();
		}
	}
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
	private void findViewById() {
		mListView=(ListView) findViewById(R.id.math);
		mButton=(Button) findViewById(R.id.sbmit);
		Seting = (ImageButton) findViewById(R.id.menu_imgbtn);
		menu_left = (ImageButton) findViewById(R.id.menu_left);
	}
   private OnClickListener Click=new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sbmit:
			
			int mlistNum=MathData.mList.size();
			lastData(mlistNum);
			int zqNum=zq.size();
			if(mlistNum==zqNum)
			{
				playSoundstart();
				AlerDialog.showAlerDialog(MainActivity.this,
						"���������ȫ��ȷ", "����˼,����", new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if(mediaPlayer!=null)
								{
								mediaPlayer.stop();
								}
								zq=null;
								cw=null;
								zq=new ArrayList<Integer>();
								cw=new ArrayList<Integer>();
								MathData.mList=null;
								MathData.mList=new ArrayList<MathBean>();
								//TODO
								initView();
								AlerDialog.closeDialog();
							}
						}, "��û��,�˳�",new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								
								if(mediaPlayer!=null)
								{
								mediaPlayer.stop();
								}
								MathData.mList=null;
								Intent itexit=new Intent();
								itexit.setClass(MainActivity.this, MoreActivity.class);
								startActivity(itexit);
								finish();
							}},true);
				
				
				
			}else{
//				zqNum;��ȷ
				AlerDialog.showAlerDialog(MainActivity.this,
						"��ȷ��["+zqNum+"]������Ŭ��", "����˼,����", new OnClickListener() {
						
							@Override
							public void onClick(View v) {
								zq=null;
								cw=null;
								zq=new ArrayList<Integer>();  
								cw=new ArrayList<Integer>();  
								MathData.mList=null;
								MathData.mList=new ArrayList<MathBean>();
								initView();
								
								AlerDialog.closeDialog();
							}
						}, "��û��,�˳�",new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Intent itexit=new Intent();
								itexit.setClass(MainActivity.this, MoreActivity.class);
								startActivity(itexit);
								finish();
								
							}},false);
			}
			break;
		case R.id.menu_imgbtn:
			mDragLayout.open();

			break;
		}
		

		
	}
//�õ�listView�е�����
	private void lastData(int mlistNum) {
		int count=mListView.getChildCount();
		for(int i=0;i<count;i++){
		View view=mListView.getChildAt(i);
		TextView addend1=(TextView) view.findViewById(R.id.addend1);
		TextView symbol=(TextView) view.findViewById(R.id.symbol);
		TextView addend2=(TextView) view.findViewById(R.id.addend2);
		EditText edit=(EditText) view.findViewById(R.id.setOther);
		String str=edit.getText().toString();
		
		int add1=Integer.parseInt(addend1.getText().toString());
		int add2=Integer.parseInt(addend2.getText().toString());
		String sym=symbol.getText().toString();
		if(!str.equals("")){
		int other=Integer.parseInt(str);
		
		if(sym.equals("��"))
		{
			if(add1*add2==other)
			{
				zq.add(other);
			}else{
				cw.add(other);
			}
		}else if(sym.equals("��"))
		{
			
			if(add1/add2==other)
			{
				zq.add(other);
			}else{
				cw.add(other);
			}
		}else if(sym.equals("+")){
			if(add1+add2==other)
			{
				zq.add(other);
			}else{
				cw.add(other);
			}
		}else{
			if(add1-add2==other)
			{
				zq.add(other);
			}else{
				cw.add(other);
			}
		  }
		}
	}
	}
};
private void playSoundstart() {
	if (mediaPlayer != null) {
		mediaPlayer.start();
	}
}
/**
 * ��ʼ������
 */
private void initBeepSound() {
	if (mediaPlayer == null) {
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnCompletionListener(beepListener);
		AssetFileDescriptor file = getResources().openRawResourceFd(
				R.raw.blow);
		try {
			mediaPlayer.setDataSource(file.getFileDescriptor(),
					file.getStartOffset(), file.getLength());
			file.close();
			mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
			mediaPlayer.setLooping(true);
			mediaPlayer.prepare();
		} catch (IOException e) {
			mediaPlayer = null;
		}
	}
}
private final OnCompletionListener beepListener = new OnCompletionListener() {
	public void onCompletion(MediaPlayer mediaPlayer) {
		mediaPlayer.seekTo(0);
	}
};
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
				Intent itt=new Intent();
				itt.setClass(MainActivity.this,CheckTopicActivity.class);
				startActivity(itt);
				finish();
				break;
			case 1:
				Intent itts=new Intent();
				itts.setClass(MainActivity.this, MantraSheetActivity.class);
				startActivity(itts);
				finish();
				break;
			case 2:
				
				showShare();
				
//				 Intent intent=new Intent(Intent.ACTION_SEND);
//				  intent.setType("image/*");   
//			      intent.putExtra(Intent.EXTRA_SUBJECT, "������ѧ");
//			      intent.putExtra(Intent.EXTRA_TEXT, "����ı����������������ϣ�ѧϰ�ĺ����֣���������ѧ����");
//			      startActivity(Intent.createChooser(intent, getTitle()));
				break;
			case 3:
				Intent itmore=new Intent();
				itmore.setClass(MainActivity.this, MoreActivity.class);
				startActivity(itmore);
				finish();
				
				break;
			default:
				break;
			}
		}
	});
}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode==KeyEvent.KEYCODE_BACK)
	{
		
						if(mediaPlayer!=null)
						{
						mediaPlayer.stop();
						}
						MathData.mList=null;
						
						
						Intent ittmain=new Intent();
						ittmain.setClass(MainActivity.this, MoreActivity.class);
						startActivity(ittmain);
						finish();
		}
	
	return false;
}
@Override
protected void onDestroy() {
	super.onDestroy();
	
	MathData.mList=null;
}
@Override
protected void onResume() {
	initFloatView();
	super.onResume();
}
@Override
protected void onPause() {
	wm.removeView(leftbtn);
	super.onPause();
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
//	// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
//	oks.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cn.math");
	// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
	oks.setText("����ûѧϰû��Ȥ��������ѧ�ñ���ѧϰ����Ȥ���ñ����������������ϣ�ѧϰ�ĺ����֣���������ѧ��");
	// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
	//
	oks.setImagePath(PHONE_PATH);// ȷ��SDcard������ڴ���ͼƬ
	// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
	oks.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cn.math");
	// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
//	oks.setComment("����ûѧϰû��Ȥ��������ѧ�ñ���ѧϰ����Ȥ���ñ����������������ϣ�ѧϰ�ĺ����֣���������ѧ��");
	// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
//	oks.setSite(getString(R.string.app_name));
	// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
//	oks.setSiteUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cn.math");

	// ��������GUI
	oks.show(this);
}


}
