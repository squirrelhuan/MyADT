package com.huan.myadt.activity;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.huan.myadt.R;
import com.huan.myadt.adapter.FragmentAdapter;
import com.huan.myadt.fragment.MainFragment1;
import com.huan.myadt.fragment.MainFragment2;
import com.huan.myadt.fragment.MainFragment3;
import com.huan.myadt.fragment.MainFragment4;

public class MainActivity extends AppCompatActivity implements OnCheckedChangeListener {
	private FragmentAdapter fragmentAdapter;
	MainFragment1 mainFragment1;
	MainFragment2 mainFragment2;
	MainFragment3 mainFragment3;
	MainFragment4 mainFragment4;
	ViewPager viewPager;
	
	RadioGroup main_radio_group;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		main_radio_group = (RadioGroup) findViewById(R.id.main_radio_group);
		main_radio_group.setOnCheckedChangeListener(this);
		
		List<Fragment> fragments = new ArrayList<Fragment>();

		mainFragment1 = new MainFragment1();
		mainFragment2 = new MainFragment2();
		mainFragment3 = new MainFragment3();
		mainFragment4 = new MainFragment4();
		fragments.add(mainFragment1);
		fragments.add(mainFragment2);
		fragments.add(mainFragment3);
		fragments.add(mainFragment4);
		//fragments.add(mainFragment4);
		fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		viewPager.setAdapter(fragmentAdapter);

		// 设置启动之后展示的fragment
		viewPager.setCurrentItem(0);
		getSupportActionBar().setTitle("日志");
		viewPager.setOffscreenPageLimit(4);
		viewPager.addOnPageChangeListener(mOnPageChangeListener);
		main_radio_group.check(R.id.main_radio_button_1);
		
		//this.g
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings_clear_logs) {
			mainFragment1.clearLogs();
			return true;
		}
		if (id == R.id.action_settings_clear_apps) {
			mainFragment2.clearApps();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				getSupportActionBar().setTitle("日志");
				main_radio_group.check(R.id.main_radio_button_1);
				break;
			case 1:
				getSupportActionBar().setTitle("应用");
				main_radio_group.check(R.id.main_radio_button_2);
				break;
			case 2:
				getSupportActionBar().setTitle("开发");
				main_radio_group.check(R.id.main_radio_button_3);
				break;
			case 3:
				getSupportActionBar().setTitle("设置");
				main_radio_group.check(R.id.main_radio_button_4);
				break;

			default:
				break;
			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		switch (checkedId) {
		case R.id.main_radio_button_1:
			viewPager.setCurrentItem(0);
			break;
		case R.id.main_radio_button_2:
			viewPager.setCurrentItem(1);
			break;
		case R.id.main_radio_button_3:
			viewPager.setCurrentItem(2);
			break;
		case R.id.main_radio_button_4:
			viewPager.setCurrentItem(3);
			break;
		default:
			break;
		}
	}

}
