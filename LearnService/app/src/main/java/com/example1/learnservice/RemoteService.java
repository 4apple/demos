package com.example1.learnservice;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class RemoteService extends Service {
    public RemoteService() {
    }

    private RemoteCallbackList<ICallback> mCallbacks = new RemoteCallbackList<ICallback>();
//    private ICallback mCallback;
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Remote Service onBind");
        return new IMyAppRemoteBinder.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }
            //provide api to client to change the String data
            @Override
            public void setData(String data) throws RemoteException {
                RemoteService.this.data = data;
            }

            @Override
            public void registerCallBack(ICallback callback) throws RemoteException {
//                  mCallback = call;
                if(callback!=null){
                    mCallbacks.register(callback);
                }
            }

            @Override
            public void unregisterCallBack(ICallback callback) throws RemoteException {
//                 mCallback = null;
                if(callback!=null){
                    mCallbacks.unregister(callback);
                }
            }

        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Remote Service Create");
        //new Thread,show log (data) every seconds
        new Thread(){
            @Override
            public void run() {
                super.run();
                running = true;
                int count = 1;
                while(running) {
                    try {
                        sleep(1000);
                        System.out.println(data);
//                        if(mCallback!=null){
//                            mCallback.showResult(count);
//                        }
                        callBack(count);
                        count++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }
/*
    private void callBack() {
        int N = mCallbacks.beginBroadcast();
        try {
            for (int i = 0; i < N; i++) {
                mCallbacks.getBroadcastItem(i).showResult(i);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "", e);
        }
        mCallbacks.finishBroadcast();
    }*/

    private void callBack(int count) {
        int N = mCallbacks.beginBroadcast();
        try {
            for(int i = 0; i < N ; i++) {
                mCallbacks.getBroadcastItem(i).showResult(count);
                System.out.println("index=" + i +",count="+count);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mCallbacks.finishBroadcast();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallbacks.kill();
        running = false;
        System.out.println("Remote Service Destroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Remote Service onStartCommand,data=" + intent.getStringExtra("data"));
        return super.onStartCommand(intent, flags, startId);
    }
    private String data="default string";
    private Boolean running = false;
}
