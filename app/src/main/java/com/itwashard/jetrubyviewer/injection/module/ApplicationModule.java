package com.itwashard.jetrubyviewer.injection.module;

import android.app.Application;
import android.content.Context;

import com.itwashard.jetrubyviewer.data.remote.JetrubyService;
import com.itwashard.jetrubyviewer.data.remote.JetrubyServiceFactory;
import com.itwashard.jetrubyviewer.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by stanislavzakharov on 04.01.17.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    JetrubyService provideJetrubbyService() {
        return JetrubyServiceFactory.makeJetrubyService();
    }
}
