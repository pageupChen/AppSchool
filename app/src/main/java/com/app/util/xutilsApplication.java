package com.app.util;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2017/10/27.
 */

public class xutilsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
