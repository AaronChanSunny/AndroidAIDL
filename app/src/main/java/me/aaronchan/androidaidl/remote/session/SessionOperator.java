package me.aaronchan.androidaidl.remote.session;

import android.os.SystemClock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.aaronchan.androidaidl.Packet;
import me.aaronchan.androidaidl.remote.io.BaseIoTask;
import me.aaronchan.androidaidl.remote.io.OnIoChangedListener;
import me.aaronchan.androidaidl.remote.io.RecvPacketTask;

/**
 * Created by Administrator on 2016/5/19.
 */
public class SessionOperator implements ISessionOperator, OnIoChangedListener {

    private OnSessionChangedListener mSessionChangedListener;
    private BaseIoTask mRecvTask;
    private ExecutorService mRecvTaskExecutor;

    public SessionOperator(OnSessionChangedListener onOperatorChangedListener) {
        if (onOperatorChangedListener == null) {
            throw new IllegalArgumentException("OnSessionChangedListener can not be null.");
        }

        mSessionChangedListener = onOperatorChangedListener;

        initIo();
    }

    private void initIo() {
        mRecvTask = new RecvPacketTask(this);

        mRecvTaskExecutor = Executors.newSingleThreadExecutor();
        mRecvTaskExecutor.execute(mRecvTask);
    }

    @Override
    public boolean sendPacket(Packet packet) {
        SystemClock.sleep(3000);

        mSessionChangedListener.onSendComplete(true);
        return true;
    }

    @Override
    public void close() {
        mRecvTask.stop();
    }

    @Override
    public void onRecvBytes(byte[] data) {
        Packet packet = new Packet(0, 5, "Packet from io");

        mSessionChangedListener.onRecvPacket(packet);
    }
}
