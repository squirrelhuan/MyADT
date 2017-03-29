package com.huan.myadt.service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.prefs.Preferences;

import com.alibaba.fastjson.JSON;
import com.huan.myadt.MyApp;
import com.huan.myadt.R;
import com.huan.myadt.activity.ErrorAnalysisActivity;
import com.huan.myadt.activity.MainActivity;
import com.huan.myadt.bean.DataEunm;
import com.huan.myadt.bean.DataModel;
import com.huan.myadt.bean.FileModel;
import com.huan.myadt.bean.CGQ_log.LogCate;
import com.huan.myadt.constant.Constants;
import com.huan.myadt.event.LogCateEvent.LogCateManager;
import com.huan.myadt.provider.ApplicationProvider;
import com.huan.myadt.provider.LogCateProvider;
import com.huan.myadt.utils.AutoInstall;
import com.huan.myadt.utils.PreferencesService;
import com.huan.aidl.MyAdtHelper;
import com.huan.bean.MyLogBean;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MyADTService extends Service {
	private IBinder binder = new StudentQueryBinder();
	private static Context mcontext;
	private PreferencesService preferences;

	@Override
	public void onCreate() {
		super.onCreate();
		if (preferences == null) {
			preferences = new PreferencesService(this);
		}
		
		mcontext = getApplicationContext();
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		initSocket();
	}

	@Override
	public IBinder onBind(Intent intent) {
		/*Bundle bundle = intent.getExtras();
		name_app = bundle.getString("AppName");
		if(preferences.getBoolean("ShowConnectToast")){
			Toast.makeText(getApplicationContext(), name_app+" 已连接", 3000).show();
		}*/
		// context.startService(new Intent("com.huan.myadt.log.deal"));
		return binder;
	}

	private final class StudentQueryBinder extends MyAdtHelper.Stub {
		public boolean sendMessage(MyLogBean myLogBean) throws RemoteException {
			send(myLogBean);
			return true;
		}
	}

	private static String SERVER_ADDRESS = "192.168.0.106";
	public static int SERVER_PORT = 6892;

	
	
	public static String getSERVER_ADDRESS() {
		SERVER_ADDRESS = MyApp.getPreferencesService().getValue("PcServerIP", "192.168.0.106");
		return SERVER_ADDRESS;
	}

	public static void setSERVER_ADDRESS(String sERVER_ADDRESS) {
		MyApp.getPreferencesService().save("PcServerIP", sERVER_ADDRESS);
		SERVER_ADDRESS = sERVER_ADDRESS;
	}

	private boolean send(MyLogBean myLogBean) {
		// String json_str = JSON.toJSONString(myLogBean);
		sendOneData(JSON.toJSONString(myLogBean));
		LogCate logCate = new LogCate();
		logCate.setPackagename(myLogBean.getPackagename());
		logCate.setVersioncode(myLogBean.getVersioncode());
		logCate.setVersionname(myLogBean.getVersionname());
		logCate.setLevel(myLogBean.getLevel());
		logCate.setTime(new Date());
		logCate.setTag(myLogBean.getTag());
		logCate.setText(myLogBean.getContent());
		LogCateProvider.getInstance().getLogs().add(logCate);
		boolean insertSuccess = ApplicationProvider.getInstance().addAppByName(myLogBean.getPackagename());
		/** app连接提示 */
		if(insertSuccess&&MyApp.instance.getPreferencesService().getBoolean("ShowConnectToast")){
			ShowToast(ApplicationProvider.getInstance().getPackageInfo(myLogBean.getPackagename()).getName()+"- 已连接 -MyAdt");
		}
		/** 收到异常通知 */
		if(logCate.getLevel()==LogCateProvider.LEVEL_Log.Error&&MyApp.instance.getPreferencesService().getBoolean("ShowNotification")){
			showCzNotify(logCate);
		}
		if (logCate.getLevel() == LogCateProvider.LEVEL_Log.Error&&MyApp.instance.getPreferencesService().getBoolean("AutorEnterErrorActivity")) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setAction(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			startActivity(intent);
		}
		return true;
	}
	public Handler CDSQ = new Handler();
	
	private void ShowToast(String string) {
		MyRunnable Crunnable = new MyRunnable(string);
		CDSQ.post(Crunnable);
	}

	public class MyRunnable implements Runnable {
		String str = null;
		
		MyRunnable(String str){
			this.str = str;
		}
		public void run() {
			Toast.makeText(getApplicationContext(), this.str,3000).show();
		}
	}
	
	static int notifyId = 100;
	public static NotificationManager mNotificationManager;
	@SuppressLint("NewApi")
	public static void showCzNotify(LogCate logCate) {
		// Notification mNotification = new
		// Notification();//为了兼容问题，不用该方法，所以都采用BUILD方式建立
		// Notification mNotification = new
		// Notification.Builder(this).getNotification();//这种方式已经过时
		notifyId = (int) (Math.random() * 1000);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mcontext);
		// //PendingIntent 跳转动作
		Intent intent = new Intent(mcontext, ErrorAnalysisActivity.class);

		// intent.setClass(mcontext.getApplicationContext(),
		// LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle bundle = new Bundle();
		bundle.putSerializable("LogCate", logCate);
		intent.putExtras(bundle);
		PendingIntent pendingIntent = PendingIntent.getActivity(mcontext, 0, intent, 0);
		mBuilder.setSmallIcon(ApplicationProvider.getInstance().getPackageInfo(logCate.getPackagename()).getIcon().getAlpha())
		.setTicker(ApplicationProvider.getInstance().getPackageInfo(logCate.getPackagename()).getName())
				.setContentTitle(logCate.getTag())
				.setContentText(logCate.getText())
				.setContentIntent(pendingIntent);
		Notification mNotification = mBuilder.build();
		// 设置通知 消息 图标
		mNotification.icon = R.drawable.ic_launcher;
		// 在通知栏上点击此通知后自动清除此通知֪
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;// FLAG_ONGOING_EVENT
																// FLAG_AUTO_CANCEL
		// 设置显示通知时的默认的发声、震动、Light效果
		mNotification.defaults = Notification.DEFAULT_ALL;
		// 设置发出消息的内容
		mNotification.tickerText = "ͨmessage";
		// 设置发出通知的时间
		mNotification.when = System.currentTimeMillis();
		mNotification.flags = Notification.FLAG_AUTO_CANCEL; // 在通知栏上点击此通知后自动清除此通知
		// mNotification.setLatestEventInfo(this, "常驻测试",
		// "使用cancel()方法才可以把我去掉哦", null); //设置详细的信息 ,这个方法现在已经不用了
		mNotificationManager.notify(notifyId, mNotification);
	}
	
	public static void sendOneData(final String bb) {
		if(!MyApp.getPreferencesService().getBoolean("PcServerIPState")){
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (socket == null) {
						return;
					}
					InetAddress serverAddress = InetAddress.getByName(getSERVER_ADDRESS());
					byte send_data[] = bb.getBytes();
					DatagramPacket send_packet = new DatagramPacket(send_data, send_data.length, serverAddress,
							SERVER_PORT);
					socket.send(send_packet);
					// Log.d("dhh", "socket单次发送被运行" + send_packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (socket != null) {
			socket.disconnect();
			socket.close();
			socket = null;
		}
	}

	private static DatagramSocket socket = null;
	private DatagramPacket receive_packet;
	private DatagramPacket send_packet;
	public static final int LOCAL_PORT = 3149;

	private void initSocket() {
		try {
			if (socket == null) {
				socket = new DatagramSocket(LOCAL_PORT);
				//Toast.makeText(getApplicationContext(), "newSocket...", 3000).show();
			} else {
				//Toast.makeText(getApplicationContext(), "initSocket...", 3000).show();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		// 不停地接收内容
		Executors.newCachedThreadPool().execute(new ReceiveDataRunnable());

		// 通知上线
		// 发送一条数据告诉服务器我上线了
		// sendData(a);
		// 上线通知代替心跳，3秒一次上线通知
		// Executors.newCachedThreadPool().execute(new SendheartbeatRunnable());
		// sendData();
		// 2秒自动发送一次获取机器列表信息
		// Executors.newCachedThreadPool().execute(new SendHeartRunnable());
	}

	public static String result;

	// 接收所有通信返回的数据
	private class ReceiveDataRunnable implements Runnable {
		@Override
		public void run() {

			byte data1[] = new byte[1024];
			// 参数一:要接受的data 参数二：data的长度
			DatagramPacket packet1 = new DatagramPacket(data1, data1.length);
			// 把接收到的data转换为String字符串
			while (true) {
				try {
					socket.receive(packet1);
					result = new String(packet1.getData(), packet1.getOffset(), packet1.getLength(), "UTF-8");
					Log.i("CGQ", "recieve success:" + result);
					DataModel dataModel = JSON.parseObject(result, DataModel.class);
					Log.i("CGQ", "result:" + result);
					// DataModel dataModel = JSON.toJavaObject(result_json,
					// DataModel.class);
					dealRecived(dataModel);

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void installApk(String filePath) {
		/*
		 * Environment.getExternalStorageDirectory() +
		 * "/Download/paopaomusic.apk"
		 */
		AutoInstall.setUrl(filePath);
		AutoInstall.install(getApplicationContext());
	}

	/**
	 * 处理接收到的数据
	 * 
	 * @param dataModel
	 * @throws IOException
	 */
	private void dealRecived(DataModel dataModel) throws IOException {

		Log.i("CGQ", "to json success............................................................");
		FileModel fileModel = JSON.parseObject(dataModel.getData().toString(), FileModel.class);
		switch (dataModel.getType()) {
		case DataEunm.File:
			gogogo(fileModel.getPath());
			// downFile(fileModel.getPath(), fileModel.getSize());
			break;

		default:
			break;
		}
	}

	Tcp_Thread thread_tcp;

	/**
	 * @param string
	 **************************************************************************/
	public void gogogo(String filename) {
		if (thread_tcp == null) {
			try {
				thread_tcp = new Tcp_Thread(filename);
				thread_tcp.run(); // 启动线程运行
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class Tcp_Thread extends Thread {
		String filename;

		Tcp_Thread(String filename) {
			this.filename = filename;
		}

		@Override
		public void run() {

			ServerSocket server = null;
			try {
				server = new ServerSocket(48127);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (true) {
				try {
					System.out.println("开始监听...");
					/*
					 * 如果没有访问它会自动等待
					 */
					Socket socket = server.accept();
					System.out.println("有链接");
					receiveFile(socket, this.filename);
				} catch (Exception e) {
					System.out.println("服务器异常");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 接收文件方法
	 * 
	 * @param socket
	 * @param filename
	 * @throws IOException
	 */
	public void receiveFile(Socket socket, String filename) throws IOException {
		byte[] inputByte = null;
		int length = 0;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		String filePath = Constants.BASE_FILE_PATH + new Date().toString() + new Random().nextInt(100) + filename;
		try {
			try {
				dis = new DataInputStream(socket.getInputStream());
				File f = new File(Constants.BASE_FILE_PATH);
				if (!f.exists()) {
					f.mkdir();
				}
				/*
				 * 文件存储位置
				 */
				fos = new FileOutputStream(new File(filePath));
				inputByte = new byte[1024 * 10];
				System.out.println("开始接收数据...");
				while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
					fos.write(inputByte, 0, length);
					fos.flush();
				}
				System.out.println("完成接收：" + filePath);

				// installApk(filePath);
				// 在外部Activity调用者看来，不需要在意太多内部实现，只需要传入一个url跟一个context即可
				AutoInstall.setUrl(filePath);
				AutoInstall.install(mcontext);
			} finally {
				if (fos != null)
					fos.close();
				if (dis != null)
					dis.close();
				if (socket != null)
					socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}