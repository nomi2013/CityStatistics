package com.nk.citystatistics.ui.cities;

import android.content.Context;
import com.nk.citystatistics.R;
import com.nk.citystatistics.db.CityStatisticsDatabase;
import com.nk.citystatistics.db.model.CityInfo;
import com.nk.citystatistics.mvp.BasePresenter;
import com.nk.citystatistics.utils.StringUtils;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Noman Khan on 28/05/18.
 */
public class CityListPresenter extends BasePresenter<CityListMvpView> {


    @Inject
    CityListPresenter(){

    }

    public void getAllValue(CityStatisticsDatabase database, Scheduler mainThread,
            Scheduler newThread)  {
        database.cityDao().getAllCities()
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new SingleObserver<List<CityInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<CityInfo> cityInfos) {
                        if(getMvpView() != null)getMvpView().getAllCitiesData(cityInfos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(getMvpView() != null)getMvpView().getAllCitiesFailed(e);

                    }
                });

    }

    public void validateForm(Context context, final CityInfo cityInfo) {

        if (StringUtils.isEmpty(cityInfo.getCityName().trim())) {
            if(getMvpView() != null)getMvpView().showErrorMessage(context.getString(R.string.empty_city));
            return;
        } else if (StringUtils.emptyLength(cityInfo.getCityPopulation())) {
            if(getMvpView() != null)getMvpView().showErrorMessage(context.getString(R.string.invalid_population_data));
            return;
        } else if (StringUtils.isEmpty(cityInfo.getState().trim())) {
            if(getMvpView() != null)getMvpView().showErrorMessage(context.getString(R.string.empty_state));
            return;
        }

        if(getMvpView() != null)getMvpView().validationSuccessfull(cityInfo);

    }


    public void saveData(CityStatisticsDatabase database, Scheduler mainThread,
            Scheduler newThread, CityInfo cityInfo) {
        Completable.fromAction(() -> database.cityDao().insertCity(cityInfo))
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        if (getMvpView() != null) getMvpView().dataSaveSuccessfully(cityInfo);
                        if (!isDisposed())this.dispose();

                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            if (getMvpView() != null) getMvpView().dataSavingError(e);
                            if (!isDisposed())this.dispose();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                    }
                });
    }

    public void sortCityName(Scheduler mainThread,
            Scheduler newThread, List<CityInfo> infoList) {

        if(infoList.size() == 1) {
            if(getMvpView() != null)getMvpView().sortedCityData(infoList);
            return;
        }


        Completable.fromAction(() -> Collections.sort(infoList,
                (o1, o2) -> o1.cityName.compareTo(o2.cityName)))
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        infoList.size();
                        if(getMvpView() != null)getMvpView().sortedCityData(infoList);
                        if (!isDisposed())this.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(getMvpView() != null)getMvpView().showErrorMessage(e.getMessage());
                        if (!isDisposed())this.dispose();
                    }
                });


    }

    public void sortPopulation(Scheduler mainThread,
            Scheduler newThread , List<CityInfo> infoList) {

        if(infoList.size() == 1) {
            getMvpView().sortedCityData(infoList);
            return;
        }

        Completable.fromAction(() -> Collections.sort(infoList,
                (o1, o2) -> o1.cityPopulation - o2.cityPopulation))
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        infoList.size();
                        if(getMvpView() != null)getMvpView().sortedCityData(infoList);
                        if (!isDisposed())this.dispose();

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isDisposed())this.dispose();

                    }
                });


    }

    public void sortCreationDate(Scheduler mainThread,
            Scheduler newThread , List<CityInfo> infoList) {

        if(infoList.size() == 1) {
            if(getMvpView() != null)getMvpView().sortedCityData(infoList);
            return;
        }

        Completable.fromAction(() -> Collections.sort(infoList,
                (o1, o2) -> o1.creationDate.compareTo(o2.creationDate)))
                .observeOn(mainThread)
                .subscribeOn(newThread)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        infoList.size();
                        if(getMvpView() != null)getMvpView().sortedCityData(infoList);
                        if (!isDisposed())this.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isDisposed())this.dispose();

                    }
                });


    }

    public void removeCity(CityStatisticsDatabase database, Scheduler mainThread,
            Scheduler newThread ,CityInfo cityInfo) {
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
