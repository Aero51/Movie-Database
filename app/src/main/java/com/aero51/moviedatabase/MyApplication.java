package com.aero51.moviedatabase;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
//TODO check for memory leaks with context
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}