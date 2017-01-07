package com.itwashard.jetrubyviewer.injection.module;

import android.app.Application;
import android.content.Context;

import com.itwashard.jetrubyviewer.data.DataManager;
import com.itwashard.jetrubyviewer.data.remote.JetrubyService;
import com.itwashard.jetrubyviewer.injection.ApplicationContext;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

@Module
public class ApplicationTestModule {
    private final Application mApplication;

    public ApplicationTestModule(Application application) {
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

    /************* MOCKS *************/

    @Provides
    @Singleton
    DataManager providesDataManager() {
        return Mockito.mock(DataManager.class);
    }

    @Provides
    @Singleton
    JetrubyService provideBourbonService() {
        return Mockito.mock(JetrubyService.class);
    }
}
