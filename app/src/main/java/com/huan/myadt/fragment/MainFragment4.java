package com.huan.myadt.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.huan.myadt.MyApp;
import com.huan.myadt.R;
import com.huan.myadt.activity.MainActivity;
import com.huan.myadt.activity.RefrenceWebActivity;
import com.huan.myadt.adapter.MySimpleListAdapter;
import com.huan.myadt.adapter.ToggleButton;
import com.huan.myadt.adapter.ToggleButton.OnToggleChanged;
import com.huan.myadt.fragment.base.BaseFragment;
import com.huan.myadt.service.MyADTService;
import com.huan.myadt.utils.IntentUtil;

public class MainFragment4 extends BaseFragment implements OnItemClickListener, OnClickListener {

    private ToggleButton toggleButton_showToast;
    private ToggleButton toggleButton_showNotification;
    private ToggleButton toggleButton_enterErrorActivity;
    private ToggleButton toggleButton_button_sys_server;

    MainActivity mainActivity;
    MySimpleListAdapter adapter;
    Context mcContext;

    ListView listView;
    List<String> items = new ArrayList<String>();
    EditText et_serverIP;
    LinearLayout ll_serverip;

    @Override
    public View setContentUI(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_main_4, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void init() {
        mcContext = this.getActivity();
        items.add("高级设置");
        items.add("MyADT使用手册");

        mainActivity = (MainActivity) getActivity();
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new MySimpleListAdapter(getActivity(), items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        ll_serverip = (LinearLayout) findViewById(R.id.ll_serverip);
        et_serverIP = (EditText) findViewById(R.id.et_serverIP);
        et_serverIP.setText(MyApp.getPreferencesService().getValue("PcServerIP", "192.168.0.106"));
        toggleButton_button_sys_server = (ToggleButton) findViewById(R.id.button_sys_server);
        toggleButton_button_sys_server.toggle(MyApp.getPreferencesService().getBoolean("PcServerIPState"));
        toggleButton_button_sys_server.setOnToggleChanged(new OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                MyApp.getPreferencesService().save("PcServerIPState", on);
                MyADTService.setSERVER_ADDRESS(et_serverIP.getText().toString());
                ll_serverip.setVisibility(on ? View.VISIBLE : View.GONE);
                Toast.makeText(mcContext, "log同步到ip: " + et_serverIP.getText() + (on ? " 开启" : " 关闭"), 1000).show();
            }
        });

        toggleButton_showToast = (ToggleButton) findViewById(R.id.button_show_connect_toast);
        toggleButton_showToast.toggle(MyApp.getPreferencesService().getBoolean("ShowConnectToast"));
        toggleButton_showToast.setOnToggleChanged(new OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                MyApp.getPreferencesService().save("ShowConnectToast", on);
                Toast.makeText(mcContext, "app连接提示已 " + (on ? "开启" : "关闭"), 1000).show();
            }
        });

        toggleButton_showNotification = (ToggleButton) findViewById(R.id.button_show_notification);
        toggleButton_showNotification.toggle(MyApp.getPreferencesService().getBoolean("ShowNotification"));
        toggleButton_showNotification.setOnToggleChanged(new OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                MyApp.getPreferencesService().save("ShowNotification", on);
                Toast.makeText(mcContext, "异常通知已 " + (on ? "开启" : "关闭"), 1000).show();
            }
        });

        toggleButton_enterErrorActivity = (ToggleButton) findViewById(R.id.button_enter_error);
        toggleButton_enterErrorActivity.toggle(MyApp.getPreferencesService().getBoolean("AutorEnterErrorActivity"));
        toggleButton_enterErrorActivity.setOnToggleChanged(new OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                MyApp.getPreferencesService().save("AutorEnterErrorActivity", on);
                Toast.makeText(mcContext, "异常自动进入错误详情页面已 " + (on ? "开启" : "关闭"), 1000).show();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Bundle bundle = new Bundle();
        bundle.putString("Title", items.get(arg2));
        IntentUtil.jump(getActivity(), RefrenceWebActivity.class, bundle);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_show_connect_toast:
                //MyADTService.SERVER_ADDRESS = et_serviceId.getText().toString();
                break;
            default:
                break;
        }
    }

}
