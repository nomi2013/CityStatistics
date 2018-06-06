package com.nk.citystatistics.ui.splash;

import android.os.Handler;
import android.webkit.URLUtil;
import com.nk.citystatistics.mvp.BasePresenter;
import com.nk.citystatistics.utils.URLUtils;
import javax.inject.Inject;

/**
 * Created by Noman Khan on 05/06/18.
 */
public class SplashPresenter extends BasePresenter<SplashMvpView> {

    @Inject
    SplashPresenter() {

    }

    @Override
    public void attachView(SplashMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void holdScreen() {
        new Handler().postDelayed(() -> getMvpView().openCityListActivity(), 5000);
    }

    public void checkURL(String url) {
        if (URLUtils.isNetworkUrl(url)) {
            if(getMvpView() != null)getMvpView().urlIsValid(url);
        } else {
            if(getMvpView() != null)getMvpView().urlIsInvalid();
        }
    }
}
