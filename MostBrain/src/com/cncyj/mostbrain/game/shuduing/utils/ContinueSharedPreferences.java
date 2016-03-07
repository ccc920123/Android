package com.cncyj.mostbrain.game.shuduing.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * �����洢��ȡ���������Ϸ
 * */
public class ContinueSharedPreferences {
	//��̬������
	private Context context ;
	//Sharedperference
	private SharedPreferences shared ;
	private SharedPreferences.Editor editor ;
	//��Ҫ�洢����Ϣ
	private int stage  ;   //ѡ��Ĺؿ�
	private ArrayList<String[]> numList ;			//������������ (TIPS:�Ǵ�1��ʼ�� ��9����)
	private String[] itemList ;					//������
	private static final String DEFAULT_NULL = "0" ;	//ArrayList��Ĭ�ϵĿ�λ��
	private static final String STAGE_KEY = "stage" ;		//stage����sharedPerference�е�key
	private static final String TIME = "time" ;				//������ͣ��ʱ��
	private static final long DEFAULT_TIME = 0 ;
	
	
	public ContinueSharedPreferences(Context context){
		//�õ�������Ϣ
		this.context = context ;
		//�õ�SharedPerference�����Դ洢����
		shared = this.context.getSharedPreferences("continueGame", Activity.MODE_PRIVATE) ;
		editor = shared.edit() ;
		
	}
	
	/**
	 * ����Ϸ��Ϣ����sharedperference
	 * */
	public void saveStage(int stage, ArrayList<String[]> numList){
		//�õ�׼���洢������
		this.stage = stage ;
		this.numList = numList ;
		
		//����stage��Ϣ
		editor.putInt(STAGE_KEY, stage) ;
		editor.commit() ;
		
		//��������������Ϣ
		for(int i=0;i<10;i++){
			itemList = numList.get(i);
			for(int j=0;j<10;j++){
				editor.putString(""+i+j, itemList[j]) ;
				editor.commit() ;
			}
		}
	}
	
	/**
	 * ���洢����Ϸ��Ϣȡ��
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
	 * ���洢�Ĺؿ���Ϣȡ��
	 * */
	public int getStage(){
		return shared.getInt(STAGE_KEY, stage) ;
	}
	
	/**
	 * �����˳�ʱ��ʱ��
	 * */
	public void saveTime(Long time){
		editor.putLong(TIME, time) ;
		editor.commit() ;
	}
	
	/**
	 * ȡ���ϴ���Ϸ��ʱ��
	 * */
	public long getTime(){
		return shared.getLong(TIME, DEFAULT_TIME) ;
	}
}
