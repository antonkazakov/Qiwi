package com.antonkazakov.qiwitask.ui.screen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.antonkazakov.qiwitask.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainFragment mainFragment = (MainFragment) fragmentManager.findFragmentByTag("fuck");
        if (mainFragment == null) {
            mainFragment = new MainFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.root, mainFragment, "fuck")
                    .commit();
        }
    }

}
