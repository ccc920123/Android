package com.cn.math.utli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;

public class CopyFileToSDcard {

	
	/**
	 * ��assets�ļ����Ƶ�sdcard
	 * @param context
	 * @param sdcardPath
	 * @throws 
	 * @throw
	 */
	public static void copyFileToSdCard(Context context,String sdcardPath){
//	Resources rou=context.getResources();
//    InputStream is =rou.openRawResource(id)(R.���db);
		try {
			
			File mFile = new File(sdcardPath);
//			if (!mFile.exists()) {�����ļ�
//				File file2 = new File(mFile.getParent());
//				file2.mkdir();
//			}
			if (!mFile.exists()) {//�����ļ���
				mFile.getParentFile().mkdirs();
				mFile.createNewFile();
			}
		 InputStream is= context.getAssets().open("math.jpg");
		
    FileOutputStream fos =new FileOutputStream(sdcardPath);
    byte[] buffer = new byte[1024];
    int count =0;
    // ��ʼ����db�ļ�
    while ((count=is.read(buffer))>0){
            fos.write(buffer, 0, count); 
            }         
       fos.close();
       is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
