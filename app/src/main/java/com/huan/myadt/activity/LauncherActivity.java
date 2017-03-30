package com.huan.myadt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.huan.myadt.R;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class LauncherActivity extends Activity{

    public final int MSG_FINISH_LAUNCHERACTIVITY = 500;

    public Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FINISH_LAUNCHERACTIVITY:
                    //跳转到MainActivity，并结束当前的LauncherActivity
                    Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                default:
                    break;
            }
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 不显示系统的标题栏，保证windowBackground和界面activity_main的大小一样，显示在屏幕不会有错位（去掉这一行试试就知道效果了）
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 注意：添加3秒睡眠，以确保黑屏一会儿的效果明显，在项目应用要去掉这3秒睡眠
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        setContentView(R.layout.activity_launcher);
        // 停留3秒后发送消息，跳转到MainActivity
        mHandler.sendEmptyMessageDelayed(MSG_FINISH_LAUNCHERACTIVITY, 800);
    }
}
