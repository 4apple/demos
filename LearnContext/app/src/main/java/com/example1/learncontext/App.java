package com.example1.learncontext;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by user on 3/28/16.
 */
public class App extends Application {
    private String textData = "default";

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public String getTextData() {
        return textData;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("App onCreate");
        //add init code before activity here
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
