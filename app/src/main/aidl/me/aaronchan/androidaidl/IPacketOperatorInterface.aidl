// IPacketOperatorInterface.aidl
package me.aaronchan.androidaidl;

// Declare any non-default types here with import statements
import me.aaronchan.androidaidl.Packet;
import me.aaronchan.androidaidl.IPacketListenerInterface;

interface IPacketOperatorInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean sendPacket(in Packet packet);
    boolean startIM(int userId);

    void registerListener(in IPacketListenerInterface listener);
    void unRegisterListener(in IPacketListenerInterface listener);
}
