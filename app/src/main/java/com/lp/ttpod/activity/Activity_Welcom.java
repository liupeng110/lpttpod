package com.lp.ttpod.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lp.ttpod.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/** 717219917@qq.com  2016/12/14 0:19 */
@ContentView(R.layout.activity_welcom)//欢迎页
public class Activity_Welcom extends Activity_Base {
   @ViewInject(R.id.welcom_txt) TextView welcom_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Activity_Welcom.this,Activity_Main.class);
                startActivity(intent);
            }
        }, 2000);
    }

    @Event(R.id.welcom_txt)
    private void welcom_txt(View v){
        Intent intent = new Intent(this,Activity_Main.class);
        startActivity(intent);
    }



}
