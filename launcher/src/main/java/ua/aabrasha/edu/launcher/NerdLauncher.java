package ua.aabrasha.edu.launcher;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NerdLauncher extends ListActivity {

    private static final String TAG = NerdLauncher.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nerd_launcher);

        final PackageManager pm = getPackageManager();

        Intent launcher = new Intent(Intent.ACTION_MAIN);
        launcher.addCategory(Intent.CATEGORY_LAUNCHER);

        final List<ResolveInfo> resolveInfos = pm.queryIntentActivities(launcher, 0);

        ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(
                this,
                android.R.layout.simple_list_item_1,
                resolveInfos
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
                TextView listItem = (TextView) convertView;
                listItem.setText(resolveInfos.get(position).loadLabel(pm));
                return listItem;
            }
        };

        Collections.sort(resolveInfos, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo o1, ResolveInfo o2) {
                return String.CASE_INSENSITIVE_ORDER.compare(
                        o1.loadLabel(pm).toString(), o2.loadLabel(pm).toString()
                );
            }
        });

        setListAdapter(adapter);

        Log.d(TAG, String.valueOf(resolveInfos.size()));
        Log.d(TAG, resolveInfos.toString());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        ResolveInfo resolveInfo = (ResolveInfo) getListAdapter().getItem(position);
        ActivityInfo activityInfo = resolveInfo.activityInfo;

        if (activityInfo == null)
            return;

        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
        startActivity(i);
    }
}
