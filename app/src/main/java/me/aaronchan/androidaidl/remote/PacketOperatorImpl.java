package me.aaronchan.androidaidl.remote;

import java.util.concurrent.ConcurrentLinkedQueue;

import me.aaronchan.androidaidl.Packet;

/**
 * Created by aaronchan on 16/5/18.
 */
public class PacketOperatorImpl implements IPacketOperator {

    private ConcurrentLinkedQueue<Packet> mPackets;

    public PacketOperatorImpl() {
        mPackets = new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean sendPacket(Packet packet) {
        mPackets.add(packet);
        return true;
    }
}
