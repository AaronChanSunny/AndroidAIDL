package me.aaronchan.androidaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.aaronchan.androidaidl.remote.CoreService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private boolean mBound = false;
    private ServiceConnection mConn;
    private IPacketOperatorInterface mRemoteService;
    private IPacketListenerInterface mPacketListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        bindService(new Intent(this, CoreService.class), mConn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (mRemoteService != null) {
            try {
                mRemoteService.unRegisterListener(mPacketListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (mBound) {
            unbindService(mConn);
        }
        super.onStop();
    }

    private void initData() {
        mPacketListener = new IPacketListenerInterface.Stub() {
            @Override
            public void recvPacket(Packet packet) throws RemoteException {
                Log.d(TAG, "RecvPacket " + packet.getContent() + " in " + Thread.currentThread()
                        .getName());
            }
        };

        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected");
                mBound = true;
                mRemoteService = IPacketOperatorInterface.Stub.asInterface(service);

                try {
                    mRemoteService.registerListener(mPacketListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected");
                mBound = false;
                mRemoteService = null;
            }
        };
    }

    private void initView() {
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mRemoteService.startIM(1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mRemoteService.sendPacket(new Packet(0, 5, "Hello World!"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
