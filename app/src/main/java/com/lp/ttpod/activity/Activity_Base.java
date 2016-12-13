package com.lp.ttpod.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.xutils.x;

/**
 * 717219917@qq.com
 * 2016/12/13  23:40
 */
public class Activity_Base extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         x.view().inject(this);
    }
}
