package com.huan.myadt.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * <pre>
 * 业务名:
 * 功能说明:用户数据操作类
 * 编写日期:	
 * 作者: 李经方
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
/** 进度 ：功能待添加 */
public class DaoUtils {

	private static String Tag_Data = "DATA";
	/**
	 * 获取本地存储数据
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context,String key) {
		SharedPreferences sharedPreferences= context.getSharedPreferences(Tag_Data,Activity.MODE_PRIVATE); 
		String value = sharedPreferences.getString(key,null);
		return value;
	}
	
	/**
	 * 保存数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveString(Context context, String key,String value) {
		
		SharedPreferences pref = context.getSharedPreferences(Tag_Data,Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = pref.edit(); 
		editor.putString(key, value);
		editor.commit();
	}

	
}
