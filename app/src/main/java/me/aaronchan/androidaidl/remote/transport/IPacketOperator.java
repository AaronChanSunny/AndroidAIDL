package me.aaronchan.androidaidl.remote.transport;

import me.aaronchan.androidaidl.Packet;

/**
 * Created by aaronchan on 16/5/17.
 */
public interface IPacketOperator {

    boolean sendPacket(Packet packet);
}
