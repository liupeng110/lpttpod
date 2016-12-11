package com.lp.ttpod.app;

import android.app.Application;
import org.xutils.x;
/**
 * 717219917@qq.com
 * 2016/12/4  0:33
 */
public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
         x.Ext.init(this);
         x.Ext.setDebug(true);

    }
}
