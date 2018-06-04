package com.nk.citystatistics.ui;

import com.nk.citystatistics.db.model.CityInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noman Khan on 04/06/18.
 */
public class TestUtils {

    public static List<CityInfo> getCityListData() {
        List<CityInfo> cityInfoList = new ArrayList<>();

        CityInfo cityInfo = new CityInfo();
        cityInfo.setCityName("Lucknow");
        cityInfo.setCityPopulation(989);
        cityInfo.setState("UP");

        cityInfoList.add(cityInfo);

        CityInfo cityInfo1 = new CityInfo();
        cityInfo1.setCityName("Agra");
        cityInfo1.setCityPopulation(10989);
        cityInfo1.setState("UP");

        cityInfoList.add(cityInfo1);

        return cityInfoList;
    }

    public static CityInfo getCityInfo(String name, int population, String state) {
        CityInfo cityInfo = new CityInfo();
        cityInfo.setCityName(name);
        cityInfo.setCityPopulation(population);
        cityInfo.setState(state);

        return cityInfo;
    }
}
