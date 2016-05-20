package me.aaronchan.androidaidl.remote.io;

import android.os.SystemClock;

/**
 * Created by Administrator on 2016/5/20.
 */
public class RecvPacketTask extends BaseIoTask {

    private OnIoChangedListener mIoChangedListener;

    public RecvPacketTask(OnIoChangedListener ioChangedListener) {
        if (ioChangedListener == null) {
            throw new IllegalArgumentException("OnIoChangedListener can not be null.");
        }

        mIoChangedListener = ioChangedListener;
    }

    @Override
    public void run() {
        while (mRunning) {
            SystemClock.sleep(3000);

            mIoChangedListener.onRecvBytes(new byte[20]);
        }
    }

}
