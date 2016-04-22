package com.example1.anotherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AnotherAppService extends Service {
    public AnotherAppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("AnotherAppService onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("AnotherAppService onDestroy");
    }
}
