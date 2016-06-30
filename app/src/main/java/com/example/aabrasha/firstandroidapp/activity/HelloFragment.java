package com.example.aabrasha.firstandroidapp.activity;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aabrasha.firstandroidapp.R;

/**
 * Created by Andrii Abramov on 6/30/16.
 */
public class HelloFragment extends Fragment {

    TextView tvHello;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        tvHello = (TextView) view.findViewById(R.id.textView);
        tvHello.setText("Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT +
                "\nBuild.VERSION.CODENAME: " + Build.VERSION.CODENAME);

        return view;
    }
}
