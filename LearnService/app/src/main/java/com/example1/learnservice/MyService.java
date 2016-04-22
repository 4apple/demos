package com.example1.learnservice;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.telecom.Call;

public class MyService extends Service {
    //创建字符串，用于log显示及数据同步
    private String data="default value";
    private Boolean isRunning = true;
    public MyService() {
    }

    @Override
    public void onRebind(Intent intent) {
        System.out.println("My service onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onCreate() {
//        getPackageManager().setComponentEnabledSetting("com.example1.learnservice.MyService");
        super.onCreate();
        System.out.println("Service Create");
        System.out.println("Process tid  for MyService:" + android.os.Process.myTid() + ",pid:"+ android.os.Process.myPid());
        //启用一个Thread，来每个一秒打印一个字符串，而当点击Activity中的Sync data 的button，
        // 则用EditText内容去修改这段字符串，以验证Binder通讯的目的
        new Thread(){
            @Override
            public void run() {
                int i = 0;
                while(isRunning){
                    try {
                        sleep(5000);
                        if(callback != null){
                            i++;
                            callback.onDataChange(data + i);
                        }
                        //System.out.println(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        System.out.println("Service Destroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service onStartCommand");
        if(intent != null){
            System.out.println("get Extra data from intent:" + intent.getStringExtra("extraData"));
        } else {
            System.out.println("get Extra data from intent:" + null);
        }
        return START_REDELIVER_INTENT;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("on Bind service");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("default onUnbind return:" + super.onUnbind(intent));
        return true;
    }

    public class MyBinder extends Binder {
        public void setData(String data){
            MyService.this.data = data;
        }

        public Callback getCallBack() {
            return callback;
        }

        public void setCallback(Callback callback) {
            MyService.this.callback = callback;
        }
    }
    private Callback callback = null;

}
interface Callback {
    void onDataChange(String data);
}