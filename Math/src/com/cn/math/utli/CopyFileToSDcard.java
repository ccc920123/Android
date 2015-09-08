package com.cn.math.utli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;

public class CopyFileToSDcard {

	
	/**
	 * 将assets文件复制到sdcard
	 * @param context
	 * @param sdcardPath
	 * @throws 
	 * @throw
	 */
	public static void copyFileToSdCard(Context context,String sdcardPath){
//	Resources rou=context.getResources();
//    InputStream is =rou.openRawResource(id)(R.你的db);
		try {
			
			File mFile = new File(sdcardPath);
//			if (!mFile.exists()) {创建文件
//				File file2 = new File(mFile.getParent());
//				file2.mkdir();
//			}
			if (!mFile.exists()) {//创建文件夹
				mFile.getParentFile().mkdirs();
				mFile.createNewFile();
			}
		 InputStream is= context.getAssets().open("math.jpg");
		
    FileOutputStream fos =new FileOutputStream(sdcardPath);
    byte[] buffer = new byte[1024];
    int count =0;
    // 开始复制db文件
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
