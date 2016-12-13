package com.lp.ttpod.app;

import android.app.Application;
import com.lp.ttpod.util.L;
import org.xutils.x;

/** 717219917@qq.com  2016/12/14 0:15 */
public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
         x.Ext.init(this);
         x.Ext.setDebug(true);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    /**退出应用*/
    public static void exit(){
        L.i("进入MyApp.exit()");
    }


}
