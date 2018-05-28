package com.nk.citystatistics.application;

import android.app.Application;
import com.nk.citystatistics.di.AppComponents;
import com.nk.citystatistics.di.AppModules;
import com.nk.citystatistics.di.DaggerAppComponents;

/**
 * Created by Noman Khan on 27/05/18.
 */
public class CityStatisticsApplication extends Application {
    public static AppComponents appComponents;
    public static CityStatisticsApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        injectDependencies(INSTANCE);
    }

    public static void injectDependencies(CityStatisticsApplication context) {
        appComponents = DaggerAppComponents.builder()
                .appModules(new AppModules(context))
                .build();
        appComponents.inject(context);
    }

    public static AppComponents getAppComponents() {
        return appComponents;
    }

}
