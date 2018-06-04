package com.nk.citystatistics;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Created by Noman Khan on 03/06/18.
 */
public class RxJavaTestRunner extends BlockJUnit4ClassRunner {

    public RxJavaTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        RxAndroidPlugins.reset();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }
}
