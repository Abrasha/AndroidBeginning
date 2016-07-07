package ua.aabrasha.edu.services.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Andrii Abramov on 7/7/16.
 */
public class TimeService extends IntentService {

    private static final String TAG = TimeService.class.getSimpleName();
    public static final int INTERVAL = 1000; // 2 sec

    public TimeService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "TimeService triggered");
        Log.d(TAG, "Got an intent: " + intent.getStringExtra("name"));
        sendBroadcast(new Intent(NetworkStateChangedReceiver.ACTION_SHOW_NETWORK_NOTIFICATION));
    }


    public static void setSystemAlarm(Context context, boolean setOn) {
        Intent i = new Intent(context, TimeService.class);
        i.putExtra("name", "andrew");

        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (setOn) {
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), INTERVAL, pi);
            Log.d(TAG, "Enabling TimeService");
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
            Log.d(TAG, "Disabling TimeService");
        }
    }

    public static boolean isTimeServiceOn(Context context) {
        Intent i = new Intent(context, TimeService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }


}
