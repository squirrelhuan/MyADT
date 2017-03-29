package com.huan.myadt;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.Toast;

import com.huan.myadt.handler.CrashHandler;
import com.huan.myadt.provider.ApplicationProvider;
import com.huan.myadt.service.MyADTService;
import com.huan.myadt.utils.PreferencesService;
import com.huan.myadt.utils.ToastUtils;

public class MyApp extends Application {
	public static String AppId = "a51f0fcbc53b417a8d090da3b29b192c";
	public static String LOG_TAG="CGQ";
	private static ToastUtils toastUtils;
	private CrashHandler crashHandler;
	public static MyApp instance;
	private static PreferencesService preferencesService;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		toastUtils = new ToastUtils(this);
		
		preferencesService = new PreferencesService(getContext());

		ApplicationProvider applicationProvider = ApplicationProvider.getInstance();
		applicationProvider.init(this);
		startService(new Intent(this,MyADTService.class));
		//异常捕获
		//crashHandler = CrashHandler.getInstance();  
	    //crashHandler.init(this.getApplicationContext()); 
	}

	public static MyApp getInstance() {
		return instance;
	}

	public static Context getContext() {
		return instance.getApplicationContext();
	}

	public static Resources getRes() {
		return getContext().getResources();
	}

	public ToastUtils getToastUtils() {
		return toastUtils;
	}
	
	public static PreferencesService getPreferencesService() {
		return preferencesService = new PreferencesService(getContext());
	}

	public void ShowToast(String string) {
		//Toast.makeText(this, "test", 3000).show();
		getToastUtils().showToast(string);
	}
}
