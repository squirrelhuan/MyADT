package com.huan.myadt.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
public class MySimpleListAdapter extends BaseAdapter {

	ArrayList<String> mList;
	Context mContext;
	LayoutInflater mInflater;
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public MySimpleListAdapter(Context mContext, List<String> mList) {
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.mList = (ArrayList<String>) mList;
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
			convertView = mInflater.inflate(R.layout.item_simple_list, null);

			viewHolder.tv_title = (TextView) convertView
					.findViewById(com.huan.myadt.R.id.tv_title);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String mLog = mList.get(position);
		viewHolder.tv_title.setText(mLog);
		
		return convertView;
	}


	class ViewHolder {
		TextView tv_title;
	}

}
