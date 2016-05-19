package me.aaronchan.androidaidl.remote.transport;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Created by aaronchan on 16/5/19.
 */
public class TransportLayoutImpl implements ITransportLayout {

    private static final String TAG = TransportLayoutImpl.class.getSimpleName();

    private CountDownLatch lock;

    @Override
    public boolean startIM(int id) {
        lock = new CountDownLatch(1);

        requestServer();

        try {
            Log.d(TAG, "Waiting... in " + Thread.currentThread().getName());

            lock.await();

            Log.d(TAG, "Thread " + Thread.currentThread().getName() + " awake");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.countDown();
        }

        return true;
    }

    private void requestServer() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Awake thread in " + Thread.currentThread().getName());
                SystemClock.sleep(3000);
                lock.countDown();
            }
        });
    }
}
