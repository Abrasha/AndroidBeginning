package ua.aabrasha.edu.services.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Andrii Abramov on 7/7/16.
 */
public class ScreenUnlockBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = ScreenUnlockBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.d(TAG, "Action ACTION_SCREEN_ON");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d(TAG, "Action ACTION_SCREEN_OFF");
        }

        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Log.d(TAG, "Action ACTION_USER_PRESENT");
        }
    }
}
