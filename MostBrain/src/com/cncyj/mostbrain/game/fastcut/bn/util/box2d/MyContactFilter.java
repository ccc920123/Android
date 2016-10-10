package com.cncyj.mostbrain.game.fastcut.bn.util.box2d;

import com.cncyj.mostbrain.game.fastcut.bn.fastcut.MySurfaceView;
import com.cncyj.mostbrain.game.fastcut.jbox2d.callbacks.ContactFilter;
import com.cncyj.mostbrain.game.fastcut.jbox2d.dynamics.Body;
import com.cncyj.mostbrain.game.fastcut.jbox2d.dynamics.Fixture;

public class MyContactFilter extends ContactFilter//��ײ���������
{
	public MyContactFilter(MySurfaceView mv){	}//������
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB)///�����Ƿ���ײ�ķ���
	{
		Body bodyA= fixtureA.getBody();
		Body bodyB= fixtureB.getBody();
		if((Integer)(bodyA.getUserData())==-1||(Integer)(bodyB.getUserData())==-1)//�жϲ�����ײ�ĸ����Ƿ�����ǽ��
		{
			return true;
		}else
		{
			return false;
		}
	}
}
