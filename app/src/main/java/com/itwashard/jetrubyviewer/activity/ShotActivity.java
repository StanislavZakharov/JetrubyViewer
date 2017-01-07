package com.itwashard.jetrubyviewer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.itwashard.jetrubyviewer.R;
import com.itwashard.jetrubyviewer.data.model.Shot;
import com.itwashard.jetrubyviewer.fragments.ShotFragment;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class ShotActivity extends BaseActivity {

    public static final String EXTRA_SHOT = "com.itwashard.jetrubyviewer.EXTRA_SHOT";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Shot shot = getIntent().getParcelableExtra(EXTRA_SHOT);
        if (shot == null) {
            throw new IllegalArgumentException("Shot activity requires a shot instance!");
        }
        activityComponent().inject(this);
        setContentView(R.layout.activity_shot);

        ShotFragment shotFragment = ShotFragment.newInstance(shot);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, shotFragment)
                .commit();
    }

    public static Intent getStartIntent(Context context, Shot shot) {
        Intent intent = new Intent(context, ShotActivity.class);
        intent.putExtra(EXTRA_SHOT, shot);
        return intent;
    }
}
