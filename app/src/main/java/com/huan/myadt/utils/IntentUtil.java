package com.huan.myadt.utils;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;

/**
 * 页面跳转工具
 */
public class IntentUtil {

	/**
	 * 
	 * @param context
	 * @param dest
	 *            目标activity
	 * @param bundle
	 *            携带的数据
	 */
	public static void jump(Activity source, Class<? extends Activity> dest, Bundle bundle) {
		Log.d("intent", "*****\nform: " + source.getClass().getName() + "\njumpTo: " + dest.getCanonicalName());
		Intent intent = null;
		intent = new Intent(source, dest);
		if (bundle != null) {
			intent.putExtras(bundle);

			Map<String, Object> map = new HashMap<String, Object>();
			for (String a : bundle.keySet()) {
				Object object = bundle.get(a);
				map.put(a, object);
			}
			Log.d("intent", "bundle= " + JSON.toJSONString(map));
		}
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		source.startActivity(intent);
	}

	public static void jumpForResult(Activity source, Class<? extends Activity> dest, Bundle bundle, int requestCode) {
		Log.d("intent", "*****\nform: " + source.getClass().getName() + "\njumpForResultTo: " + dest.getCanonicalName());
		Intent intent = null;
		intent = new Intent(source, dest);
		if (bundle != null) {
			intent.putExtras(bundle);

			Map<String, Object> map = new HashMap<String, Object>();
			for (String a : bundle.keySet()) {
				Object object = bundle.get(a);
				map.put(a, object);
			}
			Log.d("intent", "bundle= " + JSON.toJSONString(map));
		}
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		source.startActivityForResult(intent, requestCode);

	}
}
