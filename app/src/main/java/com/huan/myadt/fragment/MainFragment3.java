package com.huan.myadt.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huan.myadt.R;
import com.huan.myadt.activity.DesignActivity;
import com.huan.myadt.activity.LearnWebActivity;
import com.huan.myadt.activity.MainActivity;
import com.huan.myadt.activity.MarkdownActivity;
import com.huan.myadt.activity.TestActivity;
import com.huan.myadt.adapter.MySimpleListAdapter;
import com.huan.myadt.fragment.base.BaseFragment;
import com.huan.myadt.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

public class MainFragment3 extends BaseFragment implements View.OnClickListener {

	MainActivity mainActivity;


	private TextView tv_study;
	private TextView tv_test;
	private TextView tv_design;
	private TextView tv_demo;

	@Override
	public View setContentUI(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_main_3, container, false);
	}
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void init() {

		mainActivity = (MainActivity) getActivity();


		tv_study = (TextView)findViewById(R.id.tv_study);
		tv_study.setOnClickListener(this);
		tv_test = (TextView)findViewById(R.id.tv_test);
		tv_test.setOnClickListener(this);
		tv_design = (TextView)findViewById(R.id.tv_design);
		tv_design.setOnClickListener(this);
		tv_demo = (TextView)findViewById(R.id.tv_demo);
		tv_demo.setOnClickListener(this);

	}



	@Override
	public void onClick(View v) {

		Bundle bundle = new Bundle();
		switch (v.getId()) {
			case R.id.tv_study:
				bundle.putInt("StudyType",1);
				IntentUtil.jump(getActivity(), TestActivity.class, bundle);
				break;
			case R.id.tv_test:
				bundle.putInt("StudyType",0);
				IntentUtil.jump(getActivity(), TestActivity.class, bundle);
				break;
			case R.id.tv_design:
				IntentUtil.jump(getActivity(), DesignActivity.class, null);
				break;
			case R.id.tv_demo:
				IntentUtil.jump(getActivity(), MarkdownActivity.class, null);
				break;
			default:
				break;
		}
	}
	
}
