package me.aaronchan.androidaidl.remote.transport;

import android.os.SystemClock;

/**
 * Created by aaronchan on 16/5/19.
 */
public class TransportLayoutManager implements ITransportLayoutManager {

    private static final String TAG = TransportLayoutManager.class.getSimpleName();

    private IPacketOperator mPacketOperator;

    public TransportLayoutManager(IPacketOperator packetOperator) {
        mPacketOperator = packetOperator;
    }

    @Override
    public boolean startIM(int id) {
        SystemClock.sleep(3000);

        return true;
    }

    @Override
    public void stopIM() {
        mPacketOperator.closeSession();
    }

}
