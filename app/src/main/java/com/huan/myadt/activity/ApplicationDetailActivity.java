package com.huan.myadt.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.huan.myadt.R;
import com.huan.myadt.adapter.FlowLayout;
import com.huan.myadt.bean.CGQ_exception.ExceptionMoel;
import com.huan.myadt.bean.CGQ_log.LogCate;
import com.huan.myadt.provider.ApplicationProvider;
import com.huan.myadt.provider.ExceptionProvider;
import com.huan.myadt.provider.LogCateProvider;

public class ApplicationDetailActivity extends AppCompatActivity {

	private LogCate mLogCate;
	private ImageView iv_icon;
	private TextView tv_appName;
	private TextView tv_versionCode;
	private TextView tv_packageName;
	private TextView tv_content;
	private TextView tv_selector;
	private TextView tv_time;
	private TextView tv_issue;
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	private List<ExceptionMoel> exceptions_list = new ArrayList<ExceptionMoel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_error);
		init();
	}

	private void init() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey("LogCate")) {
			mLogCate = (LogCate) bundle.getSerializable("LogCate");
		}
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		iv_icon.setImageDrawable(ApplicationProvider.getInstance()
				.getAppByName(mLogCate.getPackagename()).getIcon());
		tv_appName = (TextView) findViewById(R.id.tv_appName);
		tv_appName.setText(ApplicationProvider.getInstance()
				.getAppByName(mLogCate.getPackagename()).getName());
		
	}

	private void initDialog(final ExceptionMoel exceptionMoel) {
		LayoutInflater inflaterDl = LayoutInflater.from(this);
		LinearLayout layout = (LinearLayout) inflaterDl.inflate(
				R.layout.dialog_error, null);
		
		TextView tv_dialog_name = (TextView) layout.findViewById(R.id.tv_dialog_name); 
		tv_dialog_name.setText(exceptionMoel.getSimpleName());
		final TextView tv_dialog_description = (TextView) layout.findViewById(R.id.tv_dialog_description); 
		tv_dialog_description.setText(exceptionMoel.getDescription());
		RadioGroup main_radio_group;
		main_radio_group = (RadioGroup) layout.findViewById(R.id.dialog_radio_group);
		main_radio_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.radio_button_issue:
					tv_dialog_description.setText(exceptionMoel.getDescription());
					break;
				case R.id.radio_button_suggestion:
					tv_dialog_description.setText(exceptionMoel.getSuggestion());
					break;
				default:
					break;
				}
			}
		});
		// 对话框
		Dialog dialog = new AlertDialog.Builder(
				ApplicationDetailActivity.this).create();
		dialog.show();
		dialog.getWindow().setContentView(layout);
	}

	FlowLayout mFlowLayout;

	private void initChildViews() {
		// TODO Auto-generated method stub
		mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);
		MarginLayoutParams lp = new MarginLayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 5;
		lp.rightMargin = 5;
		lp.topMargin = 5;
		lp.bottomMargin = 5;
		for (int i = 0; i < exceptions_list.size(); i++) {
			TextView view = new TextView(this);
			view.setPadding(5, 0, 5, 0);
			view.setText(exceptions_list.get(i).getSimpleName());
			// exceptions_list.get(i).getSimpleName()
			view.setTextColor(Color.WHITE);
			if (exceptions_list.get(i).getDescription() != null) {
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.tv_bg_stroke_red));
			} else {
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.tv_bg_stroke_gray));
			}
			MyOnClickListener mClickListener = new MyOnClickListener() {
				int index = 0;

				@Override
				public void onClick(View v) {
					if (exceptions_list.get(index).getDescription() != null) {
						initDialog(exceptions_list.get(index));
					} else {
						Toast.makeText(ApplicationDetailActivity.this, "未知异常",
								Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void setIndex(int i) {
					// TODO Auto-generated method stub
					index = i;
				}
			};
			mClickListener.setIndex(i);
			view.setOnClickListener(mClickListener);
			mFlowLayout.addView(view, lp);
		}
	}

	public interface MyOnClickListener extends OnClickListener {
		void setIndex(int i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_common, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

