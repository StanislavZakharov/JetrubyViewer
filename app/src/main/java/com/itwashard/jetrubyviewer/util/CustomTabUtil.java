package com.itwashard.jetrubyviewer.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import com.itwashard.jetrubyviewer.R;

import timber.log.Timber;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class CustomTabUtil {

    public static void open(Context context, Uri uri) {
        int color = ContextCompat.getColor(context, R.color.colorPrimary);
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .setToolbarColor(color)
                .setShowTitle(true)
                .build();

        Intent intent = customTabsIntent.intent;
        intent.setData(uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException error) {
            Timber.e(error, "There was a problem start the browser intent");
        }
    }
}
