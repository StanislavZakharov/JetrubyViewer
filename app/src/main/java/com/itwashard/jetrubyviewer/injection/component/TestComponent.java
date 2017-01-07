package com.itwashard.jetrubyviewer.injection.component;

import com.itwashard.jetrubyviewer.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
