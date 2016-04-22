package colorcatcher.startservicefromanotherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AppService extends Service {
    public AppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new IAppServiceRemoteBinder.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void setData(String data) throws RemoteException {
                AppService.this.data = data;
            }

            @Override
            public void stopThread() throws RemoteException {
                AppService.this.stopThread();
            }

            @Override
            public void startThread() throws RemoteException {
                AppService.this.startThread();
            }

        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service started");
        //startThread();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service stop");
        running = false;
        mThread.interrupt();
        mThread= null;
    }

    public void startThread() {
        mThread = new Thread(){
            @Override
            public void run() {
                super.run();
                running = true;
                while(running) {
                    System.out.println(data);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        mThread.start();
    }

    public void stopThread(){
        running = false;
        //mThread.interrupt();
        mThread= null;
    }
    private String data = "默认数据";
    private boolean running = false;
    private static Thread mThread = null;
}
