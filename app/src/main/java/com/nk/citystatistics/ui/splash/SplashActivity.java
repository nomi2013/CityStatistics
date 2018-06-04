package com.nk.citystatistics.ui.splash;

import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nk.citystatistics.R;
import com.nk.citystatistics.application.CityStatisticsApplication;
import com.nk.citystatistics.base.BaseActivity;
import com.nk.citystatistics.ui.cities.CityListActivity;
import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashMvpView{

    @Inject
    SplashPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attachView(this);
        presenter.holdScreen();

        ImageView imageView = findViewById(R.id.imgSplash);
        loadGif(imageView);

    }


    @Override
    public void injectDependency() {
        CityStatisticsApplication.getAppComponents().inject(this);
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

    private void loadGif(ImageView imageView) {
        Glide.with(context)
                .load("https://media.giphy.com/media/t7sEnf5w7wJ1CEPyy7/giphy.gif")
                .apply(new RequestOptions().placeholder(R.drawable.splash_bg).error(R.drawable
                        .splash_bg).fitCenter().override((int)context.getResources().getDimension
                        (R.dimen._250sdp), (int)context.getResources().getDimension(R.dimen
                        ._250sdp)))
                .into(imageView);
    }

}
