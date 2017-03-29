package com.huan.myadt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huan.myadt.MyApp;
import com.huan.myadt.R;
import com.huan.myadt.test.image.ZipImage;


public class LearnWebActivity extends AppCompatActivity /*implements AdListener*/{
	private WebView webView;
	private ImageView iv_test;
	private TextView tv_size;
	private String title,url;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.learn_web_layout);
		Bundle bundle = getIntent().getExtras();
		if(bundle.containsKey("Title")){
			title = bundle.getString("Title");
			url = "file:///android_asset/html/JAVA_"+title.replace("模式", "")+".html";
		}
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
		
		iv_test = (ImageView) findViewById(R.id.iv_test);
		ZipImage zipImage = new ZipImage();

		//Bitmap bitmap = zipImage.imageZoom(this);
		/*Toast.makeText(context, "size:"+bitMap.getRowBytes() * bitMap.getHeight(), Toast.LENGTH_SHORT).show();*/
		//tv_size = (TextView) findViewById(R.id.tv_size);
		//  ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    //    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	    //    byte[] b = baos.toByteArray();
	    //    int length = b.length;
		//tv_size.setText("size:"+length/1024);
		//tv_size.setText(bitmap.gets);
		//Drawable drawable = new BitmapDrawable(bitmap);
		//iv_test.setImageBitmap(bitmap);
		// ImageUtils.savePhoto(bitmap,
		//		 Environment.getExternalStorageDirectory()+"/AppIconChange", "icon");
		// FileSize fileSize = new FileSize();
		 //fileSize.zipFile(new File(Environment.getExternalStorageDirectory()+"/AppIconChange/icon.png"),  Environment.getExternalStorageDirectory()+"/AppIconChange/ic_lanucher.png");
		/*try {
			zipImage.saveFile(bitmap,Environment.getExternalStorageDirectory() + "/icon.png");
			
		} catch (IOException e) {
			Toast.makeText(this, "save fail", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}*/
	}

	@Override
	protected void onResume() {
		/*AdwoAdView adView=new AdwoAdView(LearnWebActivity.this,MyApp.AppId,false,0);
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
			Toast.makeText(LearnWebActivity.this, phone, Toast.LENGTH_LONG).show();
		}

		// Html调用此方法传递数据
		public void showcontacts() {
			String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
			// 调用JS中的方法
			webView.loadUrl("javascript:show('" + json + "')");
		}

		public void toast(String str) {
			Toast.makeText(LearnWebActivity.this, "aaaaaaaaaaaa  --- " + str,
					Toast.LENGTH_LONG).show();
		}
	}
/************************************************************************************************/
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(MyApp.LOG_TAG, "onDestroy");
		//Toast.makeText(LearnWebActivity.this, "onDestroy",
		//		Toast.LENGTH_LONG).show();
	}
	/*
	@Override
	public void onReceiveAd(Object arg0) {
		Log.e(MyApp.LOG_TAG, "onReceiveAd");
		Toast.makeText(LearnWebActivity.this, "onReceiveAd",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onFailedToReceiveAd(View adView, ErrorCode errorCode) {
		Log.e(MyApp.LOG_TAG, "onFailedToReceiveAd");
		Toast.makeText(LearnWebActivity.this, "onFailedToReceiveAd",
				Toast.LENGTH_LONG).show();
	}


	@Override
	public void onDismissScreen() {
		Log.e(MyApp.LOG_TAG, "onDismissScreen");
		Toast.makeText(LearnWebActivity.this, "onDismissScreen",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onPresentScreen() {
		Log.e(MyApp.LOG_TAG, "onPresentScreen");
		Toast.makeText(LearnWebActivity.this, "onPresentScreen",
				Toast.LENGTH_LONG).show();
	}*/
}
