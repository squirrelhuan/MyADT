package com.huan.myadt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.huan.myadt.MyApp;
import com.huan.myadt.R;
/*import com.sixth.adwoad.AdListener;
import com.sixth.adwoad.AdwoAdView;
import com.sixth.adwoad.ErrorCode;*/

public class RefrenceWebActivity extends AppCompatActivity /*implements AdListener*/{
	private WebView webView;
	private String title,url;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.learn_web_layout);
		Bundle bundle = getIntent().getExtras();
		title = bundle.getString("Title");
		url = "file:///android_asset/html/"+"MyADT_Refrence"+".html";
		getSupportActionBar().setTitle(title);
		// 加载页面o
		webView = (WebView) findViewById(R.id.wv_learn);
		// 允许JavaScript执行
		webView.getSettings().setJavaScriptEnabled(true);
		// 找到Html文件，也可以用网络上的文件/MyADT/assets/html/JAVA之适配器模式java_my_life博客园.html
		//webView.loadUrl("file:///android_asset/html/JAVA_Adapter.html");
		webView.loadUrl(url);
		// 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
		//webView.addJavascriptInterface(new Contact(), "contact");
		
	}

	@Override
	protected void onResume() {
	/*	AdwoAdView adView=new AdwoAdView(RefrenceWebActivity.this,MyApp.AppId,false,0);
		adView.setListener(this);
		//Context：嵌入广告条的程序组段；  
		//Adwo_PID:您在 Adwo 平台注册程序后提供给你的 32 位字符串；  
		//testMode：默认 false;设置 true 出现测试广告，false 为正式模式，提交安沃审核 和发布市场时请务必设置为 false； 
		// refreshInterval：以秒为单位，0 标示单次请求，最小不能为 30 秒。
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
		RelativeLayout layout_ad = (RelativeLayout) findViewById(R.id.layout_ad);
		layout_ad.addView(adView,params); 
		//注：因为广告条会因广告需求动态变换大小，请不要设置特定宽高，在设定广告 view 和 parentView 的时候请使用 WRAP_CONTENT。
*/
		super.onResume();
	}
	
	private final class Contact {
		// JavaScript调用此方法拨打电话
		public void call(String phone) {
			// startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +
			// phone)));
			Toast.makeText(RefrenceWebActivity.this, phone, Toast.LENGTH_LONG).show();
		}

		// Html调用此方法传递数据
		public void showcontacts() {
			String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
			// 调用JS中的方法
			webView.loadUrl("javascript:show('" + json + "')");
		}

		public void toast(String str) {
			Toast.makeText(RefrenceWebActivity.this, "aaaaaaaaaaaa  --- " + str,
					Toast.LENGTH_LONG).show();
		}
	}
/************************************************************************************************/
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(MyApp.LOG_TAG, "onDestroy");
		//Toast.makeText(RefrenceWebActivity.this, "onDestroy",
		//		Toast.LENGTH_LONG).show();
	}
/*
	@Override
	public void onReceiveAd(Object arg0) {
		Log.e(MyApp.LOG_TAG, "onReceiveAd");
		Toast.makeText(RefrenceWebActivity.this, "onReceiveAd",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onFailedToReceiveAd(View adView, ErrorCode errorCode) {
		Log.e(MyApp.LOG_TAG, "onFailedToReceiveAd");
		Toast.makeText(RefrenceWebActivity.this, "onFailedToReceiveAd",
				Toast.LENGTH_LONG).show();
	}


	@Override
	public void onDismissScreen() {
		Log.e(MyApp.LOG_TAG, "onDismissScreen");
		Toast.makeText(RefrenceWebActivity.this, "onDismissScreen",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onPresentScreen() {
		Log.e(MyApp.LOG_TAG, "onPresentScreen");
		Toast.makeText(RefrenceWebActivity.this, "onPresentScreen",
				Toast.LENGTH_LONG).show();
	}*/
}
