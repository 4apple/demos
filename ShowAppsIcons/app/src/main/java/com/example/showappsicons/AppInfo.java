package com.example.showappsicons;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by user on 4/14/16.
 */
public class AppInfo {
    public String appName="";
    public String packageName="";
    public String versionName="";
    public String className="";
    public int versionCode=0;
    public Drawable appIcon=null;
    public int usage=0;
    public void print()
    {
        Log.v("app", "Name:" + appName + " Package:" + packageName);
        Log.v("app","Name:"+appName+" versionName:"+versionName);
        Log.v("app","Name:"+appName+" versionCode:"+versionCode);
    }
}
