package com.cdjysdkj.diary.application;

import android.app.Activity;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cdjysdkj.diary.utils.ActivityManagerUtils;

public class MyApplication extends Application{

	public static String TAG;
	
	private static MyApplication myApplication = null;
	  public  static RequestQueue queues;
	
	public static MyApplication getInstance(){
		return myApplication;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TAG = this.getClass().getSimpleName();
		   queues= Volley.newRequestQueue(getApplicationContext());
		myApplication = this;
	}

	 public static RequestQueue getQueues(){
	        return  queues;
	    }
	
	public static MyApplication getAppContext() {
		return myApplication;
	}

	public void addActivity(Activity ac){
		ActivityManagerUtils.getInstance().addActivity(ac);
	}
	
	public void exit(){
		ActivityManagerUtils.getInstance().removeAllActivity();
	}
	
	public Activity getTopActivity(){
		return ActivityManagerUtils.getInstance().getTopActivity();
	}
	

}
