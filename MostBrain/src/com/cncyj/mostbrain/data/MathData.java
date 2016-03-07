package com.cncyj.mostbrain.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cncyj.mostbrain.bean.MathBean;

public class MathData {
	
	public static List<MathBean> mList=new ArrayList<MathBean>();
	public static ArrayList<String> sybList=new ArrayList<String>();

	/**
	 * 10以内加减法
	 * 
	 * @throws 
	 * @throw
	 */
	public static void init10Datas() {
		MathBean bean=null;
		if(mList==null)
		{
			mList=new ArrayList<MathBean>();
		}
		sybList.add("+");
		sybList.add("-");
		for(int i=0;i<=10;i++){
			Random rd=new Random();
			int rdsyb=rd.nextInt(sybList.size());
			String syb=sybList.get(rdsyb);
			
		mList.add(new MathBean(rd.nextInt(10), rd.nextInt(10),syb, "=", null));
		}
//		sybList=null;
		bean=null;
	}
	
	/**
	 * 10以内加减法乘除
	 * 
	 * @throws 
	 * @throw
	 */
	public static void init10Data() {
		MathBean bean=null;
		if(mList==null)
		{
			mList=new ArrayList<MathBean>();
		}
		sybList.add("×");
		sybList.add("÷");
		for(int i=0;i<=10;i++){
			Random rd=new Random();
			int rdsyb=rd.nextInt(sybList.size());
			String syb=sybList.get(rdsyb);
			int add1=rd.nextInt(10);
			int add2=rd.nextInt(10);
			if(add1!=0 && add2!=0){
			if(syb.equals("÷") && add1%add2==0)
			{
		   mList.add(new MathBean(add1, add2,syb, "=", null));
			}else
			{
				   mList.add(new MathBean(add1, add2,"×", "=", null));
			}
			}else
			{
				 mList.add(new MathBean(add1, add2,"×", "=", null));
			}
		}
//		sybList=null;
		bean=null;
	}
	/**
	 * 100以内加减法
	 * 
	 * @throws 
	 * @throw
	 */
	public static void init100Datas() {
		MathBean bean=null;
		if(mList==null)
		{
			mList=new ArrayList<MathBean>();
		}
		sybList.add("+");
		sybList.add("-");
		for(int i=0;i<=10;i++){
			Random rd=new Random();
			int rdsyb=rd.nextInt(sybList.size());
			String syb=sybList.get(rdsyb);
		mList.add(new MathBean(rd.nextInt(100), rd.nextInt(100),syb, "=", null));
		}
//		sybList=null;
		bean=null;
	}
	
	/**
	 * 100以内加乘除
	 * 
	 * @throws 
	 * @throw
	 */
	public static void init100Data() {
		MathBean bean=null;
		if(mList==null)
		{
			mList=new ArrayList<MathBean>();
		}
		sybList.add("×");
		sybList.add("÷");
		for(int i=0;i<=10;i++){
			Random rd=new Random();
			int rdsyb=rd.nextInt(sybList.size());
			String syb=sybList.get(rdsyb);
			int add1=rd.nextInt(100);
			int add2=rd.nextInt(100);
			if(add1!=0 && add2!=0){
				if(syb.equals("÷") && add1%add2==0)
				{
			          mList.add(new MathBean(add1, add2,syb, "=", null));
				}else 
				{
					if(i!=0)
					{
						i--;
					}
				}
			}
		}
//		sybList=null;
		bean=null;
	}
	/**
	 * 100以内的除
	 * 
	 * @throws 
	 * @throw
	 */
	public static void init100Datac() {
		MathBean bean=null;
		if(mList==null)
		{
			mList=new ArrayList<MathBean>();
		}
		sybList.add("÷");
		for(int i=0;i<=10;i++){
			Random rd=new Random();
			int rdsyb=rd.nextInt(sybList.size());
			String syb=sybList.get(rdsyb);
			int add1=rd.nextInt(100);
			int add2=rd.nextInt(100);
			if(add1!=0&&add2!=0){
			if(add1%add2==0)
			{
		   mList.add(new MathBean(add1, add2,syb, "=", null));
			}else{
				if(i!=0)
				{
					i--;
				}
			}
			}
		}
		bean=null;
	}
	/**
	 * 10以内的除
	 * 
	 * @throws 
	 * @throw
	 */
	public static void init10Datac() {
		MathBean bean=null;
		if(mList==null)
		{
			mList=new ArrayList<MathBean>();
		}
		sybList.add("÷");
		for(int i=0;i<=5;i++){
			Random rd=new Random();
			int rdsyb=rd.nextInt(sybList.size());
			String syb=sybList.get(rdsyb);
			int add1=rd.nextInt(10);
			int add2=rd.nextInt(10);
			if(add1!=0&&add2!=0){
			if(add1%add2==0)
			{
		   mList.add(new MathBean(add1, add2,syb, "=", null));
			}else{
				if(i!=0){
				i--;
				}
			}
			
			}
		}
		bean=null;
	}
}
