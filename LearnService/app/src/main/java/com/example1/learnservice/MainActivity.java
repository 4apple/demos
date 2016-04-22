package com.example1.learnservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.*;
import android.os.Process;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent intent ;
    private EditText etData;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //intent = new Intent(MainActivity.this, MyService.class);
        intent = new Intent();
        intent.setComponent(new ComponentName("com.example1.learnservice",
                "com.example1.learnservice.MyService"));
        //intent.putExtra("extraData","MyExtraData");
        findViewById(R.id.btnStartService).setOnClickListener(this);
        findViewById(R.id.btnStopService).setOnClickListener(this);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        findViewById(R.id.btnKillProcess).setOnClickListener(this);
        findViewById(R.id.btnStartIntentService).setOnClickListener(this);
        //注册同步数据监听器
        findViewById(R.id.btnSync).setOnClickListener(this);
        //获取EditText控件
        etData = (EditText)findViewById(R.id.etData);
        tv = (TextView)findViewById(R.id.textView);
        myCallback = new Callback() {
            @Override
            public void onDataChange(String data) {
                Message msg = new Message();
                Bundle bundle= new Bundle();
                bundle.putString("data",data);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        };

        //register local broadcast receiver
        IntentFilter intentFilter = new IntentFilter(MyIntentService.BROADCAST_ACTION);
        ResponseReceiver responseReceiver = new ResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(responseReceiver,intentFilter);


        System.out.println("Process tid  for MainActivity:" + android.os.Process.myTid() + ",pid:"+ android.os.Process.myPid());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnStartService:
                startService(intent);

                break;
            case R.id.btnStopService:
                stopService(intent);
                break;
            case R.id.btnBindService:
                bindService(intent, this, Context.BIND_AUTO_CREATE);
                //myBinder.setData("test");
                break;
            case R.id.btnUnbindService:
                unbindService(this);
                break;
            case R.id.btnKillProcess:
                android.os.Process.killProcess(Process.myPid());
                break;
            case R.id.btnSync:
                if(myBinder!= null){
                    myBinder.setData(etData.getText().toString());
                }
                break;
            case R.id.btnStartIntentService:
                MyIntentService.startActionFoo(this, "参数1", "参数2");
                break;
        }
    }

    private MyService.MyBinder myBinder=null;
    private Callback myCallback = null;
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("onServiceConnected");
        myBinder = (MyService.MyBinder) service;
        myBinder.setCallback(myCallback);

    }
    /*
    当收到Service传回来的String类型的data后，会将数据显示到一个TextView上，
    但因为操作onDataChange()是在Service里的一个子线程中操作的，Android中
    UI操作必须在主线程进行，因此，当接受到数据后，我们需要封装成消息，发送给
    UI线程中的Handler，由Handler中的HandleMessage（）来接受数据并更新TextView;
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.getData().getString("data");
            tv.setText(data);
        }
    };
    @Override
    public void onServiceDisconnected(ComponentName name) {
        System.out.println("onServiceDisconnected");
    }

    class ResponseReceiver extends BroadcastReceiver {
        public ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String param1 = intent.getStringExtra(MyIntentService.EXTENDED_DATA_STATUS);
            String param2 = intent.getStringExtra(MyIntentService.EXTENDED_DATA_STATUS2);
            System.out.println("param1:" + param1 + ",param2:" + param2);
            String data = "param1:" + param1 + ",param2:" + param2;
            System.out.println("Process tid  for ResponseReceiver:" + Process.myTid() + ",pid:"+ Process.myPid());
            tv.setText(data);
        }
    }
}
