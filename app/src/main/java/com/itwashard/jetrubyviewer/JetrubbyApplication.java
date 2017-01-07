package com.itwashard.jetrubyviewer;

import android.app.Application;
import android.content.Context;

import com.itwashard.jetrubyviewer.injection.component.ApplicationComponent;
import com.itwashard.jetrubyviewer.injection.component.DaggerApplicationComponent;
import com.itwashard.jetrubyviewer.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class JetrubbyApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static JetrubbyApplication get(Context context) {
        return (JetrubbyApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
