package com.nk.citystatistics.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nk.citystatistics.db.model.CityInfo;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by Noman Khan on 28/05/18.
 */
@Dao
public interface CityStatisticsDao {

    @Insert
    void insertCity(CityInfo cityInfo);

    @Query("SELECT * FROM CityInfo")
    Single<List<CityInfo>> getAllCities();

    @Delete
    void deleteCity(CityInfo cityInfo);
}
