package com.huan.myadt.bean.CGQ_app;

import android.graphics.drawable.Drawable;

public class AppModel {

	private String name;// 当前版本的包名
	private String packageName;
	private Drawable icon;
	// 当前应用的版本名称
	private String versionName;
	// 当前版本的版本号
	private int versionCode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

}
