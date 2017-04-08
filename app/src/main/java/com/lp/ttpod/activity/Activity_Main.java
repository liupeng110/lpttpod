package com.lp.ttpod.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lp.ttpod.R;
import com.lp.ttpod.fragment.Fragment_Charts;
import com.lp.ttpod.fragment.Fragment_My;

import com.lp.ttpod.util.L;
import com.nineoldandroids.view.ViewPropertyAnimator;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/** 717219917@qq.com  2016/12/14 0:26 */
@ContentView(R.layout.activity_main)//主页面
public class Activity_Main extends Activity_Base {
	@ViewInject(R.id.viewPager) private ViewPager viewPager;
	@ViewInject(R.id.tab_game) private TextView tab_game;
	@ViewInject(R.id.tab_app) private TextView tab_app;
	@ViewInject(R.id.tab_ceshi) private TextView tab_ceshi;
	@ViewInject(R.id.line) private View line;
	private ArrayList<Fragment> fragments;
	private ArrayList<TextView>textViews;
	private int line_width;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
		 ViewPropertyAnimator.animate(tab_app).scaleX(1.0f).setDuration(0);

		fragments = new ArrayList<>();
		fragments.add(new Fragment_My());
		fragments.add(new Fragment_Charts());
		fragments.add(new Fragment_Charts());

		textViews = new ArrayList<>();
		textViews.add(tab_app);
		textViews.add(tab_game);
		textViews.add(tab_ceshi);

		line_width = getWindowManager().getDefaultDisplay().getWidth() / fragments.size();
		line.getLayoutParams().width = line_width;
		line.requestLayout();

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new FragmentStatePagerAdapter( getSupportFragmentManager()) {
			@Override public int getCount() {
				return fragments.size();
			}
			@Override public Fragment getItem(int index) {
				return fragments.get(index);
			}
		});

		viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override public void onPageSelected(int index) { changeState(index); }
			@Override public void onPageScrolled(int index, float arg1, int offset) {
				float tagerX = index * line_width + offset / fragments.size();
				ViewPropertyAnimator.animate(line).translationX(tagerX).setDuration(0);
			}
            @Override public void onPageScrollStateChanged(int state) { }
		});

		tab_app.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(0);
			}
		});
		tab_game.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(1);

			}
		});
		tab_ceshi.setOnClickListener(new OnClickListener() {// ����

					@Override
					public void onClick(View arg0) {
						viewPager.setCurrentItem(2);

					}
				});
		    int size=textViews.size();
			for (int curr=0;curr<size;curr++)
			{
				final int temp =curr;
				textViews.get(temp).setOnClickListener(new OnClickListener()
				{
					@Override public void onClick(View v)
					{
						 viewPager.setCurrentItem(temp);
					}
				});
		}

	}

 	private void changeState(int index) {
		L.i("当前index:"+index);
		int size=textViews.size();
		for (int curr=0;curr<size;curr++) {
			if (curr==index){
				L.i("当前index:"+index+"---------------color");
				textViews.get(curr).setTextColor(getResources().getColor(R.color.green));
				ViewPropertyAnimator.animate(textViews.get(curr)).scaleX(1.3f).scaleY(1.3f).setDuration(200);
			}else {
				L.i("当前index:"+index+",-----------else");
				textViews.get(curr).setTextColor(getResources().getColor(R.color.gray_white));
				ViewPropertyAnimator.animate(textViews.get(curr)).scaleX(1.0f).scaleY(1.0f).setDuration(200);
			}
		}

	}





}
