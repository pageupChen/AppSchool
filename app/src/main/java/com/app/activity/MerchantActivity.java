package com.app.activity;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.appproject.R;

public class MerchantActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
//
        toolbar = (Toolbar) this.findViewById(R.id.merchantActivity_toolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("赞助社区");

        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
