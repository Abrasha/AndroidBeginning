package com.example.aabrasha.firstandroidapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.aabrasha.firstandroidapp.R;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("MainActivity Toolbar");

        tvHello = (TextView) findViewById(R.id.textView);
        tvHello.setText("Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT +
        "\nBuild.VERSION.CODENAME: " + Build.VERSION.CODENAME);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_basic_item:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.menu_true_false__item:
                Intent trueFalseIntent = new Intent(this, TrueFalseActivity.class);
                startActivity(trueFalseIntent);
                break;
        }

        return true;
    }
}
