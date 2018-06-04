package com.nk.citystatistics.application;

import android.app.Application;
import com.nk.citystatistics.di.AppComponents;
import com.nk.citystatistics.di.AppModules;
import com.nk.citystatistics.di.DaggerAppComponents;

/**
 * Created by Noman Khan on 27/05/18.
 */
public class CityStatisticsApplication extends Application {
    public  AppComponents appComponents;

    @Override
    public void onCreate() {
        super.onCreate();
        injectDependencies(this);
    }

    public  void injectDependencies(CityStatisticsApplication context) {
        appComponents = DaggerAppComponents.builder()
                .appModules(new AppModules(context))
                .build();
        appComponents.inject(context);
    }

    public  AppComponents getAppComponents() {
        return appComponents;
    }

}
