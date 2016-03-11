package com.cncyj.mostbrain.game.kuaifanying;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class Tool {
	public static int screenWidth = 0;
	public static int screenHeight = 0;

	public static int screenWidthC = 480;
	public static int screenHeightC = 800;
	public static String Tag = "2048";

	public static void Log(String info) {
		android.util.Log.i(Tag, info);
	}

	public static void setWH(int w, int h) {
		screenWidth = w;
		screenHeight = h;
		// Tool.Log("w=="+w+"  h==="+h);
	}

	/**
	 * 矩形碰撞
	 * 
	 * @param x1
	 * @param y1
	 * @param w1
	 * @param h1
	 * @param x2
	 * @param y2
	 * @param w2
	 * @param h2
	 * @return
	 */
	public static boolean isRectInRect(int x1, int y1, int w1, int h1, int x2,
			int y2, int w2, int h2) {
		if (isPointInRect(x1, y1, x2, y2, w2, h2))
			return true;
		if (isPointInRect(x1 + w1, y1, x2, y2, w2, h2))
			return true;
		if (isPointInRect(x1, y1 + h1, x2, y2, w2, h2))
			return true;
		if (isPointInRect(x1 + w1, y1 + h1, x2, y2, w2, h2))
			return true;

		if (isPointInRect(x2, y2, x1, y1, w1, h1))
			return true;
		if (isPointInRect(x2 + w2, y2, x1, y1, w1, h1))
			return true;
		if (isPointInRect(x2, y2 + h2, x1, y1, w1, h1))
			return true;
		if (isPointInRect(x2 + w2, y2 + h2, x1, y1, w1, h1))
			return true;

		return false;
	}

	/**
	 * @param x 点的x
	 * @param y 点的y
	 * @param x1
	 * @param y1
	 * @param w
	 * @param h
	 * @return
	 */
	public static boolean isPointInRect(int x, int y, int x1, int y1, int w,
			int h) {
		if (x < x1 || x > x1 + w)
			return false;
		if (y < y1 || y > y1 + h)
			return false;
		return true;
	}

	/**
	 * 矩形与圆碰撞
	 * 
	 * @param arcX
	 * @param arcY
	 * @param r
	 * @param rectx
	 * @param recty
	 * @param rectw
	 * @param recth
	 * @return
	 */
	public static boolean isRectInArc(int arcX, int arcY, int r, int rectx,
			int recty, int rectw, int recth) {
		if (isPointInArc(arcX, arcY, r, rectx, recty)) {
			return true;
		}
		if (isPointInArc(arcX, arcY, r, rectx + rectw, recty)) {
			return true;
		}
		if (isPointInArc(arcX, arcY, r, rectx, recty - recth)) {
			return true;
		}
		if (isPointInArc(arcX, arcY, r, rectx + rectw, recty - recth)) {
			return true;
		}
		return false;
	}

	/***
	 * 点与圆是否碰撞
	 * 
	 * @param arcX
	 * @param arcY
	 * @param r
	 * @param heroX
	 * @param heroY
	 * @return
	 */
	public static boolean isPointInArc(int arcX, int arcY, int r, int heroX,
			int heroY) {
		return (heroY - arcY) * (heroY - arcY) + (heroX - arcX)
				* (heroX - arcX) <= r * r;

	}
public static Bitmap getImageFromDrawable(int id)
{
	         BitmapDrawable bd = (BitmapDrawable) LiveActivity.app.getResources().getDrawable(id);
	         return bd.getBitmap();
		
}
//	/*
//	 * 从Assets中读取图片
//	 */
//	public static Bitmap getImageFromAssetsFile(String fileName) {
//		Tool.Log("getImageFromAssetsFile="+fileName);
//		Bitmap image = null;
//		AssetManager am = LiveActivity.app.getResources().getAssets();
//		try {
//			InputStream is = am.open(fileName);
//			image = BitmapFactory.decodeStream(is);
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return image;
//
//	}
/**
 * 
* @Description 
* 
* @param  @param bitmap
* @param  @param color
* @param  @return
* @return
* @throws
* @throws
*
 */
	public static final Bitmap createRGBImage(Bitmap bitmap, int color) {
		int bitmap_w = bitmap.getWidth();
		int bitmap_h = bitmap.getHeight();
		int[] arrayColor = new int[bitmap_w * bitmap_h];
		int count = 0;
		for (int i = 0; i < bitmap_h; i++) {
			for (int j = 0; j < bitmap_w; j++) {
				int color1 = bitmap.getPixel(j, i);

				if (color1 == 0xffffffff) {
					color1=color;
				} else {

				}
				arrayColor[count] = color1;
				count++;
			}
		}
		bitmap = Bitmap.createBitmap(arrayColor, bitmap_w, bitmap_h,
				Config.ARGB_4444);
		return bitmap;
	}
}
