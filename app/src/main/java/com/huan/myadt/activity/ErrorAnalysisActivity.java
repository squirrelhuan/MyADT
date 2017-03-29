package com.huan.myadt.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.huan.myadt.MyApp;
import com.huan.myadt.R;
import com.huan.myadt.adapter.FlowLayout;
import com.huan.myadt.bean.CGQ_exception.ExceptionMoel;
import com.huan.myadt.bean.CGQ_log.LogCate;
import com.huan.myadt.provider.ApplicationProvider;
import com.huan.myadt.provider.ExceptionProvider;
import com.huan.myadt.provider.LogCateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ErrorAnalysisActivity extends AppCompatActivity /*implements AdListener*/{

	private LogCate mLogCate;
	private ImageView iv_icon;
	private TextView tv_appName;
	private TextView tv_versionCode;
	private TextView tv_versionName;
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

	@Override
	protected void onResume() {
		/*AdwoAdView adView=new AdwoAdView(this,MyApp.AppId,false,0);
		adView.setListener(this);
		//Context：嵌入广告条的程序组段；  
		//Adwo_PID:您在 Adwo 平台注册程序后提供给你的 32 位字符串；  
		//testMode：默认 false;设置 true 出现测试广告，false 为正式模式，提交安沃审核 和发布市场时请务必设置为 false； 
		// refreshInterval：以秒为单位，0 标示单次请求，最小不能为 30 秒。
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
		RelativeLayout layout_ad = (RelativeLayout) findViewById(R.id.layout_ad);
		layout_ad.addView(adView,params); 
		//注：因为广告条会因广告需求动态变换大小，请不要设置特定宽高，在设定广告 view 和 parentView 的时候请使用 WRAP_CONTENT。
*/
		super.onResume();
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
		tv_versionCode = (TextView) findViewById(R.id.tv_versionCode);
		tv_versionCode.setText(mLogCate.getVersioncode()
				+ "");
		tv_versionName = (TextView) findViewById(R.id.tv_versionName);
		tv_versionName.setText(mLogCate.getVersionname());
		tv_packageName = (TextView) findViewById(R.id.tv_packageName);
		tv_packageName.setText(ApplicationProvider.getInstance()
				.getAppByName(mLogCate.getPackagename()).getPackageName());
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_content.setText(getClickableSpan(mLogCate.getText()));
		// 此行必须有
		tv_content.setMovementMethod(LinkMovementMethod.getInstance());
		tv_selector = (TextView) findViewById(R.id.tv_selector);
		tv_selector.setText(LogCateProvider.getLevel(mLogCate.getLevel()) + ">"
				+ mLogCate.getTag());
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_time.setText(formatter.format(mLogCate.getTime()));
		tv_issue = (TextView) findViewById(R.id.tv_issue);

		if (exceptions_list.size() > 0) {
			initChildViews();
		}
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
				ErrorAnalysisActivity.this).create();
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
						Toast.makeText(ErrorAnalysisActivity.this, "未知异常",
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

	/*
	 * //取消按钮 Button btnCancel = (Button)
	 * layout.findViewById(R.id.dialog_cancel); btnCancel.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * Toast.makeText(getApplicationContext(), "cancel",
	 * Toast.LENGTH_SHORT).show(); } });
	 */

	/*
	 * //确定按钮 Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
	 * btnOK.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
	 * } });
	 */

	/*
	 * //关闭按钮 ImageButton btnClose = (ImageButton)
	 * layout.findViewById(R.id.dialog_close); btnClose.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { dialog.dismiss(); } });
	 */

	/**
	 * @param text
	 ************************************************************************************************************************/
	private SpannableString getClickableSpan(String text) {

		// 监听器
		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ErrorAnalysisActivity.this, "Click Success",
						Toast.LENGTH_SHORT).show();
			}
		};
		SpannableString spanableInfo = new SpannableString(text);
		try {
			spanableInfo = dealAll(text, listener);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return spanableInfo;
	}

	public SpannableString dealAll(String text, OnClickListener listener)
			throws IOException {

		SpannableString spanableInfo = new SpannableString(text);
		if (!text.contains("Exception")) {
			return spanableInfo;
		}
		BufferedReader in;
		StringReader stringReader = new StringReader(text);
		in = new BufferedReader(stringReader);

		String s;
		int index = 0;
		while ((s = in.readLine()) != null) {
			if (s.contains("Caused by:")) {

				int length = s.length();
				if (length > 0) {
					int start = index; // 超链接起始位置
					int end = index + s.length(); // 超链接结束位置

					// 可以为多部分设置超链接
					spanableInfo.setSpan(new Clickable(listener), start, end,
							Spanned.SPAN_MARK_MARK);
				}
				dealLine(s);
			}
			index += s.length() + 1;
		}
		in.close();
		return spanableInfo;
	}

	public void dealLine(String text) throws IOException {
		String[] words = text.split("\\b");
		List<String> exceptions_str = new ArrayList<String>();
		for (int i = 0; i < exceptions_list.size(); i++) {
			exceptions_str.add(exceptions_list.get(i).getSimpleName());
		}
		for (String w : words) {
			if (w.endsWith("Exception")) {
				if (!exceptions_str.contains(w)) {
					ExceptionMoel exceptionMoel = new ExceptionMoel();
					exceptionMoel.setSimpleName(w);
					if (ExceptionProvider.getInstance().getExceptionMoelByName(
							w) != null) {
						exceptionMoel = ExceptionProvider.getInstance()
								.getExceptionMoelByName(w);
					}
					exceptions_list.add(exceptionMoel);
				}
			}
		}
	}
/*****************************************************************************************/
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(MyApp.LOG_TAG, "onDestroy");
		//Toast.makeText(this, "onDestroy",
		//		Toast.LENGTH_LONG).show();
	}
	
/*	@Override
	public void onReceiveAd(Object arg0) {
		Log.e(MyApp.LOG_TAG, "onReceiveAd");
		Toast.makeText(this, "onReceiveAd",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onFailedToReceiveAd(View adView, ErrorCode errorCode) {
		Log.e(MyApp.LOG_TAG, "onFailedToReceiveAd");
		Toast.makeText(this, "onFailedToReceiveAd",
				Toast.LENGTH_LONG).show();
	}


	@Override
	public void onDismissScreen() {
		Log.e(MyApp.LOG_TAG, "onDismissScreen");
		Toast.makeText(this, "onDismissScreen",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onPresentScreen() {
		Log.e(MyApp.LOG_TAG, "onPresentScreen");
		Toast.makeText(this, "onPresentScreen",
				Toast.LENGTH_LONG).show();
	}*/
}

class Clickable extends ClickableSpan implements View.OnClickListener {
	private final View.OnClickListener mListener;

	public Clickable(View.OnClickListener listener) {
		mListener = listener;
	}

	@Override
	public void onClick(View view) {
		mListener.onClick(view);
	}
}
