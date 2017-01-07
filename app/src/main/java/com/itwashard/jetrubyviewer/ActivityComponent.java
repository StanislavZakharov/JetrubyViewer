package com.itwashard.jetrubyviewer;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

import com.itwashard.jetrubyviewer.activity.ShotActivity;
import com.itwashard.jetrubyviewer.fragments.BrowseFragment;
import com.itwashard.jetrubyviewer.fragments.ShotFragment;
import com.itwashard.jetrubyviewer.injection.PerActivity;
import com.itwashard.jetrubyviewer.injection.component.ApplicationComponent;
import com.itwashard.jetrubyviewer.injection.module.ActivityModule;

import dagger.Component;

/**
 * This component injects dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BrowseFragment browseFragment);

    void inject(ShotFragment shotFragment);

    void inject(ShotActivity shotActivity);

}
