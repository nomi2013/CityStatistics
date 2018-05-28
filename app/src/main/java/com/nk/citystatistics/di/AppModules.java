package com.nk.citystatistics.di;

import android.arch.persistence.room.Room;
import com.nk.citystatistics.application.CityStatisticsApplication;
import com.nk.citystatistics.db.CityStatisticsDatabase;
import com.nk.citystatistics.utils.AppConstant;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Named;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Noman Khan on 27/05/18.
 */

@Module
public class AppModules {
    CityStatisticsApplication myApplication;

    public AppModules(CityStatisticsApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    @Singleton
    CityStatisticsDatabase provideCityStatisticsDatabase() {
        return Room.databaseBuilder(myApplication,
                CityStatisticsDatabase.class, "city_statistics.db")
                .build();
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    @Named(AppConstant.MAIN_THREAD)
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named(AppConstant.NEW_THREAD)
    Scheduler provideNewThreadScheduler() {
        return Schedulers.newThread();
    }
}
