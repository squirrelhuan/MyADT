package com.huan.myadt.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.huan.myadt.R;
import com.huan.myadt.activity.ErrorAnalysisActivity;
import com.huan.myadt.activity.MainActivity;
import com.huan.myadt.bean.CGQ_log.LogCate;
import com.huan.myadt.provider.LogCateProvider;
import com.huan.myadt.utils.IntentUtil;

/**
 * 
 * @author CGQ
 * @Time 2016.11.29
 */
public class MyLogListAdapter extends BaseAdapter {

	ArrayList<LogCate> mList;
	Context mContext;
	LayoutInflater mInflater;
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public MyLogListAdapter(Context mContext, ArrayList<LogCate> mList) {
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_my_log_list, null);

			viewHolder.tv_level = (TextView) convertView
					.findViewById(com.huan.myadt.R.id.tv_level);
			viewHolder.tv_tag = (TextView) convertView
					.findViewById(R.id.tv_tag);
			viewHolder.tv_application = (TextView) convertView
					.findViewById(R.id.tv_application);
			viewHolder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			viewHolder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		LogCate mLog = mList.get(position);
		viewHolder.tv_level.setText(LogCateProvider.getLevel(mLog.getLevel()));
		setLevelColor(viewHolder.tv_level, mLog.getLevel());

		viewHolder.tv_application.setText(mLog.getPackagename());
		viewHolder.tv_tag.setText(mLog.getTag());
		viewHolder.tv_content.setText(mLog.getText());
		viewHolder.tv_time.setText(formatter.format(mLog.getTime()));
		String content_str = mLog.getText();
		String content_new = null;
		if (content_str.contains("\n")) {
			String[] lines = content_str.split("\n");
			for (String line : lines) {
				if (line.startsWith("Caused by:")) {
					content_new += "<a href=\\\"http://" +""
							+ "\\\">" +line+"</>" + "\n";
				} else {
					content_new += line + "\n";
				}
			}
			viewHolder.tv_content.setText(getClickableHtml(content_new));
		} else {
			viewHolder.tv_content.setText(content_str);
		}

		// textview1
		String html = "<font color=\"red\">I love android</font><br>";
		html += "<font color=\"#0000FF\"><big><i>I love android</i></big></font><br><p>";
		html += "<big>百度</big>";

		CharSequence charSequence = Html.fromHtml(html);
		//viewHolder.tv_content.setText(charSequence);
		// viewHolder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());//点击时产生超链接。

		
		 String url =
		  "This is a page with lots of URLs. <a href=\\\"http://jb51.net\\\">jb51.net</> "
		  +"This left is a very good blog" ; url += "you want in that blog." +
		 "The Next Link is <a href=\\\"http://www.google.com.hk\\\">Google HK</a>";
		

		return convertView;
	}

	private void setLevelColor(TextView tv_level, int i) {
		switch (i) {
		case LogCateProvider.LEVEL_Log.Debug:
			tv_level.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.tv_bg_stroke_blue));
			break;
		case LogCateProvider.LEVEL_Log.Error:
			tv_level.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.tv_bg_stroke_red));
			break;
		case LogCateProvider.LEVEL_Log.Assert:
			tv_level.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.tv_bg_stroke_red));
			break;
		case LogCateProvider.LEVEL_Log.Warn:
			tv_level.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.tv_bg_stroke_orange));
			break;
		case LogCateProvider.LEVEL_Log.Info:
			tv_level.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.tv_bg_stroke_green));
			break;
		case LogCateProvider.LEVEL_Log.Verbose:
			tv_level.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.tv_bg_stroke_violet));
			break;
		default:
			tv_level.setBackgroundDrawable(mContext.getResources().getDrawable(
					R.drawable.tv_bg_stroke_violet));
			break;
		}
	}

	class ViewHolder {
		TextView tv_level, tv_tag, tv_application, tv_content,tv_time;
		//TextViewFixTouchConsume tv_content;
	}

	private void setLinkClickable(
			final SpannableStringBuilder clickableHtmlBuilder,
			final URLSpan urlSpan) {
		int start = clickableHtmlBuilder.getSpanStart(urlSpan);
		int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
		int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);
		ClickableSpan clickableSpan = new ClickableSpan() {
			public void onClick(View view) {
				// Do something with URL here.
				Intent mIntent = new Intent();
				IntentUtil.jump((Activity) mContext,
						ErrorAnalysisActivity.class, null);
				// Toast.makeText(this, "服务已断开！", Toast.LENGTH_SHORT).show();
			}
		};
		clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
	}

	private CharSequence getClickableHtml(String html) {
		Spanned spannedHtml = Html.fromHtml(html);
		SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(
				spannedHtml);
		URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(),
				URLSpan.class);
		for (final URLSpan span : urls) {
			if(urls.toString().equals("Caused by:")){
				setLinkClickable(clickableHtmlBuilder, span);
			}
			//setLinkClickable(clickableHtmlBuilder, span);
		}
		return clickableHtmlBuilder;
	}
}
