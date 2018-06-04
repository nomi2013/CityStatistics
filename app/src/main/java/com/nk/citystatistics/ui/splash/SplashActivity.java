package com.nk.citystatistics.ui.splash;

import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nk.citystatistics.R;
import com.nk.citystatistics.application.CityStatisticsApplication;
import com.nk.citystatistics.base.BaseActivity;
import com.nk.citystatistics.ui.cities.CityListActivity;
import com.nk.citystatistics.utils.ToastUtils;
import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashMvpView{

    @Inject
    SplashPresenter presenter;
    private ImageView imgGif;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imgGif = findViewById(R.id.imgSplash);

        presenter.attachView(this);
        presenter.holdScreen();
        presenter.checkURL("https://media.giphy.com/media/t7sEnf5w7wJ1CEPyy7/giphy.gif");

    }


    @Override
    public void injectDependency() {
        new CityStatisticsApplication().getAppComponents().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void openCityListActivity() {
        startActivity(CityListActivity.newIntent(this));
        finish();
    }

    @Override
    public void urlIsValid(String url) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.splash_bg).error(R.drawable
                        .splash_bg).fitCenter().override((int)context.getResources().getDimension
                        (R.dimen._250sdp), (int)context.getResources().getDimension(R.dimen
                        ._250sdp)))
                .into(imgGif);
    }

    @Override
    public void urlIsInvalid() {
        ToastUtils.showToastMessageNormal(applicationContext, getString(R.string.url_invalid));
    }

}
