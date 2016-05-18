package me.aaronchan.androidaidl.remote;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import me.aaronchan.androidaidl.IPacketListenerInterface;
import me.aaronchan.androidaidl.IPacketOperatorInterface;
import me.aaronchan.androidaidl.Packet;

/**
 * Created by aaronchan on 16/5/18.
 */
public class CoreService extends Service {

    private static final String TAG = CoreService.class.getSimpleName();

    private boolean mShouldStop = false;
    private IPacketOperator mIPacketOperator;
    private IPacketListenerInterface mPacketListener;
    private Binder mBinder;
    private RemoteCallbackList<IPacketListenerInterface> mCallbackList;

    @Override
    public void onCreate() {
        super.onCreate();

        initData();
    }

    private void initData() {
        mCallbackList = new RemoteCallbackList<>();

        mIPacketOperator = new PacketOperatorImpl();

        mBinder = new IPacketOperatorInterface.Stub() {

            @Override
            public boolean sendPacket(Packet packet) throws RemoteException {
                Log.d(TAG, "SendPacket " + packet.getContent() + " in " + Thread.currentThread()
                        .getName());

                return mIPacketOperator.sendPacket(packet);
            }

            @Override
            public boolean startIM(int userId) throws RemoteException {
                Log.d(TAG, "User " + userId + " startIM in " + Thread.currentThread().getName());

                SystemClock.sleep(3000);
                return true;
            }

            @Override
            public void registerListener(IPacketListenerInterface listener) throws RemoteException {
                mCallbackList.register(listener);
            }

            @Override
            public void unRegisterListener(IPacketListenerInterface listener) throws RemoteException {
                mCallbackList.unregister(listener);
            }
        };

        new HeartBeatThread().start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setShouldStop(boolean shouldStop) {
        mShouldStop = shouldStop;
    }

    private void notifyPacketRecv(Packet packet) {
        int n = mCallbackList.beginBroadcast();

        for (int i = 0; i < n; i ++) {
            IPacketListenerInterface listener = mCallbackList.getBroadcastItem(i);

            try {
                listener.recvPacket(packet);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        mCallbackList.finishBroadcast();
    }

    class HeartBeatThread extends Thread {

        private int mPacketId = 0;

        @Override
        public void run() {
            super.run();

            while (!mShouldStop) {
                SystemClock.sleep(3000);

                notifyPacketRecv(new Packet(mPacketId, 3, "Content " + mPacketId));

                mPacketId++;
            }
        }

    }

}
