package com.nk.citystatistics.ui.splash;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import com.nk.citystatistics.utils.URLUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by Noman Khan on 05/06/18.
 */
public class SplashPresenterTest {

    @Mock
    private SplashMvpView splashMvpView;

    private SplashPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new SplashPresenter();
        presenter.attachView(splashMvpView);
    }

    @Test
    public void attachView() {
        assertEquals(true, presenter.isViewAttached());
    }

    @Test
    public void checkURLWithPresenter() {
        String url = "https://media.giphy.com/media/t7sEnf5w7wJ1CEPyy7/giphy.gif";
        presenter.checkURL(url);
        verify(splashMvpView).urlIsValid(url);
    }
    @Test
    public void checkValidURL(){
        String url = "https://media.giphy.com/media/t7sEnf5w7wJ1CEPyy7/giphy.gif";
        assertEquals(true, URLUtils.isNetworkUrl(url));
    }

    @Test
    public void checkInvalidURL(){
        String url = "htt://media.giphy.com/media/t7sEnf5w7wJ1CEPyy7/giphy.gif";
        assertEquals(true, URLUtils.isNetworkUrl(url));
    }
}