package com.huan.myadt.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huan.myadt.R;
import com.huan.myadt.activity.LearnWebActivity;
import com.huan.myadt.activity.MainActivity;
import com.huan.myadt.activity.MarkdownActivity;
import com.huan.myadt.activity.TestActivity;
import com.huan.myadt.adapter.MySimpleListAdapter;
import com.huan.myadt.fragment.base.BaseFragment;
import com.huan.myadt.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

public class MainFragment3 extends BaseFragment implements OnItemClickListener,View.OnClickListener {

	MainActivity mainActivity;
	MySimpleListAdapter adapter;


	ListView listView;
	List<String> items = new ArrayList<String>();

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
		items.add("SimpleFactory模式");
		items.add("FactoryMethod模式");
		items.add("AbstractFactory模式");
		items.add("Singleton模式");
		items.add("Builder模式");
		items.add("Prototype模式");
		items.add("Adapter模式");
		items.add("Composite模式");
		items.add("Decorator模式");
		items.add("Proxy模式");
		items.add("Flyweight模式");
		items.add("Facade模式");
		items.add("Bridge模式");
		items.add("Immutable模式");
		items.add("Strategy模式");
		items.add("TemplateMethod模式");
		items.add("Objserver模式");
		items.add("Iterator模式");
		items.add("ChainOfResponsibility模式");
		items.add("Command模式");
		items.add("Memento模式");
		items.add("State模式");
		items.add("Visitor模式");
		items.add("Interpreter模式");
		items.add("Mediator模式");
		mainActivity = (MainActivity) getActivity();
		listView = (ListView) findViewById(R.id.list_view);
		adapter = new MySimpleListAdapter(getActivity(), items);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		LinearLayout ll_study = (LinearLayout) findViewById(R.id.ll_study);
		ll_study.setOnClickListener(this);
		LinearLayout ll_test = (LinearLayout) findViewById(R.id.ll_test);
		ll_test.setOnClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putString("Title", items.get(arg2));
		IntentUtil.jump(getActivity(), LearnWebActivity.class, bundle);
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.ll_study:
				IntentUtil.jump(getActivity(), MarkdownActivity.class, null);
				break;
			case R.id.ll_test:
				IntentUtil.jump(getActivity(), TestActivity.class, null);
				break;
			default:
				break;
		}
	}
	
}
