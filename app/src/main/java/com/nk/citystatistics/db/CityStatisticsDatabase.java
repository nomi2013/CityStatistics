package com.nk.citystatistics.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.nk.citystatistics.db.model.CityInfo;

/**
 * Created by Noman Khan on 28/05/18.
 */
@Database(entities = {CityInfo.class}, version = 1)
public abstract class CityStatisticsDatabase extends RoomDatabase{
    public abstract CityStatisticsDao cityDao();
}
