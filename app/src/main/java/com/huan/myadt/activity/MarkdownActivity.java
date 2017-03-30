package com.huan.myadt.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.huan.myadt.R;
import com.zzhoujay.markdown.MarkDown;

import java.io.InputStream;

public class MarkdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown);
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

        final TextView textView = (TextView) findViewById(R.id.textView);
        assert textView != null;
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        final InputStream stream = getResources().openRawResource(R.raw.hello);

        textView.post(new Runnable() {
            @Override
            public void run() {
                long time = System.nanoTime();
                Spanned spanned = MarkDown.fromMarkdown(stream, new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable = new ColorDrawable(Color.LTGRAY);
                        drawable.setBounds(0, 0, textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight(), 400);
                        return drawable;
                    }
                }, textView);
                long useTime = System.nanoTime() - time;
                Toast.makeText(getApplicationContext(), "use time:" + useTime, Toast.LENGTH_LONG).show();
                textView.setText(spanned);
            }
        });
    }

}
