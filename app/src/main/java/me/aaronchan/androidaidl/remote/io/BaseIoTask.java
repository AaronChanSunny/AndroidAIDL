package me.aaronchan.androidaidl.remote.io;

/**
 * Created by Administrator on 2016/5/20.
 */
public abstract class BaseIoTask implements Runnable {
    protected volatile boolean mRunning = true;

    public void stop() {
        mRunning = false;
    }
}
