package com.cncyj.mostbrain.game.saolei;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.cncyj.mostbrain.R;

public class Utils {
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static Drawable[] getDrawable(Context context) {
		return new Drawable[] {
				context.getResources().getDrawable(R.drawable.bg_white),
				context.getResources().getDrawable(R.drawable.n1),
				context.getResources().getDrawable(R.drawable.n2),
				context.getResources().getDrawable(R.drawable.n3),
				context.getResources().getDrawable(R.drawable.n4),
				context.getResources().getDrawable(R.drawable.n5),
				context.getResources().getDrawable(R.drawable.n6),
				context.getResources().getDrawable(R.drawable.n7),
				context.getResources().getDrawable(R.drawable.n8),
				context.getResources().getDrawable(R.drawable.bg_red),
				context.getResources().getDrawable(R.drawable.saoleiredqi),
				context.getResources().getDrawable(R.drawable.bg_green),
				context.getResources().getDrawable(R.drawable.bomb_nomal),
				context.getResources().getDrawable(R.drawable.bg_blue),
				context.getResources().getDrawable(R.drawable.bomb_explode),
				context.getResources().getDrawable(R.drawable.bomb_wrong) };
	}
}
