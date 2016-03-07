package com.cncyj.mostbrain.game.zuiqiangdanao;

import android.content.Context;
import android.content.SharedPreferences;

public class Gamevoid {
	//����״̬
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	Context context;
	public Gamevoid(String name,Context context) {

		this.context = context;
		this.preferences = context.getSharedPreferences(name, 0);
		this.editor = preferences.edit();
	}	
	//��������״̬
	public void setSaveMusic(String name,boolean bool){
		editor = preferences.edit();
		editor.putBoolean(name, bool);
		editor.commit();		
	}
	//��ȡ����״̬
	public boolean getSaveMusic(String name){
		return preferences.getBoolean(name, true);
	}
}
