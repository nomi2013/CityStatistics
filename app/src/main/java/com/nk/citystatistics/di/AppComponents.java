package com.nk.citystatistics.di;

import com.nk.citystatistics.application.CityStatisticsApplication;
import com.nk.citystatistics.dialog.CityInputDialogFragment;
import com.nk.citystatistics.ui.cities.CityListActivity;
import com.nk.citystatistics.ui.splash.SplashActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by Noman Khan on 27/05/18.
 */
@Component(modules = AppModules.class)
@Singleton
public interface AppComponents {
    void inject(CityStatisticsApplication context);
    void inject(CityListActivity cityListActivity);
    void inject(CityInputDialogFragment cityInputDialogFragment);

    void inject(SplashActivity splashActivity);
}
