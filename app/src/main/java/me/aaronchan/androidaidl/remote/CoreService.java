package me.aaronchan.androidaidl.remote;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import me.aaronchan.androidaidl.IPacketOperatorInterface;
import me.aaronchan.androidaidl.Packet;

/**
 * Created by aaronchan on 16/5/18.
 */
public class CoreService extends Service {

    private static final String TAG = CoreService.class.getSimpleName();

    private boolean mShouldStop = false;
    private IPacketOperator mIPacketOperator;
    private Binder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();

        mIPacketOperator = new PacketOperatorImpl();

        mBinder = new IPacketOperatorInterface.Stub() {

            @Override
            public boolean sendPacket(Packet packet) throws RemoteException {
                Log.d(TAG, "sendPacket in " + Thread.currentThread().getName());

                return mIPacketOperator.sendPacket(packet);
            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setShouldStop(boolean shouldStop) {
        mShouldStop = shouldStop;
    }

    class HeartBeatThread extends Thread {

        @Override
        public void run() {
            super.run();

            while (!mShouldStop) {

            }
        }

    }
}
