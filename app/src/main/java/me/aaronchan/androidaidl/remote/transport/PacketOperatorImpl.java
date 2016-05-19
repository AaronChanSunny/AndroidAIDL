package me.aaronchan.androidaidl.remote.transport;

import android.util.Log;

import me.aaronchan.androidaidl.Packet;
import me.aaronchan.androidaidl.remote.session.ISessionOperator;
import me.aaronchan.androidaidl.remote.session.SessionOperator;

/**
 * Created by aaronchan on 16/5/18.
 */
public class PacketOperatorImpl implements IPacketOperator, OnOperatorChangedListener {

    private static final String TAG = PacketOperatorImpl.class.getSimpleName();

    private ISessionOperator mSession;

    public PacketOperatorImpl() {
        mSession = new SessionOperator();
    }

    @Override
    public boolean sendPacket(Packet packet) {
        mSession.sendPacket(packet, this);

        return true;
    }

    @Override
    public void onSendComplete(boolean result) {
        Log.d(TAG, "onSendComplete");
    }
}
