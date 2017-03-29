package com.huan.myadt.fragment;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.huan.myadt.R;
import com.huan.myadt.activity.ErrorAnalysisActivity;
import com.huan.myadt.activity.MainActivity;
import com.huan.myadt.adapter.FlowAdapter;
import com.huan.myadt.adapter.MyLogListAdapter;
import com.huan.myadt.adapter.menu.ConstellationAdapter;
import com.huan.myadt.adapter.menu.GirdDropDownAdapter;
import com.huan.myadt.adapter.menu.ListDropDownAdapter;
import com.huan.myadt.adapter.menu.TagsAdapter;
import com.huan.myadt.bean.CGQ_log.LogCate;
import com.huan.myadt.event.LogCateEvent.ILogCateListener;
import com.huan.myadt.event.LogCateEvent.LogCateEvent;
import com.huan.myadt.event.LogCateEvent.LogCateManager;
import com.huan.myadt.fragment.base.BaseFragment;
import com.huan.myadt.provider.LogCateProvider;
import com.huan.myadt.provider.LogCateProvider.LEVEL_Log;
import com.huan.myadt.utils.ActivityUtils;
import com.huan.myadt.utils.DaoUtils;
import com.huan.myadt.utils.IntentUtil;
import com.huan.myadt.utils.MoveBg;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class MainFragment1 extends BaseFragment
		implements ILogCateListener, OnItemClickListener, OnClickListener, OnItemLongClickListener {

	@Bind(R.id.dropDownMenu)
	DropDownMenu mDropDownMenu;
	private String headers[] = { "Level", "Tag", "性别", "星座" };
	private List<View> popupViews = new ArrayList<>();

	private GirdDropDownAdapter levelAdapter;
	private ListDropDownAdapter ageAdapter;
	private ListDropDownAdapter sexAdapter;
	private ConstellationAdapter constellationAdapter;
	private TagsAdapter tagsAdapter;

	private String levels[] = { "All", "Verbose", "Debug", "Info", "Warn", "Error", "Assert" };
	private String ages[] = { "All", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上" };
	private String constellations[] = { "All", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上" };
	private String sexs[] = { "不限", "男", "女" };
	private List<String> tags;
	private List<String> tags_c;

	private int constellationPosition = 0;
	private int tagsPosition = 0;
	private static int level;

	private FlowAdapter flowLayout;
	private RelativeLayout layout;
	private LinearLayout title_layout;
	private TextView tv_front;// 需要移动的View
	private TextView btn_title_bar_01, btn_title_bar_02, btn_title_bar_03, btn_title_bar_04, btn_title_bar_05,
			btn_title_bar_06;

	MainActivity mainActivity;
	ListView listView;
	MyLogListAdapter adapter;
	ArrayList<LogCate> mList = new ArrayList<LogCate>();

	private int mLastY = 0;
	int avg_width = 0;// 用于记录平均每个标签的宽度，移动的时候需要
	int startX = 0;// 移动的起始位置

	@Override
	public View setContentUI(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_main_1, container, false);
	}

	@Override
	public void onResume() {
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		LogCateManager.getInstance().removeDoorListener(this);// 给门1增加监听器
		super.onDestroy();
	}

	private ClipboardManager myClipboard;
	private ClipData myClip;

	@Override
	public void init() {
		LogCateManager.getInstance().addDoorListener(this);// 给门1增加监听器
		initView();
		myClipboard = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
		mainActivity = (MainActivity) getActivity();
		adapter = new MyLogListAdapter(getActivity(), mList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		listView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					mLastY = (int) event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					int y = (int) event.getY();
					if (!isPlaying && Math.abs(y - mLastY) > 5) {
						if (y < mLastY) // 向下
						{
							if (title_layout.getVisibility() == View.VISIBLE) {
								ShowTitle(title_layout, mDropDownMenu, true);
							}
						} else // 向上
						{
							if (title_layout.getVisibility() == View.GONE) {
								ShowTitle(title_layout, mDropDownMenu, false);
							}
						}
					}
					break;
				}
				return false;
			}
		});
		/*
		 * listView.setOnScrollListener(new OnScrollListener() {
		 * 
		 * @Override public void onScrollStateChanged(AbsListView view, int
		 * scrollState) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onScroll(AbsListView view, int
		 * firstVisibleItem, int visibleItemCount, int totalItemCount) {
		 * if(firstVisibleItem==0){ Log.e("log", "滑到顶部"); //
		 * title_layout.setVisibility(View.GONE); //
		 * ShowTitle(title_layout,mDropDownMenu, false); }
		 * if(visibleItemCount+firstVisibleItem==totalItemCount){ Log.e("log",
		 * "滑到底部"); } } });
		 */
		layout = (RelativeLayout) findViewById(R.id.layout_title_bar);

		title_layout = mDropDownMenu.getTabMenuView();
		btn_title_bar_01 = (TextView) findViewById(R.id.btn_title_bar_01);
		btn_title_bar_01.setOnClickListener(this);
		btn_title_bar_02 = (TextView) findViewById(R.id.btn_title_bar_02);
		btn_title_bar_02.setOnClickListener(this);
		btn_title_bar_03 = (TextView) findViewById(R.id.btn_title_bar_03);
		btn_title_bar_03.setOnClickListener(this);
		btn_title_bar_04 = (TextView) findViewById(R.id.btn_title_bar_04);
		btn_title_bar_04.setOnClickListener(this);
		btn_title_bar_05 = (TextView) findViewById(R.id.btn_title_bar_05);
		btn_title_bar_05.setOnClickListener(this);
		btn_title_bar_06 = (TextView) findViewById(R.id.btn_title_bar_06);
		btn_title_bar_06.setOnClickListener(this);

		tv_front = new TextView(getActivity());
		tv_front.setBackgroundResource(R.drawable.linearlayout_white);
		tv_front.setTextColor(Color.WHITE);
		tv_front.setWidth(140);
		tv_front.setHeight(75);
		tv_front.setText("所有");
		tv_front.setTextColor(this.getResources().getColor(R.color.blue));
		tv_front.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		layout.addView(tv_front, param);
		
		level = LEVEL_Log.All;
		for (LogCate logcate : LogCateProvider.getInstance().getLogsByLevelAndTag(0, tags_c)) {
			mList.add(logcate);
		}
	}

	private void initView() {
		// init city menu
		final ListView cityView = new ListView(getActivity());
		levelAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(levels));
		cityView.setDividerHeight(0);
		cityView.setAdapter(levelAdapter);

		// init age menu
		final ListView ageView = new ListView(getActivity());
		ageView.setDividerHeight(0);
		ageAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(ages));
		ageView.setAdapter(ageAdapter);

		// init sex menu
		final ListView sexView = new ListView(getActivity());
		sexView.setDividerHeight(0);
		sexAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(sexs));
		sexView.setAdapter(sexAdapter);

		// init constellation
		final View constellationView = getActivity().getLayoutInflater().inflate(R.layout.custom_layout, null);
		GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
		constellationAdapter = new ConstellationAdapter(getActivity(), Arrays.asList(constellations));
		constellation.setAdapter(constellationAdapter);
		TextView ok = ButterKnife.findById(constellationView, R.id.ok);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDropDownMenu
						.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
				mDropDownMenu.closeMenu();
			}
		});

		// init TAG
		final View tagsView = getActivity().getLayoutInflater().inflate(R.layout.custom_tags_layout, null);
		flowLayout = ButterKnife.findById(tagsView, R.id.flowlayout_tags);
		tags = JSONArray.parseArray(DaoUtils.getString(MainFragment1.this.getActivity(), "TAGS"), String.class);
		tags_c = JSONArray.parseArray(DaoUtils.getString(MainFragment1.this.getActivity(), "TAGS_C"), String.class);
		if(tags==null||tags.size()==0){
			tags = new ArrayList<String>();
			tags_c = new ArrayList<String>();
			tags.add("All");
			tags_c.add("All");
			DaoUtils.saveString(MainFragment1.this.getActivity(), "TAGS", JSON.toJSONString(tags));
			DaoUtils.saveString(MainFragment1.this.getActivity(), "TAGS_C", JSON.toJSONString(tags_c));
		}
		flowLayout.setData(this.getActivity(), tags,tags_c);
		TextView tv_tag_add = ButterKnife.findById(tagsView, R.id.tv_tag_add);
		tv_tag_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initDialog();
			}
		});
		TextView tv_tag_clear = ButterKnife.findById(tagsView, R.id.tv_tag_clear);
		tv_tag_clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initDialogClear();
			}
		});

		// init popupViews
		popupViews.add(cityView);
		popupViews.add(tagsView);
		popupViews.add(constellationView);
		popupViews.add(ageView);
		// popupViews.add(sexView);
		// popupViews.add(constellationView);

		// add item click event
		cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				levelAdapter.setCheckItem(position);
				mDropDownMenu.setTabText(position == 0 ? headers[0] : levels[position]);
				/*
				 * "All", "Verbose", "Debug", "Info", "Warn", "Error", "Assert"
				 */
				mList.clear();
				switch (levels[position]) {
				case "All":
					level = LEVEL_Log.All;
					break;
				case "Verbose":
					level = LEVEL_Log.Verbose;
					break;
				case "Debug":
					level = LEVEL_Log.Debug;
					break;
				case "Info":
					level = LEVEL_Log.Info;
					break;
				case "Warn":
					level = LEVEL_Log.Warn;
					break;
				case "Error":
					level = LEVEL_Log.Info;
					break;
				case "Assert":
					level = LEVEL_Log.Warn;
					break;
				default:
					break;
				}
				for (LogCate logcate : LogCateProvider.getInstance().getLogsByLevelAndTag(level,tags_c)) {
					mList.add(logcate);
				}
				// myHandler = new MyHandler(getActivity().getMainLooper());
				adapter.notifyDataSetChanged();
				mDropDownMenu.closeMenu();
			}
		});

		ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ageAdapter.setCheckItem(position);
				mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
				mDropDownMenu.closeMenu();
			}
		});

		sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				sexAdapter.setCheckItem(position);
				mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
				mDropDownMenu.closeMenu();
			}
		});

		flowLayout.setOnItemClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tagsPosition = flowLayout.setCheckItem(v);
				if (tags_c.contains(((TextView) v).getText().toString())) {
					tags_c.remove(((TextView) v).getText().toString());
					v.setBackgroundResource(R.drawable.uncheck_bg);
					((TextView) v).setTextColor(getResources().getColor(R.color.drop_down_unselected));

				} else {
					tags_c.add((String) ((TextView) v).getText());
					v.setBackgroundResource(R.drawable.check_bg);
					((TextView) v).setTextColor(getResources().getColor(R.color.drop_down_selected));
				}
				if (tags_c.size() != 1) {
					tagsPosition = 0;
				}
				DaoUtils.saveString(MainFragment1.this.getActivity(), "TAGS_C", JSON.toJSONString(tags_c));
				mDropDownMenu.setTabText(tagsPosition == 0 ? headers[1] : tags.get(tagsPosition));
				
				/*
				 * "All", "Verbose", "Debug", "Info", "Warn", "Error", "Assert"
				 */
				mList.clear();
				
				for (LogCate logcate : LogCateProvider.getInstance().getLogsByLevelAndTag(level,tags_c)) {
					mList.add(logcate);
				}
				// myHandler = new MyHandler(getActivity().getMainLooper());
				adapter.notifyDataSetChanged();
				
				// mDropDownMenu.closeMenu();
				// mDropDownMenu.closeMenu();
			}
		});

		constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				constellationAdapter.setCheckItem(position);
				constellationPosition = position;
			}
		});

		// init context view
		/*
		 * TextView contentView = new TextView(getActivity());
		 * contentView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
		 * contentView.setText("内容显示区域");
		 * contentView.setGravity(Gravity.CENTER);
		 * contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		 */

		final View contentView = getActivity().getLayoutInflater().inflate(R.layout.contentview_layout, null);
		listView = ButterKnife.findById(contentView, R.id.list_view);
		/*
		 * ListView listView = new ListView(getActivity());
		 * listView.setLayoutParams(new
		 * ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
		 * LayoutParams.MATCH_PARENT)); listView.setDivider(new
		 * ColorDrawable(getActivity
		 * ().getResources().getColor(R.color.light_blank)));
		 * listView.setDividerHeight(1);
		 */
		// listView.set

		// init dropdownview
		mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
	}

	private void initDialog() {
		LayoutInflater inflaterDl = LayoutInflater.from(this.getActivity());
		LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.dialog_tag_add, null);

		// 对话框
		final Dialog dialog = new AlertDialog.Builder(this.getActivity()).create();
		final TextView tv_dialog_name = (TextView) layout.findViewById(R.id.tv_dialog_name);
		final EditText tagName = (EditText) layout.findViewById(R.id.et_dialog_tagName);
		// tv_dialog_name.setText(exceptionMoel.getSimpleName());
		Button btn_dialog_cancel = (Button) layout.findViewById(R.id.btn_dialog_cancel);
		btn_dialog_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showToast(DaoUtils.getString(MainFragment1.this.getActivity(), "TAGS"));
				dialog.dismiss();
			}
		});
		Button btn_dialog_ok = (Button) layout.findViewById(R.id.btn_dialog_ok);
		btn_dialog_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tags.contains(tagName.getText())) {
					showToast("Tag has bean Exist");
				} else {
					String value = tagName.getText().toString();
					tags.add(value);
					DaoUtils.saveString(MainFragment1.this.getActivity(), "TAGS", JSON.toJSONString(tags));
					
					showToast("length:" + tags.size());
					flowLayout.setData(MainFragment1.this.getActivity(), tags,tags_c);
					showToast("Tag add success");
					dialog.dismiss();
				}
			}
		});
		/*
		 * final TextView tv_dialog_description = (TextView)
		 * layout.findViewById(R.id.tv_dialog_description);
		 * tv_dialog_description.setText(exceptionMoel.getDescription());
		 */

		dialog.show();
		dialog.getWindow().setContentView(layout);
		// 下面两行代码加入后即可弹出输入法
		dialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

	}

	private void initDialogClear() {
		LayoutInflater inflaterDl = LayoutInflater.from(this.getActivity());
		LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.dialog_common, null);

		// 对话框
		final Dialog dialog = new AlertDialog.Builder(this.getActivity()).create();
		final TextView tv_dialog_name = (TextView) layout.findViewById(R.id.tv_dialog_name);
		tv_dialog_name.setText("Confirm delete");
		final TextView tv_dialog_content = (TextView) layout.findViewById(R.id.tv_dialog_content);
		tv_dialog_content.setText("are you sure to clear all tags?");
		Button btn_dialog_cancel = (Button) layout.findViewById(R.id.btn_dialog_cancel);
		btn_dialog_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		Button btn_dialog_ok = (Button) layout.findViewById(R.id.btn_dialog_ok);
		btn_dialog_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tags.clear();
				DaoUtils.saveString(MainFragment1.this.getActivity(), "TAGS", JSON.toJSONString(tags));
				flowLayout.setData(MainFragment1.this.getActivity(), tags,tags_c);
				dialog.dismiss();
			}
		});
		/*
		 * final TextView tv_dialog_description = (TextView)
		 * layout.findViewById(R.id.tv_dialog_description);
		 * tv_dialog_description.setText(exceptionMoel.getDescription());
		 */

		dialog.show();
		dialog.getWindow().setContentView(layout);
		// 下面两行代码加入后即可弹出输入法
		dialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

	}

	@Override
	public void onClick(View v) {
		avg_width = findViewById(R.id.layout).getWidth();
		switch (v.getId()) {
		case R.id.btn_title_bar_01:
			MoveBg.moveFrontBg(tv_front, startX, 0, 0, 0);
			startX = 0;
			tv_front.setWidth(btn_title_bar_03.getWidth() + 20);
			tv_front.setText(((TextView) v).getText());
			break;
		case R.id.btn_title_bar_02:
			MoveBg.moveFrontBg(tv_front, startX, avg_width, 0, 0);
			startX = avg_width;
			tv_front.setWidth(btn_title_bar_02.getWidth() + 20);
			tv_front.setText(((TextView) v).getText());
			break;
		case R.id.btn_title_bar_03:
			MoveBg.moveFrontBg(tv_front, startX, avg_width * 2, 0, 0);
			startX = avg_width * 2;
			tv_front.setWidth(btn_title_bar_03.getWidth() + 20);
			tv_front.setText(((TextView) v).getText());
			break;
		case R.id.btn_title_bar_04:
			MoveBg.moveFrontBg(tv_front, startX, avg_width * 3, 0, 0);
			startX = avg_width * 3;
			tv_front.setWidth(btn_title_bar_03.getWidth() + 20);
			tv_front.setText(((TextView) v).getText());
			break;
		case R.id.btn_title_bar_05:
			MoveBg.moveFrontBg(tv_front, startX, avg_width * 4, 0, 0);
			startX = avg_width * 4;
			tv_front.setWidth(btn_title_bar_03.getWidth() + 20);
			tv_front.setText(((TextView) v).getText());
			break;
		case R.id.btn_title_bar_06:
			MoveBg.moveFrontBg(tv_front, startX, avg_width * 5, 0, 0);
			startX = avg_width * 5;
			tv_front.setWidth(btn_title_bar_03.getWidth() + 20);
			tv_front.setText(((TextView) v).getText());
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent mIntent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("LogCate", mList.get(position));
		IntentUtil.jump(getActivity(), ErrorAnalysisActivity.class, bundle);
	}

	MyHandler myHandler;

	@Override
	public void doorEvent(LogCateEvent event) {
		mList.clear();
		for (LogCate logcate : LogCateProvider.getInstance().getLogsByLevelAndTag(level,tags_c)) {
			mList.add(logcate);
		}
		// adapter.notifyDataSetChanged();
		if (ActivityUtils.isForeground(mainActivity, "com.huan.myadt.activity.MainActivity")) {
			if (myHandler == null) {
				myHandler = new MyHandler(getActivity().getMainLooper());
			} else {
				myHandler.sendEmptyMessage(1);
			}
		}
	}

	/**
	 * 接受消息，处理消息 ，此Handler会与当前主线程一块运行
	 */

	class MyHandler extends Handler {
		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法，接受数据
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.d("MyHandler", "handleMessage。。。。。。");
			super.handleMessage(msg);
			// 此处可以更新UI
			Bundle b = msg.getData();
			String color = b.getString("color");
			MainFragment1.this.adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		copy(mList.get(position).getText());
		return false;
	}

	public void copy(String text) {
		myClip = ClipData.newPlainText("text", text);
		myClipboard.setPrimaryClip(myClip);
		Toast.makeText(getActivity().getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
	}

	public void clearLogs() {
		LogCateProvider.getInstance().clear();
		mList.clear();
		adapter.notifyDataSetChanged();
	}

	private boolean isPlaying;

	public void ShowTitle(final View v, final View v2, final boolean up) {
		v.setVisibility(View.VISIBLE);
		isPlaying = true;
		final float y_old = v.getY();
		// final RelativeLayout.LayoutParams layoutParams =
		// (android.widget.RelativeLayout.LayoutParams) v2.getLayoutParams();
		// final float height_old =layoutParams.height;
		ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float curValue = (float) animation.getAnimatedValue();
				if (!up) {
					v.setY(y_old + (curValue / 100 * v.getHeight()));
					/*
					 * layoutParams.topMargin = (int) ((curValue /
					 * 100)*v.getHeight()); v2.setLayoutParams(layoutParams);
					 */

					// v2.setY(y_old + (curValue / 100 * v.getHeight()));
					// RelativeLayout.LayoutParams params =
					// (RelativeLayout.LayoutParams) v2.getLayoutParams();
					// params.width = dip2px(getActivity(), width);
					// params.height = dip2px(getActivity(), height);
					// params.setMargins(dip2px(MainActivity.this, 1), 0, 0, 0);
					// // 可以实现设置位置信息，如居左距离，其它类推
					// params.topMargin = dip2px(getActivity(), curValue / 100 *
					// v.getHeight());
					// v2.setLayoutParams(params);
				} else {
					v.setY(y_old - (curValue / 100 * v.getHeight()));
					/*
					 * layoutParams.topMargin = (int) ((1-curValue /
					 * 100)*v.getHeight()); v2.setLayoutParams(layoutParams);
					 */
					// v2.setY(y_old - (curValue / 100 * v.getHeight()));
					if (curValue == 100) {
						v.setVisibility(View.GONE);
					}
				}
				if (curValue == 100) {
					isPlaying = false;
				}
			}
		});
		animator.setDuration(400);
		animator.start();
		// animator.setInterpolator(newMyInterploator()); animator.start();
		// ** 设置缩放动画 *//*
		/*
		 * ScaleAnimation animation; if (!up) { v.setVisibility(View.VISIBLE);
		 * animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1f,
		 * Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f);
		 * animation.setDuration(400);// 设置动画持续时间 // ** 常用方法
		 *//*
			 * // animation.setRepeatCount(int repeatCount);//设置重复次数 //
			 * animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态 //
			 * animation.setStartOffset(long startOffset);//执行前的等待时间
			 * v.setAnimation(animation); animation.start(); } else {
			 * v.setVisibility(View.GONE); }
			 */
	}

	/**
	 * dp转为px
	 * 
	 * @param context
	 *            上下文
	 * @param dipValue
	 *            dp值
	 * @return
	 */
	private int dip2px(Context context, float dipValue) {
		Resources r = context.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
	}
}
