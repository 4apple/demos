package colorcatcher.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import colorcatcher.startservicefromanotherapp.IAppServiceRemoteBinder;
import colorcatcher.startservicefromanotherapp.IMyAppServiceRemoteBinder;

public class MyActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent serviceIntent;
    private EditText etInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("--MyActivity--");
        setContentView(R.layout.activity_main);
        etInput = (EditText) findViewById(R.id.edtInput);
        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("colorcatcher.startservicefromanotherapp", "colorcatcher.startservicefromanotherapp.AppService"));
        findViewById(R.id.btnStartAppService).setOnClickListener(this);
        findViewById(R.id.btnStopAppService).setOnClickListener(this);
        findViewById(R.id.btnBindAppService).setOnClickListener(this);
        findViewById(R.id.btnUnbindAppService).setOnClickListener(this);
        findViewById(R.id.btnSync).setOnClickListener(this);
        findViewById(R.id.btnStartThread).setOnClickListener(this);
        findViewById(R.id.btnStopThread).setOnClickListener(this);
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
            case R.id.btnStartAppService:
                startService(serviceIntent);
                break;
            case R.id.btnStopAppService:
                stopService(serviceIntent);
                break;
            case R.id.btnBindAppService:
                bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindAppService:
                unbindService(this);
                binder = null;
            case R.id.btnSync:
/*                if(binder != null){
                    try {
                        binder.setData(etInput.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }*/
                try {
                    mybinder.setData(etInput.getText().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnStartThread:
                if(binder != null){
                    try {
                        binder.startThread();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btnStopThread:
                if(binder != null){
                    try {
                        binder.stopThread();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("Bind service");
        System.out.println(service);
        //binder = (IAppServiceRemoteBinder) service;
        binder = IAppServiceRemoteBinder.Stub.asInterface(service);
        mybinder = IMyAppServiceRemoteBinder.Stub.asInterface(service);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        System.out.println("unbind service");
    }
    private IAppServiceRemoteBinder binder = null;
    private IMyAppServiceRemoteBinder mybinder  = null;
}
