package com.nk.citystatistics.ui.cities;

import com.nk.citystatistics.db.model.CityInfo;
import com.nk.citystatistics.mvp.MvpView;
import java.util.List;

/**
 * Created by Noman Khan on 28/05/18.
 */
public interface CityListMvpView extends MvpView{

    void showErroMessage(String msg);
    void dataSaveSuccessfully(CityInfo cityInfo);
    void dataSavingError(Throwable e);
    void getAllCitiesFailed(Throwable t);

    void getAllCitiesData(List<CityInfo> cityInfo);

    void sortedCityData(List<CityInfo> infoList);
}
