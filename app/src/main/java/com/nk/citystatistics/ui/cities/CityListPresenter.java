package com.nk.citystatistics.ui.cities;

import android.content.Context;
import com.nk.citystatistics.R;
import com.nk.citystatistics.db.CityStatisticsDatabase;
import com.nk.citystatistics.db.model.CityInfo;
import com.nk.citystatistics.mvp.BasePresenter;
import com.nk.citystatistics.utils.AppConstant;
import com.nk.citystatistics.utils.StringUtils;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Noman Khan on 28/05/18.
 */
public class CityListPresenter extends BasePresenter<CityListMvpView> {

    @Inject
    CityStatisticsDatabase database;

    @Inject
    @Named(AppConstant.MAIN_THREAD)
    Scheduler mainThread;


    @Inject
    @Named(AppConstant.NEW_THREAD)
    Scheduler newThread;

    @Inject
    CityListPresenter(){

    }

    public void getAllValue()  {
        database.cityDao().getAllCities()
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new SingleObserver<List<CityInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<CityInfo> cityInfos) {
                        getMvpView().getAllCitiesData(cityInfos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().getAllCitiesFailed(e);

                    }
                });

    }

    public void saveCity(final CityInfo cityInfo) {
        Context ctx = (Context)getMvpView();

        if (StringUtils.isEmpty(cityInfo.getCityName())) {
            getMvpView().showErroMessage(ctx.getString(R.string.empty_city));
            return;
        } else if (StringUtils.emptyLength(cityInfo.getCityPopulation())) {
            getMvpView().showErroMessage(ctx.getString(R.string.invalid_population_data));
            return;
        } else if (StringUtils.isEmpty(cityInfo.getState())) {
            getMvpView().showErroMessage(ctx.getString(R.string.empty_state));
            return;
        }


        Completable.fromAction(() -> database.cityDao().insertCity(cityInfo))
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getMvpView().dataSaveSuccessfully(cityInfo);
                if (!isDisposed())this.dispose();

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().dataSavingError(e);
                if (!isDisposed())this.dispose();

            }
        });

    }

    public void sortCityName(List<CityInfo> infoList) {

        Completable.fromAction(() -> Collections.sort(infoList,
                (o1, o2) -> o1.cityName.compareTo(o2.cityName)))
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        infoList.size();
                        getMvpView().sortedCityData(infoList);
                        if (!isDisposed())this.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isDisposed())this.dispose();

                    }
                });


    }

    public void sortPopulation(List<CityInfo> infoList) {

        Completable.fromAction(() -> Collections.sort(infoList,
                (o1, o2) -> (int)o1.cityPopulation - o2.cityPopulation))
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        infoList.size();
                        getMvpView().sortedCityData(infoList);
                        if (!isDisposed())this.dispose();

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isDisposed())this.dispose();

                    }
                });


    }

    public void sortCreationDate(List<CityInfo> infoList) {

        Completable.fromAction(() -> Collections.sort(infoList,
                (o1, o2) -> o1.creationDate.compareTo(o2.creationDate)))
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        infoList.size();
                        getMvpView().sortedCityData(infoList);
                        if (!isDisposed())this.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isDisposed())this.dispose();

                    }
                });


    }

    public void removeCity(CityInfo cityInfo) {
        Completable.fromAction(() -> database.cityDao().deleteCity(cityInfo))
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        if (!isDisposed())this.dispose();

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isDisposed())this.dispose();

                    }
                });
    }
}
