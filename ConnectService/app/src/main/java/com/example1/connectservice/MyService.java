package com.example1.connectservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telecom.Call;

public class MyService extends Service {
    private String data = "this is the default data";
    public MyService() {
    }
    boolean running =false;
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public class Binder extends android.os.Binder{
        public void setData(String data){
            MyService.this.data = data;
        }
        public void setCallback(CallBack callback){
            MyService.this.setCallback(callback);
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        running = true;
        new Thread(){
            @Override
            public void run() {

                super.run();
                int i = 0;
                while(running){
                    i++;
                    String str = i+":" + data;
                    System.out.println(str);
                    if(callback != null){
                        callback.onDataChange(str);
                    }
                    try{
                        sleep(1000);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }

    private CallBack callback = null;

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    public CallBack getCallback() {
        return callback;
    }

    public interface CallBack {
        void onDataChange(String data);
    }
}
