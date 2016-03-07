package com.cncyj.mostbrain;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cncyj.mostbrain.adpter.WelcomeAdapter;
import com.cncyj.mostbrain.anomotion.TraslatePagerFormer;
import com.cncyj.mostbrain.coumview.SplshView;

/**
 * ����ҳ
 */
public class GuideActivity extends FragmentActivity implements
		OnPageChangeListener {

	private ViewPager vp;
	private WelcomeAdapter viewAdapter;
	private List<View> views;

	// �ײ�С��ͼƬ
	private ImageView[] dots;

	// ��¼��ǰѡ��λ��
	private int currentIndex;

	private int[] layout = { R.layout.guide_a, R.layout.guide_b,
			R.layout.guide_c };
	private View contentView;
	/**
	 * Handler:��ת����ͬ����
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.framelayout_guide);
		contentView = this.getLayoutInflater().inflate(R.layout.activity_guide,
				null);
		// ��ʼ��ҳ��
		initViews();
		// ��ʼ���ײ�С��
		initDots();
		setContentView(contentView);
	}

	/**
	 * ģ���������
	 */

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		// ��ʼ������ͼƬ�б�
		views.add(inflater.inflate(R.layout.guide_a, null));
		views.add(inflater.inflate(R.layout.guide_b, null));
		views.add(inflater.inflate(R.layout.guide_c, null));

		// ��ʼ��Adapter
		viewAdapter = new WelcomeAdapter(getSupportFragmentManager(), layout);

		vp = (ViewPager) contentView.findViewById(R.id.viewpager);
		vp.setPageTransformer(true, new TraslatePagerFormer());
		vp.setAdapter(viewAdapter);
		// �󶨻ص�
		vp.setOnPageChangeListener(this);
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) contentView.findViewById(R.id.ll);

		dots = new ImageView[views.size()];

		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// ����Ϊ��ɫ
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// ����Ϊ��ɫ����ѡ��״̬
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	// ������״̬�ı�ʱ����
	@Override
	public void onPageScrollStateChanged(int position) {
	}

	// ����ǰҳ�汻����ʱ����
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// ���µ�ҳ�汻ѡ��ʱ����
	@Override
	public void onPageSelected(int position) {
		// ���õײ�С��ѡ��״̬
		setCurrentDot(position);
	}

}



