package me.aaronchan.androidaidl.remote.transport;

import android.util.Log;

import me.aaronchan.androidaidl.Packet;
import me.aaronchan.androidaidl.remote.session.ISessionOperator;
import me.aaronchan.androidaidl.remote.session.OnSessionChangedListener;
import me.aaronchan.androidaidl.remote.session.SessionOperator;

/**
 * Created by aaronchan on 16/5/18.
 */
public class PacketOperator implements IPacketOperator, OnSessionChangedListener {

    private static final String TAG = PacketOperator.class.getSimpleName();

    private ISessionOperator mSession;
    private OnTransportChangedListener mTransportChangedListener;

    public PacketOperator(OnTransportChangedListener transportChangedListener) {
        mTransportChangedListener = transportChangedListener;

        mSession = new SessionOperator(this);
    }

    @Override
    public boolean sendPacket(Packet packet) {
        mSession.sendPacket(packet);

        return true;
    }

    @Override
    public void closeSession() {
        mSession.close();
    }

    @Override
    public void onSendComplete(boolean result) {
        Log.d(TAG, "onSendComplete " + result);
    }

    @Override
    public void onRecvPacket(Packet packet) {
        mTransportChangedListener.onRecvPacket(packet);
    }

    @Override
    public void onCloseSuccess() {
        Log.d(TAG, "onCloseSuccess");
    }

    @Override
    public void onCloseFail() {
        Log.d(TAG, "onCloseFail");
    }
}
