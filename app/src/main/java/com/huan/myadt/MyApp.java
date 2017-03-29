package com.huan.myadt;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import com.huan.myadt.bean.CGQ_study.Question;
import com.huan.myadt.dao.DaoMaster;
import com.huan.myadt.dao.DaoSession;
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
		Question mquestion = new Question((long)((int)Math.random()*1000),"anye3");
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
		// 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
		// 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
		// 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
		// 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
		mHelper = new DaoMaster.DevOpenHelper(this, "myadt.db", null);
		//mHelper = new DaoMaster.DevOpenHelper(new GreenDaoContextWrapper(this), "myadt.db", null);

		db = mHelper.getWritableDatabase();
		// 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
		mDaoMaster = new DaoMaster(db);
		mDaoSession = mDaoMaster.newSession();
	}

	public DaoSession getDaoSession() {
		return mDaoSession;
	}
	public SQLiteDatabase getDb() {
		return db;
	}
}
