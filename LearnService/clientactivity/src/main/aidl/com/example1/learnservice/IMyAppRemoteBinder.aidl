// IMyAppRemoteBinder.aidl
package com.example1.learnservice;
import com.example1.learnservice.ICallback;

// Declare any non-default types here with import statements

interface IMyAppRemoteBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void setData(String data);
    void registerCallBack(ICallback call);
    void unregisterCallBack(ICallback call);
}
