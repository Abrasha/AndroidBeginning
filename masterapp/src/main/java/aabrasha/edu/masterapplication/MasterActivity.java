package aabrasha.edu.masterapplication;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("deprecation")
public class MasterActivity extends AppCompatActivity {

    private static final String TAG = MasterActivity.class.getSimpleName();
    private static final String IMAGE_LINK = "http://actusperformance.com/wp-content/uploads/2015/06/ID-100288828_DONE.jpg";

    public static final String ACTION_REQUEST_CREDS_FROM_IMAGE_THREAD = "aabrasha.ACTION_REQUEST_CREDS_FROM_IMAGE_THREAD";

    private ImageView imgHolder;
    private ProgressBar progressBar;
    private Button btnLoadImage;
    private Button btnSendImage;
    private boolean imageLoaded = false;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "got: " + intent.getAction());
            if (imageLoaded) {
                Intent requestSocketCreds = new Intent(ACTION_REQUEST_CREDS_FROM_IMAGE_THREAD);
                sendBroadcast(requestSocketCreds); // it is handled in image thread
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, SocketServerService.class));

        // if thread started -> request socket creds through broadcast further
        // else start ImageThread and obligate it to send broadcast with creds to service
        registerReceiver(broadcastReceiver, new IntentFilter(SocketServerService.ACTION_REQUEST_SOCKET_FROM_SERVER));

        imgHolder = (ImageView) findViewById(R.id.imgView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLoadImage = (Button) findViewById(R.id.btnLoadImage);
        btnSendImage = (Button) findViewById(R.id.btnSendImage);

        btnSendImage.setEnabled(false);btnSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchConsumerApp();
            }
        });

        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                progressBar.setIndeterminate(true);
                new AsyncHttpImageLoader().execute(IMAGE_LINK);
            }
        });
    }

    private void launchConsumerApp() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (int i = 0; i < recentTasks.size(); i++) {
            if (recentTasks.get(i).baseActivity.toShortString().contains("aabrasha.edu.consumerapp")) {
                activityManager.moveTaskToFront(recentTasks.get(i).id, ActivityManager.MOVE_TASK_WITH_HOME);
                return;
            }
        }
        Intent intent = getPackageManager().getLaunchIntentForPackage("aabrasha.edu.consumerapp");
        startActivity(intent);
    }


    private class AsyncHttpImageLoader extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(strings[0]);

            try {
                HttpEntity response = client.execute(get).getEntity();
                return BitmapFactory.decodeStream(response.getContent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

        @Override
        protected void onPostExecute(Bitmap s) {
            imgHolder.setImageBitmap(s);
            imageLoaded = true;
            progressBar.setIndeterminate(false);
            btnSendImage.setEnabled(true);
            new Thread(new ImageThread(MasterActivity.this, s), "ImageThread").start();
            Toast.makeText(getApplication(), "Done loading", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, SocketServerService.class));
        unregisterReceiver(broadcastReceiver);
    }
}
