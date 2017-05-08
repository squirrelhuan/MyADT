package com.huan.myadt.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.huan.myadt.MyApp;
import com.huan.myadt.R;
import com.huan.myadt.adapter.MySimpleListAdapter;
import com.huan.myadt.bean.CGQ_study.User;
import com.huan.myadt.dao.UserDao;
import com.huan.myadt.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

public class DesignActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Context mContext;

    ListView listView;
    List<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        initView();
    }

    public void initView() {
        mContext = this;

        MySimpleListAdapter adapter;


        items.add("SimpleFactory模式");
        items.add("FactoryMethod模式");
        items.add("AbstractFactory模式");
        items.add("Singleton模式");
        items.add("Builder模式");
        items.add("Prototype模式");
        items.add("Adapter模式");
        items.add("Composite模式");
        items.add("Decorator模式");
        items.add("Proxy模式");
        items.add("Flyweight模式");
        items.add("Facade模式");
        items.add("Bridge模式");
        items.add("Immutable模式");
        items.add("Strategy模式");
        items.add("TemplateMethod模式");
        items.add("Objserver模式");
        items.add("Iterator模式");
        items.add("ChainOfResponsibility模式");
        items.add("Command模式");
        items.add("Memento模式");
        items.add("State模式");
        items.add("Visitor模式");
        items.add("Interpreter模式");
        items.add("Mediator模式");
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new MySimpleListAdapter(this, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Bundle bundle = new Bundle();
        bundle.putString("Title", items.get(arg2));
        IntentUtil.jump(this, LearnWebActivity.class, bundle);
    }
}
