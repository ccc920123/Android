package com.cncyj.mostbrain.game.shuduing.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用来存储读取正在玩的游戏
 * */
public class ContinueSharedPreferences {
	//静态上下文
	private Context context ;
	//Sharedperference
	private SharedPreferences shared ;
	private SharedPreferences.Editor editor ;
	//需要存储的信息
	private int stage  ;   //选择的关卡
	private ArrayList<String[]> numList ;			//玩家填入的数字 (TIPS:是从1开始的 到9结束)
	private String[] itemList ;					//子容器
	private static final String DEFAULT_NULL = "0" ;	//ArrayList中默认的空位子
	private static final String STAGE_KEY = "stage" ;		//stage存入sharedPerference中的key
	private static final String TIME = "time" ;				//存入暂停的时间
	private static final long DEFAULT_TIME = 0 ;
	
	
	public ContinueSharedPreferences(Context context){
		//得到传入信息
		this.context = context ;
		//得到SharedPerference，用以存储数据
		shared = this.context.getSharedPreferences("continueGame", Activity.MODE_PRIVATE) ;
		editor = shared.edit() ;
		
	}
	
	/**
	 * 将游戏信息存入sharedperference
	 * */
	public void saveStage(int stage, ArrayList<String[]> numList){
		//得到准备存储的数据
		this.stage = stage ;
		this.numList = numList ;
		
		//存入stage信息
		editor.putInt(STAGE_KEY, stage) ;
		editor.commit() ;
		
		//存入数独盘面信息
		for(int i=0;i<10;i++){
			itemList = numList.get(i);
			for(int j=0;j<10;j++){
				editor.putString(""+i+j, itemList[j]) ;
				editor.commit() ;
			}
		}
	}
	
	/**
	 * 将存储的游戏信息取出
	 * */
	public ArrayList<String[]> getList(){
		numList = new ArrayList<String[]>() ;
		for(int i=0;i<10;i++){
			itemList = new String[10];
			for(int j=0;j<10;j++){
				itemList[j] = shared.getString(""+i+j, DEFAULT_NULL) ;
			}
			numList.add(i, itemList) ;
		}
		return numList ;
	}
	
	/**
	 * 将存储的关卡信息取出
	 * */
	public int getStage(){
		return shared.getInt(STAGE_KEY, stage) ;
	}
	
	/**
	 * 存入退出时的时间
	 * */
	public void saveTime(Long time){
		editor.putLong(TIME, time) ;
		editor.commit() ;
	}
	
	/**
	 * 取出上次游戏的时间
	 * */
	public long getTime(){
		return shared.getLong(TIME, DEFAULT_TIME) ;
	}
}
