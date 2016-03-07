package com.cncyj.mostbrain.game.zuiqiangdanao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cncyj.mostbrain.MoreActivity;
import com.cncyj.mostbrain.R;
import com.cncyj.mostbrain.dialog.AlerDialog;

public class FateGameActivity extends Activity implements OnClickListener{
    protected static SharedPreferences shard;
	/** Called when the activity is first created. */
	private RelativeLayout relativelayout;
	private RelativeLayout.LayoutParams RLcard;
	private ArrayList<Crad> crads = new ArrayList<Crad>();
	private ArrayList<Point> points = new ArrayList<Point>();
	private Crad crad;
	private int index = 1;
	//n关数
	private int m = 0,n = 1;
	private TextView timeView;
	private TextView cusView;
	private Button back;
	private int stop = 1;
	//定义开始时间
	Intent intent;
	private int j=11;
	private int k=5;
	//sound的引用
	private SoundPool sp;
	private HashMap<Integer, Integer> hm;
	int currStreanId;
	private SharedPreferences shared ;
	private SharedPreferences.Editor editor ;
	private boolean isrester=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSoundpool();//初始化声音池
        relativelayout = new RelativeLayout(this);
        relativelayout.setBackgroundResource(R.drawable.saoleibeuijing);     
        setContentView(relativelayout); 
        FateGameActivity.shard = getSharedPreferences("ZQDNGame", Activity.MODE_PRIVATE);
     
						int i = shard.getInt("stage", -1);
						int j=shard.getInt("leve", 1);
						if(i == -1){
							m=5;
							n=1;
							
						}else{
						m=i;
						n=j;
						}
						statgame();

        
    }
    //开始游戏
    public void statgame(){
    	statindex();
    	pointcrad();
    	texttime();
	    everytime();
    	statshow();
    	addcard(m);	
    }
    //显示时间
    public void texttime(){
    	timeView = new TextView(this);
    	cusView = new TextView(this);
    	back = new Button(this);
    	relativelayout.addView(back);
    	relativelayout.addView(timeView);
    	relativelayout.addView(cusView);
    	RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) timeView.getLayoutParams();
    	RelativeLayout.LayoutParams ba = (android.widget.RelativeLayout.LayoutParams) back.getLayoutParams();
    	RelativeLayout.LayoutParams lp1 = (android.widget.RelativeLayout.LayoutParams) cusView.getLayoutParams();
    	lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    	lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    	ba.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    	ba.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    	lp.topMargin =(int) this.getResources().getDimension(R.dimen.activity);
    	cusView.setTextSize(15);
		cusView.setTextColor(Color.GREEN);
		cusView.setText("第"+(n)+"关");
    	timeView.setLayoutParams(lp);
    	cusView.setLayoutParams(lp1);
    	back.setBackgroundResource(R.drawable.ic_action_refresh);
    	back.setHeight(LayoutParams.WRAP_CONTENT);
    	back.setWidth(LayoutParams.WRAP_CONTENT);
    	back.setLayoutParams(ba);
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				timer.cancel();	
				  AlerDialog.exitDialog(FateGameActivity.this,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									isrester=false;
									Intent itt = new Intent(FateGameActivity.this,
											FateGameActivity.class);
									startActivity(itt);
									AlerDialog.closeDialog();
									finish();
									AlerDialog.closeDialog();

								}
							}, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									stop = 0;
									everytime();
									AlerDialog.closeDialog();
								}
							},"你是否重置游戏？",false);

				
				
				
				
				
			}
		});
    }
    //定义卡片开始位置
    public void statindex(){   	
    	index = 1;
    }	
    /**把所有的卡片放到集合中
     */
    public void pointcrad(){
    	for(int i = 0;i < 5;i++){
    		for (int j = 0; j < 8; j++) {
				points.add(new Point(i*82,j*94));
			}
    	}
    } 
    /**
     * 添加随机生成卡片
     */
    public void addcard(int m){
    	Point p;
    	for (int i = 1; i < m; i++) {
			crad = new Crad(this,i);
			//产生随机点
			p = points.remove((int)(Math.random()*points.size()));
			//在随机中取得卡片
			RLcard = new RelativeLayout.LayoutParams(76,90);
			RLcard.leftMargin = p.x;
			RLcard.topMargin = p.y;
			relativelayout.addView(crad, RLcard);
			playsound(1, 0);
			crad.setOnClickListener(this);
			crads.add(crad);
		}
    }
    /**
     * 点击事件  
     */
    @Override
	public void onClick(View v) {
    	if(k == 0 ){
    		//判断这个当前的v是不是当前的Card的实例
    		if(v instanceof Crad){
    			crad = (Crad) v;
    			for (Crad ca : crads) {
    				//判断当前图片的状态
    				if(ca.isAvisiable()){
    					ca.showB();
    				}
    			}
    			/**
    			 * 得到当前卡片的数字
    			 */
    			if(crad.getNum() == index){
    				playsound(1, 0);
    				//移除布局当中
    				relativelayout.removeView(crad);
    				crads.remove(crad);
    				index++;
    				if(index == m && j > 0){
    					if(n < 20){
    						
    						AlerDialog.exitDialog(FateGameActivity.this,
    								new DialogInterface.OnClickListener() {

    									@Override
    									public void onClick(DialogInterface dialog, int which) {
    										AlerDialog.closeDialog();
    										relativelayout.removeAllViews();
    										timer.cancel();
    										task.cancel();
    										timer2.cancel();
    										task2.cancel();
    										j = 11+(n-1)*5;
    										k = 5 +n;
    										crads.clear();
    										points.clear();
    										statgame();
    										

    									}
    								}, new DialogInterface.OnClickListener() {

    									@Override
    									public void onClick(DialogInterface dialog, int which) {
    										AlerDialog.closeDialog();
    										relativelayout.removeAllViews();
    										timer.cancel();
    										task.cancel();
    										timer2.cancel();
    										task2.cancel();
    										//继续下一关
    										crads.clear();
    										points.clear();
    										m = m+2;
    										j = 11+n*5;
    										n = n+1;
    										k = 5+n;
    										index = 1;
    										statgame();
    										
    									}
    								},new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Intent itt = new Intent(FateGameActivity.this,
    												MoreActivity.class);
    										startActivity(itt);
    										AlerDialog.closeDialog();
    										finish();
											
										}
									}, "成功完成了本关卡","重玩","下一关","返回",false);	
    					}else{  
    						
    						AlerDialog.exitDialog(FateGameActivity.this,
    								new DialogInterface.OnClickListener() {

    									@Override
    									public void onClick(DialogInterface dialog, int which) {
    										AlerDialog.closeDialog();
    										relativelayout.removeAllViews();
    										timer.cancel();
    										task.cancel();
    										timer2.cancel();
    										task2.cancel();
    										j = 11;
    										index = 1;
    										m = 5;
    										n = 1;
    										k = 5+2;
    										crads.clear();
    										points.clear();
    										statgame();
    										

    									}
    								}, new DialogInterface.OnClickListener() {

    									@Override
    									public void onClick(DialogInterface dialog, int which) {
    										
    										Intent itt = new Intent(FateGameActivity.this,
    												MoreActivity.class);
    										startActivity(itt);
    										AlerDialog.closeDialog();
    										finish();
    										
    									}
    								}, "恭喜你挑战成功!是否重新开始",false);		   		
    					}
    				}
    			}else{
    				playsound(2, 0);
    				crad.setAnimationCacheEnabled(true);
    				crad.startAnimation(AnimationUtils.loadAnimation(FateGameActivity.this, R.anim.shake));
    			}	
    		}
    	}
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		timer.cancel();
    		AlerDialog.exitDialog(FateGameActivity.this,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							Intent itt = new Intent(FateGameActivity.this,
									MoreActivity.class);
							startActivity(itt);
							AlerDialog.closeDialog();
							finish();

						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							stop = 0;
							everytime();
							AlerDialog.closeDialog();
						}
					}, "你确定要返回吗？",false);		   		
    	}
    	return super.onKeyDown(keyCode, event);
    }
   
	
	//设置时间
	Timer timer;
	TimerTask task;

	// 闯关时间 time方法
	public void everytime() {
		
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					j--;
					Message msg = new Message();
					Bundle b = new Bundle();
					b.putInt("time", j);
					msg.setData(b);
					handler.sendMessage(msg);
					System.out.println(j);
			}
		};
			
		// 游戏时间显示间隔
		if(stop == 0){
			timer.schedule(task, 1000, 1000);
			stop = 1;
		}else{	
			timer.schedule(task, 5000+n*1000, 1000);
		}
		super.onResume();

	}
	//添加时间 和关卡
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			timeView.setTextColor(Color.RED);
			timeView.setTextSize(14);
			timeView.setText("还有"+ msg.getData().getInt("time") + "秒");
			if(index != m){				
				if(j < 1 ){	
					//清空时间
					timer.cancel();
					task.cancel();
					timer2.cancel();
					task2.cancel();
					
					AlerDialog.exitDialog(FateGameActivity.this,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									AlerDialog.closeDialog();
									relativelayout.removeAllViews();
									timer.cancel();
									task.cancel();
									timer2.cancel();
									task2.cancel();
									j = 11+(n-1)*5;
									k = 5 +n;
									crads.clear();
									points.clear();
									statgame();
								

								}
							}, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									
									Intent itt = new Intent(FateGameActivity.this,
											MoreActivity.class);
									startActivity(itt);
									AlerDialog.closeDialog();
									finish();
									
								}
							}, "挑战失败，是否再来挑战",false);		   		
					
					
				}
			}else{
				//清空时间
				timer.cancel();
				task.cancel();
			}
		};
	};

	/**
	 * 规定时间内自动翻转卡片
	 */
		Timer timer2;
		TimerTask task2;	
	   public void statshow(){
				timer2 = new Timer();			
				task2 = new TimerTask() {					
					@Override
					public void run() {
						k--;
						Message msg = new Message();
						msg.what = k;
						handler2.sendMessage(msg);
					}						    
		    };
			timer2.schedule(task2, 1000,1000);
			super.onResume();
		}
	   Handler handler2 = new Handler(){
		   public void handleMessage(android.os.Message msg){
			  
			   if(msg.what == 0){
				   for (Crad ca : crads) {
		    			//判断当前图片的状态
						if(ca.isAvisiable()){
							playsound(1, 0);
							ca.showB();
					}
			    }
				   timer2.cancel();
				   task2.cancel();
		   }
	   };
	 };
	 //初始化声音池
	 public void initSoundpool(){
		 sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		//声音集合
		 hm = new HashMap<Integer, Integer>();
		 hm.put(1, sp.load(FateGameActivity.this, R.raw.starts, 1));
		 hm.put(2, sp.load(FateGameActivity.this, R.raw.errer, 2));
	 }
	 //获取AudioManager引用
	 public void playsound(int sound,int loop){
		 AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);//当前声音
		 float streavolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);//系统最大音量
		 float streaMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//计算机得到播放音量
		 float volume = streavolume/streaMax;
		 currStreanId = sp.play(hm.get(sound), volume/5, volume/5  , 1, loop, 1.0f);
	 }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Editor editor = shard.edit();
		if(isrester){
		editor.putInt("stage", m);
		editor.putInt("leve", n);
		}else{
			editor.putInt("stage", 5);
			editor.putInt("leve", 1);
			
		}
		editor.commit();
		
	}
	 
}