package com.cncyj.mostbrain.game.brainsharp.utli;

import com.cncyj.mostbrain.R;
import com.cncyj.mostbrain.game.brainsharp.modle.AppInfo;



public class CommonUtil {
	private static final String appId1 = "10001";
	private static final String appId2 = "10002";
	private static final String appId3 = "10003";
	private static final String appId4 = "10004";
	private static final String appId5 = "10005";
	private static final String appId6 = "10006";
	private static final String appId7 = "10007";
	private static final String appId8 = "10008";
	private static final String appId9 = "10009";
	public static final String appId10 = "10010";

	//��ҳӦ�����������
	public static String[] MainHomeApp = {appId1, appId2, appId3, appId4, appId5, appId10};
	//Ӧ�����ĵ�Ӧ��������
	public static String[] MainAppApp = {appId1, appId2, appId3, appId4, appId5, appId6, appId7, appId8, appId9};
	
	public static AppInfo getApp(String id) {
		if(id.equals(appId1)) {
			return new AppInfo(appId1,"��Ц����",R.drawable.app_cir_01,"1");
		}else if(id.equals(appId2)) {
			return new AppInfo(appId2,"��Ĭ����",R.drawable.app_cir_02,"2");
		}else if(id.equals(appId3)) {
			return new AppInfo(appId3,"Ȥζ����",R.drawable.app_cir_03,"3");
		}else if(id.equals(appId4)) {
			return new AppInfo(appId4,"��������",R.drawable.app_cir_04,"4");
		}else if(id.equals(appId5)) {
			return new AppInfo(appId5,"��������",R.drawable.app_cir_05,"5");
		}else if(id.equals(appId6)) {
			return new AppInfo(appId6,"��������",R.drawable.app_cir_06,"6");
		}else if(id.equals(appId7)) {
			return new AppInfo(appId7,"�ۺ�����",R.drawable.app_cir_07,"7");
		}else if(id.equals(appId8)) {
			return new AppInfo(appId8,"��ɫ����",R.drawable.app_cir_08,"8");
		}else if(id.equals(appId9)) {
			return new AppInfo(appId9,"��Ȥ����",R.drawable.app_cir_09,"9");
		}else if(id.equals(appId10)) {
			return new AppInfo(appId10,"����ͷ����",R.drawable.app_icon_add,"10");
		}
		return null;
	}

}
