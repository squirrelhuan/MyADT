package com.huan.myadt.interfaces;


public interface BaseInterface {

	/**
	 * 显示toast
	 * 
	 * @param str
	 */
	public void showToast(String str);

//	public void showToast(String stringId);

	
	/**
	 * 关闭activity
	 */
	public void finishUI();

	/**
	 * 显示加载进度
	 */
	public void showProgress(String msg);

	public void hideProgress();

//	public void viewClick(int id);


	
}
