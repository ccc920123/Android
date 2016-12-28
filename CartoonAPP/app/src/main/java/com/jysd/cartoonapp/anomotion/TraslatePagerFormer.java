package com.jysd.cartoonapp.anomotion;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.view.ViewGroup;

import com.jysd.cartoonapp.R;


public class TraslatePagerFormer implements PageTransformer{
/**
 * ��viewpager����ʱ�򣬶���ص��÷���
 * view����ǰҳ��
 * postion������λ��
 * ��-1��1ʱ  ����Ļ��
 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void transformPage(View view, float position) {
		ViewGroup group = (ViewGroup) view.findViewById(R.id.rl);
		
		
		/*if (position>-1&&position<1) {
			//���ؼ�������ͬ������ҳ��Ż�ʵ��ͬ����Ч��
			//�����һ���ж�
		//	ViewGroup group = (ViewGroup) view.findViewById(R.id.rl);
			for (int i = 0; i < group.getChildCount(); i++) {
				View child=group.getChildAt(i);//�ӿؼ�
				//�����ռ䲻ͬ���ƶ����ٶ�
				float foctor=(float) Math.random();
				if(child.getTag()==null){
					child.setTag(foctor);
				}else{
					foctor=(Float) child.getTag();//��ÿ���ؼ�������ͬ�ļ��ٶ�
				}
				//position:0~-1
//				child.setTranslationX(postion*foctor*child.getWidth());
				//child.setTranslationX(postion*i*100);
				child.setTranslationX(-position*i*100);
			}
		}*/
		/*//���ŷ�Χ0~1  Ч��ҳ���С������ʾ����Ļ��
		group.setScaleX(1-Math.abs(position));//ҳ������
		group.setScaleY(1-Math.abs(position));*/
		
		//Ч�� ���������ŵ�һ����С����ƽ��  
		group.setScaleX(Math.max(0.8f, 1-Math.abs(position)));//ҳ������
		group.setScaleY(Math.max(0.8f, 1-Math.abs(position)));
		
		//3D�ⷭת
		group.setPivotX(position<0f?group.getWidth():0f);
		group.setPivotY(group.getHeight()*0.5f);
		group.setRotationY(position*90);//����Խ��3D���ԽС
		
		/*//3D�ڷ�ת
				group.setPivotX(position<0f?group.getWidth():0f);
				group.setPivotY(group.getHeight()*0.5f);
				group.setRotationY(-position*90);//����Խ��3D���ԽС
		*/
		/*//Ч�����������������Լ�������ת
		group.setPivotX(group.getWidth()*0.5f);
		group.setPivotY(group.getHeight()*0.5f);
		group.setRotationY(-position*90);*/
	}
	
}