package com.example.hm.convertlab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hm.convertlab.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar topToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        topToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragCont, new HomeFragment())
                    .commit();
    }

    public Toolbar getTopToolBar() {
        return topToolBar;
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyApplication.sIsActive = true;
    }

    @Override
    protected void onStop() {
        MyApplication.sIsActive = false;
        super.onStop();
    }
}
