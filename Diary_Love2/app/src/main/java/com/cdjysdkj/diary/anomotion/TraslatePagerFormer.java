package com.cdjysdkj.diary.anomotion;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.view.ViewGroup;

import com.cdjysdkj.diary.R;

public class TraslatePagerFormer implements PageTransformer{
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void transformPage(View view, float position) {
		ViewGroup group = (ViewGroup) view.findViewById(R.id.rl);
		
		
		/*if (position>-1&&position<1) {
		//	ViewGroup group = (ViewGroup) view.findViewById(R.id.rl);
			for (int i = 0; i < group.getChildCount(); i++) {
				View child=group.getChildAt(i);//�ӿؼ�
				float foctor=(float) Math.random();
				if(child.getTag()==null){
					child.setTag(foctor);
				}else{
					foctor=(Float) child.getTag();
				}
				//position:0~-1
//				child.setTranslationX(postion*foctor*child.getWidth());
				//child.setTranslationX(postion*i*100);
				child.setTranslationX(-position*i*100);
			}
		}*/

		group.setScaleX(Math.max(0.8f, 1-Math.abs(position)));
		group.setScaleY(Math.max(0.8f, 1-Math.abs(position)));
		
		group.setPivotX(position<0f?group.getWidth():0f);
		group.setPivotY(group.getHeight()*0.5f);
		group.setRotationY(position*90);
		

	}
	
}