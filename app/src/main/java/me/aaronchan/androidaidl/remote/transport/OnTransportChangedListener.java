package me.aaronchan.androidaidl.remote.transport;

import me.aaronchan.androidaidl.Packet;

/**
 * Created by Administrator on 2016/5/20.
 */
public interface OnTransportChangedListener {
    void onRecvPacket(Packet packet);
}
