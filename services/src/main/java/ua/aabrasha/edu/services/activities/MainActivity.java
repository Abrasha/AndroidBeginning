package ua.aabrasha.edu.services.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import ua.aabrasha.edu.services.R;
import ua.aabrasha.edu.services.services.TimeService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnStartService;
    private Button btnStopService;
    private Button btnNotify;
    private ListView lvLog;

    private List<String> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messages = new ArrayList<>();

        initComponents();
    }

    private void initComponents() {
        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStopService = (Button) findViewById(R.id.btnStopService);
        btnNotify = (Button) findViewById(R.id.btnNotify);
        lvLog = (ListView) findViewById(R.id.list_log);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeService.setSystemAlarm(MainActivity.this, true);
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeService.setSystemAlarm(MainActivity.this, false);
            }
        });

        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });

        lvLog.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages));

    }

    private void showNotification() {
        Notification notification = new Notification.Builder(this)
                .setTicker("Ticker")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("content title")
                .setContentText("content text")
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }


}
