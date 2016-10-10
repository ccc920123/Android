package com.cncyj.mostbrain.game.fastcut.bn.util.manager;

import java.util.HashMap;

import android.annotation.SuppressLint;

import com.cncyj.mostbrain.game.fastcut.bn.fastcut.MySurfaceView;
import com.cncyj.mostbrain.game.fastcut.bn.util.constant.ShaderUtil;

@SuppressLint("UseSparseArrays")
public class ShaderManager 
{
	static String[][] programs={{"vertex.sh","frag.sh"},{"vertex_snow.sh","frag_snow.sh"},
		{"vertex.sh","frag_fly.sh"}};//������ɫ��������
	static HashMap<Integer,Integer> list=new HashMap<Integer,Integer>();
	public static void loadingShader(MySurfaceView mv)//������ɫ��
	{
		for(int i=0;i<programs.length;i++)
		{
			//���ض�����ɫ���Ľű�����
			String mVertexShader=ShaderUtil.loadFromAssetsFile(programs[i][0], mv.getResources());
			//����ƬԪ��ɫ���Ľű�����
			String mFragmentShader=ShaderUtil.loadFromAssetsFile(programs[i][1],mv.getResources());
			//���ڶ�����ɫ����ƬԪ��ɫ����������
			int mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
			list.put(i, mProgram);
		}  
	}
	public static int getShader(int index)//���ĳ�׳���
	{
		int result=0;
		if(list.get(index)!=null)//����б����д��׳���
		{
			result=list.get(index);
		}
		return result;
	}
}
