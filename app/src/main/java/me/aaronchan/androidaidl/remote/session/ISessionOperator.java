package me.aaronchan.androidaidl.remote.session;

import me.aaronchan.androidaidl.Packet;
import me.aaronchan.androidaidl.remote.transport.OnOperatorChangedListener;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface ISessionOperator {
    boolean sendPacket(Packet packet, OnOperatorChangedListener listener);
}
