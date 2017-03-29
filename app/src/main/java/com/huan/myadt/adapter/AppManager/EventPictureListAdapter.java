package com.huan.myadt.adapter.AppManager;

import java.util.ArrayList;
import java.util.List;

import com.huan.myadt.R;
import com.huan.myadt.bean.CGQ_app.AppModel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用于显示缩略图的adapter
 * @author CGQ
 *
 */
public class EventPictureListAdapter extends BaseAdapter {

	// 填充数据的list
	private List<AppModel> list_Drawable = new ArrayList<AppModel>();
	// 上下文
	@SuppressWarnings("unused")
	private Context context;
	// 用来导入布局
	private LayoutInflater inflater = null;

	public EventPictureListAdapter(Context context,List<AppModel> list) {
		this.context = context;
		this.list_Drawable = list;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_Drawable.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_Drawable.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView = inflater.inflate(R.layout.item_main_grid, null);
			holder.image = (ImageView) convertView.findViewById(R.id.grid_image);
			holder.name = (TextView) convertView.findViewById(R.id.grid_text);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}

		AppModel model = list_Drawable.get(position);
		holder.image.setImageDrawable(model.getIcon());
		holder.name.setText(model.getName());
		return convertView;
	}

	class ViewHolder {
		ImageView image;
		TextView name;
	}

}
