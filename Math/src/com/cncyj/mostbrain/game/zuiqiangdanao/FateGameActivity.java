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
	//n����
	private int m = 0,n = 1;
	private TextView timeView;
	private TextView cusView;
	private Button back;
	private int stop = 1;
	//���忪ʼʱ��
	Intent intent;
	private int j=11;
	private int k=5;
	//sound������
	private SoundPool sp;
	private HashMap<Integer, Integer> hm;
	int currStreanId;
	private SharedPreferences shared ;
	private SharedPreferences.Editor editor ;
	private boolean isrester=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSoundpool();//��ʼ��������
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
    //��ʼ��Ϸ
    public void statgame(){
    	statindex();
    	pointcrad();
    	texttime();
	    everytime();
    	statshow();
    	addcard(m);	
    }
    //��ʾʱ��
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
		cusView.setText("��"+(n)+"��");
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
							},"���Ƿ�������Ϸ��",false);

				
				
				
				
				
			}
		});
    }
    //���忨Ƭ��ʼλ��
    public void statindex(){   	
    	index = 1;
    }	
    /**�����еĿ�Ƭ�ŵ�������
     */
    public void pointcrad(){
    	for(int i = 0;i < 5;i++){
    		for (int j = 0; j < 8; j++) {
				points.add(new Point(i*82,j*94));
			}
    	}
    } 
    /**
     * ���������ɿ�Ƭ
     */
    public void addcard(int m){
    	Point p;
    	for (int i = 1; i < m; i++) {
			crad = new Crad(this,i);
			//���������
			p = points.remove((int)(Math.random()*points.size()));
			//�������ȡ�ÿ�Ƭ
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
     * ����¼�  
     */
    @Override
	public void onClick(View v) {
    	if(k == 0 ){
    		//�ж������ǰ��v�ǲ��ǵ�ǰ��Card��ʵ��
    		if(v instanceof Crad){
    			crad = (Crad) v;
    			for (Crad ca : crads) {
    				//�жϵ�ǰͼƬ��״̬
    				if(ca.isAvisiable()){
    					ca.showB();
    				}
    			}
    			/**
    			 * �õ���ǰ��Ƭ������
    			 */
    			if(crad.getNum() == index){
    				playsound(1, 0);
    				//�Ƴ����ֵ���
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
    										//������һ��
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
									}, "�ɹ�����˱��ؿ�","����","��һ��","����",false);	
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
    								}, "��ϲ����ս�ɹ�!�Ƿ����¿�ʼ",false);		   		
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
					}, "��ȷ��Ҫ������",false);		   		
    	}
    	return super.onKeyDown(keyCode, event);
    }
   
	
	//����ʱ��
	Timer timer;
	TimerTask task;

	// ����ʱ�� time����
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
			
		// ��Ϸʱ����ʾ���
		if(stop == 0){
			timer.schedule(task, 1000, 1000);
			stop = 1;
		}else{	
			timer.schedule(task, 5000+n*1000, 1000);
		}
		super.onResume();

	}
	//���ʱ�� �͹ؿ�
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			timeView.setTextColor(Color.RED);
			timeView.setTextSize(14);
			timeView.setText("����"+ msg.getData().getInt("time") + "��");
			if(index != m){				
				if(j < 1 ){	
					//���ʱ��
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
							}, "��սʧ�ܣ��Ƿ�������ս",false);		   		
					
					
				}
			}else{
				//���ʱ��
				timer.cancel();
				task.cancel();
			}
		};
	};

	/**
	 * �涨ʱ�����Զ���ת��Ƭ
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
		    			//�жϵ�ǰͼƬ��״̬
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
	 //��ʼ��������
	 public void initSoundpool(){
		 sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		//��������
		 hm = new HashMap<Integer, Integer>();
		 hm.put(1, sp.load(FateGameActivity.this, R.raw.starts, 1));
		 hm.put(2, sp.load(FateGameActivity.this, R.raw.errer, 2));
	 }
	 //��ȡAudioManager����
	 public void playsound(int sound,int loop){
		 AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);//��ǰ����
		 float streavolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);//ϵͳ�������
		 float streaMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//������õ���������
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