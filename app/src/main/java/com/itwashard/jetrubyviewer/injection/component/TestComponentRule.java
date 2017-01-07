package com.itwashard.jetrubyviewer.injection.component;

import android.content.Context;

import com.itwashard.jetrubyviewer.JetrubbyApplication;
import com.itwashard.jetrubyviewer.data.DataManager;
import com.itwashard.jetrubyviewer.injection.module.ApplicationTestModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by stanislavzakharov on 04.01.17.
 */

public class TestComponentRule implements TestRule {

    private final TestComponent mTestComponent;
    private final Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
        JetrubbyApplication application = JetrubbyApplication.get(context);
        mTestComponent = DaggerTestComponent.builder()
                .applicationTestModule(new ApplicationTestModule(application))
                .build();
    }

    public TestComponent getTestComponent() {
        return mTestComponent;
    }

    public Context getContext() {
        return mContext;
    }

    public DataManager getMockDataManager() {
        return mTestComponent.dataManager();
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                JetrubbyApplication application = JetrubbyApplication.get(mContext);
                application.setComponent(mTestComponent);
                base.evaluate();
                application.setComponent(null);
            }
        };
    }
}
