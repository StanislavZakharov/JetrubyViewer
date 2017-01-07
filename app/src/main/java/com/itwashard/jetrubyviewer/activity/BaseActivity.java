package com.itwashard.jetrubyviewer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.itwashard.jetrubyviewer.ActivityComponent;
import com.itwashard.jetrubyviewer.DaggerActivityComponent;
import com.itwashard.jetrubyviewer.JetrubbyApplication;
import com.itwashard.jetrubyviewer.injection.module.ActivityModule;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(JetrubbyApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
