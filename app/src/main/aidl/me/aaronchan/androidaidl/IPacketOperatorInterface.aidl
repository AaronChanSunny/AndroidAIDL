// IPacketOperatorInterface.aidl
package me.aaronchan.androidaidl;

// Declare any non-default types here with import statements
import me.aaronchan.androidaidl.Packet;

interface IPacketOperatorInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean sendPacket(in Packet packet);
}
