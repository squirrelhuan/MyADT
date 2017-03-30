package com.huan.myadt;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import com.huan.myadt.bean.CGQ_study.Question;
import com.huan.myadt.dao.DaoMaster;
import com.huan.myadt.dao.DaoSession;
import com.huan.myadt.dao.GreenDaoContextWrapper;
import com.huan.myadt.dao.QuestionDao;
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

	private DaoMaster.DevOpenHelper mHelper;
	private SQLiteDatabase db;
	private DaoMaster mDaoMaster;
	private DaoSession mDaoSession;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		toastUtils = new ToastUtils(this);
		setDatabase();
		preferencesService = new PreferencesService(getContext());

		ApplicationProvider applicationProvider = ApplicationProvider.getInstance();
		applicationProvider.init(this);
		startService(new Intent(this,MyADTService.class));
		//异常捕获
		crashHandler = CrashHandler.getInstance();
	    crashHandler.init(this.getApplicationContext());

		/** test */
		QuestionDao mquestionDao = getDaoSession().getQuestionDao();
		Question mquestion = new Question(System.currentTimeMillis(),"anye3");
		mquestionDao.insert(mquestion);//添加一个
		System.out.println("ok");
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

	/**
	 * 设置greenDao
	 */
	private void setDatabase() {


		//创建数据库  GreenDao的使用
		mHelper = new
				DaoMaster.DevOpenHelper(new GreenDaoContextWrapper(getApplicationContext()), "myadt.db", null);
		mDaoMaster = new DaoMaster(mHelper.getWritableDb());
		mDaoSession = mDaoMaster.newSession();
		//获取userdao,进行增删改查
		//QuestionDao questionDao = mDaoSession.getQuestionDao();
	}

	public DaoSession getDaoSession() {
		return mDaoSession;
	}
	public SQLiteDatabase getDb() {
		return db;
	}
}
