package com.cncyj.mostbrain.game.fastcut.bn.util.box2d;

import com.cncyj.mostbrain.game.fastcut.bn.fastcut.MySurfaceView;
import com.cncyj.mostbrain.game.fastcut.bn.object.BNObject;
import com.cncyj.mostbrain.game.fastcut.bn.util.constant.Constant;
import com.cncyj.mostbrain.game.fastcut.bn.util.manager.ShaderManager;
import com.cncyj.mostbrain.game.fastcut.jbox2d.collision.shapes.CircleShape;
import com.cncyj.mostbrain.game.fastcut.jbox2d.collision.shapes.EdgeShape;
import com.cncyj.mostbrain.game.fastcut.jbox2d.common.Vec2;
import com.cncyj.mostbrain.game.fastcut.jbox2d.dynamics.Body;
import com.cncyj.mostbrain.game.fastcut.jbox2d.dynamics.BodyDef;
import com.cncyj.mostbrain.game.fastcut.jbox2d.dynamics.BodyType;
import com.cncyj.mostbrain.game.fastcut.jbox2d.dynamics.FixtureDef;
import com.cncyj.mostbrain.game.fastcut.jbox2d.dynamics.World;
//����������״�Ĺ�����
public class Box2DUtil 
{
	//����ֱ��
	public static Body createEdge
	(
			float[] data,
			World world,//����
			boolean isStatic,
			float density,//�ܶ�
			float friction,//Ħ��ϵ��
			float restitution,
			int index
	)
	{
		//������������
		BodyDef bd=new BodyDef();
		//�����Ƿ�Ϊ���˶�����
		if(isStatic)
		{
			bd.type=BodyType.STATIC;
		}
		else
		{
			bd.type=BodyType.DYNAMIC;
		}
		float positionX=(data[0]+data[2])/2;
		float positionY=(data[1]+data[3])/2;
		//����λ��						
		bd.position.set(positionX/Constant.RATE,positionY/Constant.RATE);
		//�������д�������
		Body bodyTemp = null;
		
		while(bodyTemp==null)
		{
			bodyTemp = world.createBody(bd);
		}
		//�ڸ����м�¼��Ӧ�İ�װ����
		bodyTemp.setUserData(index);
		//����������״
		EdgeShape ps=new EdgeShape();
		ps.set(new Vec2((data[0]-positionX)/Constant.RATE,(data[1]-positionY)/Constant.RATE), new Vec2((data[2]-positionX)/Constant.RATE,(data[3]-positionY)/Constant.RATE));
		//����������������
		FixtureDef fd=new FixtureDef();
		//����Ħ��ϵ��
		fd.friction =friction;
		//����������ʧ�ʣ�������
		fd.restitution = restitution;
		 //�����ܶ�
		fd.density=density;
		//������״
		fd.shape=ps;
		//���������������������
		if(!isStatic)
		{
			bodyTemp.createFixture(fd);
		}
		else
		{
			bodyTemp.createFixture(ps, 0);//�����ܶ�Ϊ0��PolygonShape����
		}
		return bodyTemp;
	}
	//����Բ�Σ���ɫ��
	public static BNObject createCircle
	(
			MySurfaceView mv,
			float x,//x����
			float y,//y����
			float radius,//�뾶
			World world,//����
			int programId,//����ID
			int texId,//��������
			float density,//�ܶ�
			float friction,//Ħ��ϵ��
			float restitution,//�ָ�ϵ��
			int index
	)
	{
		//������������
		BodyDef bd=new BodyDef(); 
		//�����Ƿ�Ϊ���˶�����		
		bd.type=BodyType.DYNAMIC;
		//����λ��
		bd.position.set(x/Constant.RATE,y/Constant.RATE);
		//�������д�������
		Body bodyTemp=null;
		while(bodyTemp==null)
		{
			bodyTemp= world.createBody(bd); 
		}
		//����������״
		CircleShape cs=new CircleShape();
		cs.m_radius=radius/Constant.RATE;		
		//����������������
		FixtureDef fd=new  FixtureDef();
		//�����ܶ�
		fd.density = density;   		
		//����Ħ��ϵ��
		fd.friction = friction;   
		//����������ʧ�ʣ�������
		fd.restitution =restitution;      
		//������״
		fd.shape=cs;
		//���������������������
		bodyTemp.createFixture(fd);
		//����BNObject�����
		return new BNObject(mv,bodyTemp, x, y, radius*2, radius*2, texId,ShaderManager.getShader(programId),index);
	}
}
