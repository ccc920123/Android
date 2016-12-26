package com.cdjysdkj.diary.view.imagefilterndk;

public class ImageFilterNdk {

	/**
	 */
	public native static int[] whitening(int[] buff,int width,int height);
	/**
	 */
	public native static int[] ice(int[] buff,int width,int height);
	/**
	 */
	public native static int[] fire(int[] buff,int width,int height);
	/**
	 */
	public native static int[] comicStrip(int[] buff,int width,int height);
	/**
	 * @param buff
	 * @param width
	 * @param height
	 * @return
	 */
	public native static int[] light(int[] buff,int width,int height);
	/**
	 * @param buff
	 * @param width
	 * @param height
	 * @return
	 */
	public native static int[] eclosion(int[] buff,int width,int height);
	/**
	 * @param buff
	 * @param width
	 * @param height
	 * @return
	 */
	public native static int[] eclosionAndWhite(int[] buff,int width,int height);
	/**
	 * @param buff
	 * @param width
	 * @param height
	 * @return
	 */
	public native static int[] dim(int[] buff,int width,int height);
	/**
	 * lomo
	 * @param buff
	 * @param width
	 * @param height
	 * @return
	 */
	public native static int[] lomo(int[] buff,int width,int height,int type);
	
	public native static int[] filterfiveAndsix(int[] buff,int width,int height,int type);
	
}
