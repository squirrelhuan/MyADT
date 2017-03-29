package com.huan.myadt.provider;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.Toast;

import com.huan.myadt.MyApp;
import com.huan.myadt.bean.CGQ_app.AppModel;
import com.huan.myadt.bean.CGQ_log.LogCate;
import com.huan.myadt.bean.CGQ_log.LogCate_Tag;
import com.huan.myadt.event.LogCateEvent.LogCateManager;

/**
 * logcate内容提供者
 * 
 * @author CGQ
 *
 */
public class ApplicationProvider {
	private static List<AppModel> apps = new ArrayList<AppModel>();
	private static List<String> app_names = new ArrayList<String>();
	static ApplicationProvider logProvider = new ApplicationProvider();
	private static Context mContext;

	public static ApplicationProvider getInstance() {
		return logProvider;
	}

	private ApplicationProvider() {

	}

	public void init(Context context) {
		mContext = context;
	}

	public List<AppModel> getApps() {
		return apps;
	}

	public boolean addAppByName(String appname) {
		boolean insertSuccess = false;
		boolean isExit = false;
		for (String name : app_names) {
			if (name.trim().equals(appname.trim())) {
				isExit = true;
				insertSuccess = false;
			}
		}
		if (!isExit) {
			app_names.add(appname);
			apps.add(getPackageInfo(appname));
			insertSuccess = true;
		}
		return insertSuccess;
	}

	public AppModel getAppByName(String appname) {
		for (AppModel app : apps) {
			if (appname.trim().equals(app.getPackageName().trim())) {
				return app;
			}
		}
		return null;
	}

	// this.getActivity().getPackageName()
	public AppModel getPackageInfo(String packageName) {
		PackageInfo info;
		AppModel appModel = new AppModel();
		try {
			PackageManager pm = mContext.getPackageManager();
			info = pm.getPackageInfo(packageName, 0);
			appModel.setName(info.applicationInfo.loadLabel(pm).toString());
			// 当前应用的版本名称
			appModel.setVersionName(info.versionName);
			// 当前版本的版本号
			appModel.setVersionCode(info.versionCode);
			// 当前版本的包名
			appModel.setPackageName(info.packageName);
			// info.applicationInfo.icon;

			appModel.setIcon(info.applicationInfo.loadIcon(pm));
			return appModel;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return appModel;
	}

	public void addApp(AppModel app) {

		LogCateManager.getInstance().onTagsChanged();
	}

	public static class LogList extends ArrayList<LogCate> {

		@Override
		public void add(int index, LogCate element) {
			// TODO Auto-generated method stub
			super.add(index, element);
			LogCateManager.getInstance().onLogsChanged();
		}

		@Override
		public boolean add(LogCate e) {
			// TODO Auto-generated method stub
			super.add(e);
			LogCateManager.getInstance().onLogsChanged();
			return true;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			super.clear();
			LogCateManager.getInstance().onLogsChanged();
		}

	}

	public interface LEVEL {
		int verbose = 1;
		int debug = 2;
		int info = 3;
		int warn = 4;
		int error = 5;
		int exception = 6;
	}

	public static String getLevel(int i) {
		String str = null;
		switch (i) {

		case 1:
			str = "verbose";
			break;
		case 2:
			str = "debug";
			break;
		case 3:
			str = "info";
			break;
		case 4:
			str = "warn";
			break;
		case 5:
			str = "error";
			break;
		case 6:
			str = "exception";
			break;
		default:
			str = "unknow";
			break;
		}
		return str;
	}

	public static int getLevel(String name) {
		int level = 0;
		switch (name) {
		case "all":
			level = 0;
		case "verbose":
			level = 1;
			break;
		case "debug":
			level = 2;
			break;
		case "info":
			level = 3;
			break;
		case "warn":
			level = 4;
			break;
		case "error":
			level = 5;
			break;
		case "exception":
			level = 6;
			break;
		default:
			level = 0;
			break;
		}
		return level;
	}

	public void clear() {
		apps.clear();
		app_names.clear();
	}

}
