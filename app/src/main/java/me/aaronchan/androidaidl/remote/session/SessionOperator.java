package me.aaronchan.androidaidl.remote.session;

import android.os.SystemClock;

import me.aaronchan.androidaidl.Packet;
import me.aaronchan.androidaidl.remote.transport.OnOperatorChangedListener;

/**
 * Created by Administrator on 2016/5/19.
 */
public class SessionOperator implements ISessionOperator {

    @Override
    public boolean sendPacket(Packet packet, OnOperatorChangedListener listener) {
        SystemClock.sleep(3000);

        if (listener == null) {
            throw new IllegalArgumentException("OnOperatorChangedListener can not be null.");
        }

        listener.onSendComplete(true);
        return true;
    }
}
