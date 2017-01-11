package aabrasha.edu.masterapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class SocketServerService extends Service {

    private static final String TAG = SocketServerService.class.getSimpleName();

    public static final String ACTION_REQUEST_SOCKET_FROM_SERVER = "aabrasha.REQUEST_SOCKET"; // Service -> Server (asks for socket creds)
    public static final String ACTION_RESOLVE_CONNECTION = "aabrasha.RESOLVE_CONNECTION";


    BroadcastReceiver clientRequestReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "got: " + intent.getAction());
            final Intent broadcastMessage = new Intent(ACTION_REQUEST_SOCKET_FROM_SERVER);
            sendBroadcast(broadcastMessage);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(clientRequestReceiver, new IntentFilter(ACTION_RESOLVE_CONNECTION));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(clientRequestReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
