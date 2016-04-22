package com.example1.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example1.learnservice.ICallback;
import com.example1.learnservice.IMyAppRemoteBinder;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent intent=null;
    private EditText etInput;
    private TextView mShowResult;
    private TextView mShowResult2;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当客户端退出后，解除绑定服务，并将binder置为null，避免内存泄露
        if(binder!=null){
            unbindService(this);
            binder = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        intent = new Intent();
        //Android5.0及以后的版本，不允许通过隐式的方式启动服务，而只能通过显示制定包名和类名的显示方式启动
        intent.setComponent(new ComponentName("com.example1.learnservice", "com.example1.learnservice.RemoteService"));
        intent.putExtra("data", "data from ClientActivity");
        etInput = (EditText) findViewById(R.id.etInput);
        mShowResult = (TextView) findViewById(R.id.showResult);
        mShowResult2 = (TextView) findViewById(R.id.showResult2);
        findViewById(R.id.btnStartRemoteService).setOnClickListener(this);
        findViewById(R.id.btnStopRemoteService).setOnClickListener(this);
        findViewById(R.id.btnBindRemoteService).setOnClickListener(this);
        findViewById(R.id.btnUnbindRemoteService).setOnClickListener(this);
        findViewById(R.id.btnSync).setOnClickListener(this);
    }

    //实现ICallback接口
    private ICallback mCallBack = new ICallback.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void showResult(int result) throws RemoteException {
            System.out.println("result:" + result);
            Message message = new Message();
            Bundle data = new Bundle();
            data.putInt("type",1);
            data.putInt("result", result);
            message.setData(data);
            message.setTarget(mHandler);
            message.sendToTarget();
        }
    };

    //实现ICallback接口
    private ICallback mCallBack2 = new ICallback.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void showResult(int result) throws RemoteException {
            System.out.println("result2:" + result);
            Message message = new Message();
            Bundle data = new Bundle();
            data.putInt("type", 2);
            data.putInt("result", result);
            message.setData(data);
            message.setTarget(mHandler);
            message.sendToTarget();
        }
    };
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String data = String.valueOf(msg.getData().getInt("result"));
            int type = msg.getData().getInt("type");
            StringBuilder content=new StringBuilder("");
            TextView tv = null;

            switch (type) {
                case 1:
                    content.append("callback1");
                    tv = mShowResult;
                    break;
                case 2:
                    content.append("callback2");
                    tv = mShowResult2;
                    break;

            }
            content.append("_");
            content.append(data);
            if(tv != null){
                tv.setText(content.toString());
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnStartRemoteService:
                startService(intent);
                break;
            case R.id.btnStopRemoteService:
                stopService(intent);
                break;
            case R.id.btnBindRemoteService:
                bindService(intent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindRemoteService:
                if(binder != null){
                    unbindService(this);
                    binder = null;
                }
                break;
            case R.id.btnSync:
                setData();
                break;
        }
    }

    private void setData() {
        if(binder!=null){
            try {
                binder.setData(etInput.getText().toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("Client connect service");
        System.out.println(service);
        binder = IMyAppRemoteBinder.Stub.asInterface(service);
        //register callback
        try {
            binder.registerCallBack(mCallBack);
            binder.registerCallBack(mCallBack2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        binder = null;
    }
    private IMyAppRemoteBinder binder = null;
}
