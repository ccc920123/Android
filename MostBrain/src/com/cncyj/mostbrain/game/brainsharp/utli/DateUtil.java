package com.cncyj.mostbrain.game.brainsharp.utli;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static boolean isTime(){
		boolean flag;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		String date=df.format(new Date());
		if(date.equals("2014-08-24")||date.equals("2014-08-25")||date.equals("2014-08-26")||date.equals("2014-08-27")||date.equals("2014-08-28")||date.equals("2014-08-29")){
			flag=false;
		}else{
			flag=true;
		}
		return flag;
	}
}
