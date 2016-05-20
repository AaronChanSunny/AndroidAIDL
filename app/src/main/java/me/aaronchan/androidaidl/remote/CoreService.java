package me.aaronchan.androidaidl.remote;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import me.aaronchan.androidaidl.IPacketListenerInterface;
import me.aaronchan.androidaidl.IPacketOperatorInterface;
import me.aaronchan.androidaidl.Packet;
import me.aaronchan.androidaidl.remote.transport.IPacketOperator;
import me.aaronchan.androidaidl.remote.transport.ITransportLayoutManager;
import me.aaronchan.androidaidl.remote.transport.OnTransportChangedListener;
import me.aaronchan.androidaidl.remote.transport.PacketOperator;
import me.aaronchan.androidaidl.remote.transport.TransportLayoutManager;

/**
 * Created by aaronchan on 16/5/18.
 */
public class CoreService extends Service implements OnTransportChangedListener {

    private static final String TAG = CoreService.class.getSimpleName();

    private boolean mShouldStop = false;
    private ITransportLayoutManager mITransportLayout;
    private IPacketOperator mPacketOperator;
    private Binder mBinder;
    private RemoteCallbackList<IPacketListenerInterface> mCallbackList;

    @Override
    public void onCreate() {
        super.onCreate();

        startForeground(1, new Notification());

        initData();
    }

    private void initData() {
        mCallbackList = new RemoteCallbackList<>();

        mPacketOperator = new PacketOperator(this);
        mITransportLayout = new TransportLayoutManager(mPacketOperator);

        mBinder = new IPacketOperatorInterface.Stub() {

            @Override
            public boolean sendPacket(Packet packet) throws RemoteException {
                Log.d(TAG, "SendPacket " + packet.getContent() + " in " + Thread.currentThread()
                        .getName());

                return mPacketOperator.sendPacket(packet);
            }

            @Override
            public boolean startIM(int userId) throws RemoteException {
                Log.d(TAG, "StartIM userId " + userId + " in " + Thread.currentThread().getName());

                return mITransportLayout.startIM(userId);
            }

            @Override
            public void registerListener(IPacketListenerInterface listener) throws RemoteException {
                Log.d(TAG, "RegisterListener in " + Thread.currentThread().getName());

                mCallbackList.register(listener);
            }

            @Override
            public void unRegisterListener(IPacketListenerInterface listener) throws RemoteException {
                mCallbackList.unregister(listener);

                Log.d(TAG, "UnRegisterListener in " + Thread.currentThread().getName());
            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        mITransportLayout.stopIM();

        super.onDestroy();
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

    @Override
    public void onRecvPacket(Packet packet) {
        notifyPacketRecv(packet);
    }

}
