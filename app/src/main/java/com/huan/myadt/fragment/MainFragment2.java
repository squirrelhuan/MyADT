package com.huan.myadt.fragment;

import java.util.ArrayList;
import java.util.List;

import com.huan.myadt.R;
import com.huan.myadt.activity.MainActivity;
import com.huan.myadt.adapter.AppManager.EventPictureListAdapter;
import com.huan.myadt.bean.CGQ_app.AppModel;
import com.huan.myadt.fragment.base.BaseFragment;
import com.huan.myadt.provider.ApplicationProvider;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainFragment2 extends BaseFragment {

	MainActivity mainActivity;

	// 照片缩略图画廊
	GridView gridview_picture;
	List<AppModel> list_AppModel = new ArrayList<AppModel>();
	EventPictureListAdapter adapter_picture;

	@Override
	public View setContentUI(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_main_2, container, false);
	}
	@Override
	public void onResume() {
		list_AppModel.clear();
		for (AppModel appModel : ApplicationProvider.getInstance().getApps()) {
			list_AppModel.add(appModel);
		}
		//list_AppModel.add(getPackageInfo("com.example.ppmusic"));
		adapter_picture.notifyDataSetChanged();
		super.onResume();
	}
	@Override
	public void init() {
		mainActivity = (MainActivity) getActivity();

		/******* 照片缩略图显示 *****************************************************************************************/
		gridview_picture = (GridView) findViewById(R.id.gridview_picture);
		//list_AppModel
		//		.add(getResources().getDrawable(R.drawable.ic_launcher));
		// 新建适配器
		adapter_picture = new EventPictureListAdapter(this.getActivity(),
				list_AppModel);
		// 配置适配器
		gridview_picture.setAdapter(adapter_picture);
		// 拍照点击事件
		gridview_picture.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				
			}});

	}

	// this.getActivity().getPackageName()
	public AppModel getPackageInfo(String packageName) {
		PackageInfo info;
		AppModel appModel = new AppModel();
		try {
			 PackageManager pm = getActivity().getApplication().getPackageManager(); 
			info = pm.getPackageInfo(
					packageName, 0);
			appModel.setName(info.applicationInfo.loadLabel(pm).toString());
			// 当前应用的版本名称
			appModel.setVersionName(info.versionName);
			// 当前版本的版本号
			appModel.setVersionCode(info.versionCode);
			// 当前版本的包名
			appModel.setPackageName(info.packageName);
			//info.applicationInfo.icon;

			appModel.setIcon(info.applicationInfo.loadIcon(pm)); 
            return appModel;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return appModel;
	}
	public void clearApps() {
		ApplicationProvider.getInstance().clear();
		list_AppModel.clear();
		adapter_picture.notifyDataSetChanged();
	}
}
