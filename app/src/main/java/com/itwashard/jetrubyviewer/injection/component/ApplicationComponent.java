package com.itwashard.jetrubyviewer.injection.component;

import android.app.Application;
import android.content.Context;

import com.itwashard.jetrubyviewer.data.DataManager;
import com.itwashard.jetrubyviewer.data.remote.JetrubyService;
import com.itwashard.jetrubyviewer.injection.ApplicationContext;
import com.itwashard.jetrubyviewer.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    DataManager dataManager();
    JetrubyService jetrubyService();

}
