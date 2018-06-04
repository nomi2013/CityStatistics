package com.nk.citystatistics.ui.splash;

import android.os.Handler;
import com.nk.citystatistics.mvp.BasePresenter;
import javax.inject.Inject;

/**
 * Created by Noman Khan on 05/06/18.
 */
public class SplashPresenter extends BasePresenter<SplashMvpView> {

    @Inject
    SplashPresenter() {

    }

    public void holdScreen() {
        new Handler().postDelayed(() -> getMvpView().openCityListActivity(), 5000);
    }
}
