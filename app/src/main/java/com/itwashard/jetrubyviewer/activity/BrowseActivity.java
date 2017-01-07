package com.itwashard.jetrubyviewer.activity;

import android.os.Bundle;

import com.itwashard.jetrubyviewer.R;

import butterknife.ButterKnife;

public class BrowseActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        ButterKnife.bind(this);
    }

}
