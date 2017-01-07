package com.itwashard.jetrubyviewer;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class DisplayMetricsUtil {

    /**
     * Return true if the width in DP of the device is equal or greater than the given value
     */
    public static boolean isScreenW(int widthDp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        return screenWidth >= widthDp;
    }
}
