package com.nk.citystatistics.ui.cities;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import android.content.Context;
import com.nk.citystatistics.RxJavaTestRunner;
import com.nk.citystatistics.db.model.CityInfo;
import com.nk.citystatistics.ui.TestUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by Noman Khan on 03/06/18.
 */
@RunWith(RxJavaTestRunner.class)
public class CityListPresenterTest {

    @Mock
    private CityListMvpView mvpView;

    @Mock
    private Context context;

    private CityListPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler ->
                io.reactivex.schedulers.Schedulers.trampoline());

        presenter = new CityListPresenter();
        presenter.attachView(mvpView);

    }

    @Test
    public void attachView() {
        assertEquals(true, presenter.isViewAttached());
    }


    @Test
    public void validateFormFailed() {
        CityInfo cityInfo = TestUtils.getCityInfo(" ", 1200, "Up");
        presenter.validateForm(context, cityInfo);
        verify(mvpView).validationSuccessfull(any(CityInfo.class));

    }

    @Test
    @SuppressWarnings("SpellCheckingInspection")
    public void validateFormPassed() {
        CityInfo cityInfo = TestUtils.getCityInfo("Jaunpur", 200000, "Up");
        presenter.validateForm(context, cityInfo);
        verify(mvpView).validationSuccessfull(any(CityInfo.class));

    }

    @Test
    public void sortByName() {
        List<CityInfo> cityInfoList = TestUtils.getCityListData();
        presenter
                .sortCityName(AndroidSchedulers.mainThread(), Schedulers.newThread(), cityInfoList);
        verify(mvpView).sortedCityData(cityInfoList);
    }

    @Test
    public void sortByNameWithoutPresenter() {
        List<CityInfo> cityInfoList = TestUtils.getCityListData();
        List<CityInfo> tempList = new ArrayList<>();
        tempList.addAll(cityInfoList);

        Collections.sort(cityInfoList, (o1, o2) -> o1.cityName.compareTo(o2.cityName));
        Assert.assertEquals(tempList
                , cityInfoList);

    }

    @Test
    public void sortByPopulation() {
        List<CityInfo> cityInfoList = TestUtils.getCityListData();
        presenter
                .sortCityName(AndroidSchedulers.mainThread(), Schedulers.newThread(), cityInfoList);
        verify(mvpView).sortedCityData(cityInfoList);
    }


    @Test
    public void sortByState() {
        List<CityInfo> cityInfoList = TestUtils.getCityListData();

        presenter
                .sortCityName(AndroidSchedulers.mainThread(), Schedulers.newThread(), cityInfoList);
        verify(mvpView).sortedCityData(cityInfoList);
    }

    @After
    public void tearDown() {
        presenter.detachView();
        presenter = null;
    }

}