package colorcatcher.startservicefromanotherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by user on 3/25/16.
 */
public class MyAppService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAppServiceRemoteBinder.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void setData(String data) throws RemoteException {

            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service Started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service Destroy");
    }
}
