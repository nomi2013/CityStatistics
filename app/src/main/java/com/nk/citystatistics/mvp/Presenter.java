package com.nk.citystatistics.mvp;

/**
 * Created by Noman Khan on 27/05/18.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}