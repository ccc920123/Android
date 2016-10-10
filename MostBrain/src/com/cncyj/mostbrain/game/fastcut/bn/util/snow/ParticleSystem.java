package com.cncyj.mostbrain.game.fastcut.bn.util.snow;

import java.util.ArrayList;

import android.opengl.GLES20;

import com.cncyj.mostbrain.game.fastcut.bn.util.constant.MatrixState;
import com.cncyj.mostbrain.game.fastcut.bn.util.constant.ParticleConstant;


public class ParticleSystem implements Comparable<ParticleSystem> 
{
	//���ڴ�����е�����
	public ArrayList<ParticleSingle> alFsp=new ArrayList<ParticleSingle>();
	//-���ڴ����Ҫɾ��������
	ArrayList<ParticleSingle> alFspForDel=new ArrayList<ParticleSingle>();
	//����ת�����е����ӣ�ÿ�ζ�Ҫ���/
	public ArrayList<ParticleSingle> alFspForDraw=new ArrayList<ParticleSingle>();
	//���ڻ��Ƶ���������
	public ArrayList<ParticleSingle> alFspForDrawTemp=new ArrayList<ParticleSingle>();
	//��Դ��
	Object lock=new Object();
	//��ʼ��ɫ
	public float[] startColor;
	//��ֹ��ɫ
	public float[] endColor;
	//Դ�������
	public int srcBlend;
	//Ŀ��������
	public int dstBlend;
	//��Ϸ�ʽ
	public int blendFunc;
	//�������������
	public float maxLifeSpan;
	//���������ڲ���
	public float lifeSpanStep;
	//���Ӹ����߳�����ʱ����
	public int sleepSpan;
	//ÿ���緢����������
	public int groupCount;
	//���������
	public float sx;
	public float sy;
	//����λ��
	float positionX;
	float positionZ;
	//�����仯��Χ
	public float xRange;
	//���ӷ�����ٶ�
	public float vx;
	public float vy;
	//������
	ParticleForDraw fpfd;
	//������־λ
	boolean flag=true;
    public ParticleSystem(float positionx,float positionz,ParticleForDraw fpfd)
    {
    	this.positionX=positionx;
    	this.positionZ=positionz;
    	this.startColor=ParticleConstant.START_COLOR[ParticleConstant.CURR_INDEX];
    	this.endColor=ParticleConstant.END_COLOR[ParticleConstant.CURR_INDEX];
    	this.srcBlend=ParticleConstant.SRC_BLEND[ParticleConstant.CURR_INDEX]; 
    	this.dstBlend=ParticleConstant.DST_BLEND[ParticleConstant.CURR_INDEX];
    	this.blendFunc=ParticleConstant.BLEND_FUNC[ParticleConstant.CURR_INDEX];
    	this.maxLifeSpan=ParticleConstant.MAX_LIFE_SPAN[ParticleConstant.CURR_INDEX];
    	this.lifeSpanStep=ParticleConstant.LIFE_SPAN_STEP[ParticleConstant.CURR_INDEX];
    	this.groupCount=ParticleConstant.GROUP_COUNT[ParticleConstant.CURR_INDEX];
    	this.sleepSpan=ParticleConstant.THREAD_SLEEP[ParticleConstant.CURR_INDEX];
    	this.sx=0;
    	this.sy=0;
    	this.xRange=ParticleConstant.X_RANGE[ParticleConstant.CURR_INDEX];
    	this.vx=0;
    	this.vy=ParticleConstant.VY[ParticleConstant.CURR_INDEX];
    	this.fpfd=fpfd;
    	
    	new Thread()//�����߳�
    	{
    		public void run()
    		{
    			while(flag)
    			{
    				update();
    				try 
    				{
						Thread.sleep(sleepSpan);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
    			}
    		}
    	}.start();
    }
    public void drawSelf()
    {
    	//�������
        GLES20.glEnable(GLES20.GL_BLEND);
        //���û�Ϸ�ʽ
         GLES20.glBlendEquation(blendFunc);
        //���û������
        GLES20.glBlendFunc(srcBlend,dstBlend); 
        
        //��Ϊÿ�ν��л������ӵĸ����Ѿ������ǲ��ϱ仯�ģ�������Ҫ���ϵظ���
    	alFspForDrawTemp.clear();
    	synchronized(lock)
    	{
    		//������Ŀ����Ϊ�˱�֤��Ӻ������ͬʱ���У�Ҳ���Ǳ�֤��ÿ��add�����ж���
    		for(int i=0;i<alFspForDraw.size();i++)
    		{
    			alFspForDrawTemp.add(alFspForDraw.get(i));
    		}
    	}
    	MatrixState.pushMatrix();

    	MatrixState.translate(positionX, 1, positionZ);
    	for(ParticleSingle fsp:alFspForDrawTemp)
    	{
    		fsp.drawSelf(startColor,endColor,maxLifeSpan);
    	}
    	//�رջ��
        GLES20.glDisable(GLES20.GL_BLEND); 

        MatrixState.popMatrix();
    }
    
    public void update()
    {
		//�緢������
    	for(int i=0;i<groupCount;i++)
    	{
    		//�����ĸ��������������ӵ�λ��------**/
    		float px=(float) (sx+xRange*(Math.random()*2-1.0f));

            float vx=(sx-px)/150;
            //x������ٶȺ�С,���ԾͲ����������Ļ�������
            ParticleSingle fsp=new ParticleSingle(px,0.01f,vx,vy,fpfd);
            alFsp.add(fsp);
    	}   	
    	
    	//��ջ���������б����б���Ҫ�洢��Ҫɾ��������
    	alFspForDel.clear();
    	for(ParticleSingle fsp:alFsp)
    	{
    		//��ÿ������ִ���˶�����
    		fsp.go(lifeSpanStep);
    		//��������Ѿ����ڵ�ʱ���Ѿ��㹻�ˣ��Ͱ�����ӵ���Ҫɾ���������б�
    		if(fsp.lifeSpan>this.maxLifeSpan)
    		{
    			alFspForDel.add(fsp);
    		}
    	}
    	
    	//ɾ����������
    	for(ParticleSingle fsp:alFspForDel)
    	{
    		alFsp.remove(fsp);
    	}
    	//alFsp�б��д�������е����Ӷ����������б�Ϊ�����������������ӣ�ͬʱҲ����ɾ��ĳЩ���ڵ����Ӷ���
    	//���»����б� 
    	synchronized(lock)
    	{
    		alFspForDraw.clear();
    		for(int i=0;i<alFsp.size();i++)
    		{
    			alFspForDraw.add(alFsp.get(i));
    		}
    	}
    }
	@Override
	public int compareTo(ParticleSystem another) {
		return 0;
	}
}
