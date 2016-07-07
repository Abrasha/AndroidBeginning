package ua.aabrasha.edu.services.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Andrii Abramov on 7/7/16.
 */
public class NetworkStateChangedReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkStateChangedReceiver.class.getSimpleName();

    public static final String ACTION_SHOW_NETWORK_NOTIFICATION = "edu.aabrasha.net.state_changed";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_SHOW_NETWORK_NOTIFICATION)) {
            Log.d(TAG, "Network state changed to. Is connected: " + isConnectionPresent(context));
            showNotification(context);
        }
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.d(TAG, "Network state changed to. Is connected: " + isConnectionPresent(context));
        }
    }

    private boolean isConnectionPresent(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private void showNotification(Context context) {
        Notification notification = new Notification.Builder(context)
                .setTicker("Ticker")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("content title: got action from broadcast")
                .setContentText("Network state changed")
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
