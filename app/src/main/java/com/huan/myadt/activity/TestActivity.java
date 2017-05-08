package com.huan.myadt.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huan.myadt.MyApp;
import com.huan.myadt.R;
import com.huan.myadt.bean.CGQ_study.User;
import com.huan.myadt.dao.UserDao;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private Context mContext;
    private int index = 0;

    private TextView tv_title;
    private TextView tv_instructions;
    private RadioGroup rg_question_select_group;
    private RadioButton rb_001;
    private TextView tv_next_question,tv_last_question;

    int StudyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        StudyType = bundle.getInt("StudyType");

        initView();
    }

    UserDao mUserDao;
    User mUser;
    private List<User> users;
    private User user_c;
    public void initView() {
        mContext = this;

        mUserDao = MyApp.getInstance().getDaoSession().getUserDao();
        int b = Integer.valueOf((int) (Math.random() * 100));
      //  Toast.makeText(this, "b=" + b, Toast.LENGTH_SHORT).show();
        mUser = new User((long) b, "anye3", "","");
        mUserDao.insert(mUser);//添加一个

        users = mUserDao.loadAll();
        String userName = "";
        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName() + ",";
        }

        user_c = users.get(index);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(userName);
        tv_instructions = (TextView) findViewById(R.id.tv_instructions);
        tv_instructions.setText(user_c.getInstructions());

        rg_question_select_group = (RadioGroup) findViewById(R.id.rg_question_select_group);
        if(StudyType==1){
        rg_question_select_group.setVisibility(View.GONE);}
        rb_001 = (RadioButton) findViewById(R.id.rb_001);

        for (int i = 0; i < 4; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(i + "." + "dd");
            radioButton.setButtonDrawable(null);
            radioButton.setTextColor(rb_001.getTextColors());
            radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rg_question_select_group.addView(radioButton);

        }

        tv_next_question = (TextView)findViewById(R.id.tv_next_question);
        tv_last_question = (TextView)findViewById(R.id.tv_last_question);

        tv_next_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextQuestion();
            }
        });
        tv_last_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotLastQuestion();
            }
        });

        ShowQuestion();
    }

    void ToNextQuestion() {
        users.set(index,user_c);
        index++;
        ShowQuestion();
    }

    void TotLastQuestion() {
        users.set(index,user_c);
        index--;
        ShowQuestion();
    }

    void ShowQuestion(){
        user_c = users.get(index);
        tv_title.setText(index+1 + "."+user_c.getName());
        tv_instructions.setText(user_c.getInstructions());

        rg_question_select_group.removeAllViews();
        for (int i = 0; i < 4; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(i + "." + "dd");
            radioButton.setButtonDrawable(null);
            radioButton.setTextColor(rb_001.getTextColors());
            radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rg_question_select_group.addView(radioButton);

        }

        if(index<=0){
            tv_last_question.setVisibility(View.GONE);
        }else {
            tv_last_question.setVisibility(View.VISIBLE);
        }

        if(index<users.size()-1){
            tv_next_question.setVisibility(View.VISIBLE);
        }else {
            tv_next_question.setVisibility(View.GONE);
        }
    }
}
