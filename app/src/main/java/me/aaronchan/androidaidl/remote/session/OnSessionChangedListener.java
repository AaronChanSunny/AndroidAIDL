package me.aaronchan.androidaidl.remote.session;

import me.aaronchan.androidaidl.Packet;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface OnSessionChangedListener {
    void onSendComplete(boolean result);
    void onRecvPacket(Packet packet);
    void onCloseSuccess();
    void onCloseFail();
}
